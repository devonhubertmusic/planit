package planit;

import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
public class Plan {

	//Fields
    
    //List of ordered Activities that make up the plan
	private ArrayList<Activity> activityList;
    private int tryCounter;
	
	//Default Constructor
	public Plan() {
		this.activityList = new ArrayList<Activity>();
        this.tryCounter = 0;
	}
	
	//Methods
    public void generatePlan_TEMP(ArrayList<Activity> database, double availableTime, double
    availableMoney, double costPercent) {
        initializeActivityListAndUpdate();
        ArrayList<Activity> activitiesSortedByExpensiveness = sortByExpensiveness(copyDatabase(database)); //Do before calling?
        double averageExpensiveness = findAverageExpensiveness(activitiesSortedByExpensiveness); //Do before calling?
        double desiredExpensiveness = availableMoney/availableTime;
        
        if(availableTime > findMaxPossibleActivityTime(activitiesSortedByExpensiveness)) {
            displayErrorMessage("Unable to generate a long enough plan with the given activities, please choose a "
                                + "smaller time, or add longer activities!");
        } else if(availableMoney < findMinPossibleActivityCost(activitiesSortedByExpensiveness)){
            displayErrorMessage("Not enough money for any given activity");
        } else {
            //Generate plan using the sorted list
            ArrayList<Activity> possibleActivities = findPossibleActivities(activitiesSortedByExpensiveness,
                                averageExpensiveness, desiredExpensiveness, availableTime, availableMoney);
            double totalTime = 0.0;
            double totalCost = 0.0;
            double minCost = costPercent * availableMoney;
                while(possibleActivities.size() > 0) { 
                    double remainingTime = availableTime - totalTime;
                    double remainingMoney = availableMoney - totalCost;
                
                    Random rn = new Random();
                    int randomIndex = rn.nextInt(possibleActivities.size());
                    Activity currentActivity = possibleActivities.get(randomIndex);
                    if(currentActivity.getIdealTime() <= remainingTime
                        && currentActivity.getMaxCost() <= remainingMoney) {
                        activityList.add(currentActivity);
                        totalTime += currentActivity.getIdealTime();
                        totalCost += currentActivity.getMaxCost();
                    }
                    possibleActivities.remove(randomIndex);
                }
                double remainingTime = availableTime - totalTime;
                double remainingMoney = availableMoney - totalCost;
            
                double potentialStretch = 0.0;
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    potentialStretch += temp.getTimeGap();
                }
            
                //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
                double maxStretch = (1/activityList.size()) * potentialStretch;
            
                if(maxStretch >= remainingTime && (totalCost >= minCost || availableMoney == 0)) {
                    double stretchPercent = remainingTime/potentialStretch;
                    for(int i = 0; i < activityList.size(); i++) {
                        Activity temp = activityList.get(i);
                        temp.setActualTime(temp.getIdealTime() + (stretchPercent * temp.getTimeGap()));
                    }
                } else {
                    displayErrorMessage("Something went wrong, blame Devon");
                }
            
            
        }
    }
    
    public ArrayList<Activity> findPossibleActivities(ArrayList<Activity> activityList, double averageExpensiveness,
                               double desiredExpensiveness, double availableTime, double availableMoney) {
        return new ArrayList<Activity>(); //CHANGE
    }
    
    public void displayErrorMessage(String errorMessage) {
        Activity message = new Activity();
        message.setName(errorMessage); //Replace with pop-up
        this.activityList = new ArrayList<Activity>();
        this.activityList.add(message);
    }
    
    public double findMaxPossibleActivityTime(ArrayList<Activity> activities) {
        double totalTime = 0.0;
        for(int index = 0; index < activities.size(); index++) {
            Activity currentActivity = activities.get(index);
            totalTime += currentActivity.getMaxTime();
        }
        return totalTime;
    }
    
    public double findMinPossibleActivityCost(ArrayList<Activity> activities) {
        double minCost = 1000000.0;
        for(int index = 0; index < activities.size(); index++) {
            Activity currentActivity = activities.get(index);
            double cost = currentActivity.getMaxCost();
            if(cost < minCost){
                minCost = cost;
            }
        }
        return minCost;
    }
    
    public void initializeActivityListAndUpdate() {
        this.activityList = new ArrayList<Activity>();
        PlanitRunner.updateActivityList();
    }
    
    public ArrayList<Activity> sortByExpensiveness(ArrayList<Activity> activities) {
        Collections.sort(activities);
        return activities;
    }
    
    public double findAverageExpensiveness(ArrayList<Activity> activities) {
        double totalExpense = 0.0;
        for(int index = 0; index < activities.size(); index++) {
            Activity currentActivity = activities.get(index);
            totalExpense += currentActivity.getCostPerHour();
        }
        return totalExpense/activities.size();
    }
    
    
    
    
    
    
	public void generatePlan(ArrayList<Activity> database, double availableTime, double 
	availableMoney, double costPercent){
        this.activityList = new ArrayList<Activity>();
        PlanitRunner.updateActivityList();
        if(this.tryCounter > PlanitRunner.database.size() || costPercent == 0) {
            Activity temp = new Activity();
            temp.setName("Unable to generate plan");
            this.activityList.add(temp);
        } else {
            ArrayList<Activity> databaseCopy = copyDatabase(database);
            double totalTime = 0.0;
            double totalCost = 0.0;
            double minCost = costPercent * availableMoney;
         
            while(databaseCopy.size() > 0) {
                double remainingTime = availableTime - totalTime;
                double remainingMoney = availableMoney - totalCost;
         
                Random rn = new Random();
                int randomIndex = rn.nextInt(databaseCopy.size());
                Activity currentActivity = databaseCopy.get(randomIndex);
                if(currentActivity.getIdealTime() <= remainingTime
                && currentActivity.getMaxCost() <= remainingMoney) {
                    activityList.add(currentActivity);
                    totalTime += currentActivity.getIdealTime();
                    totalCost += currentActivity.getMaxCost();
                }
                databaseCopy.remove(randomIndex);
            }
            //(Database list copy is now empty)
            double remainingTime = availableTime - totalTime;
            double remainingMoney = availableMoney - totalCost;

            double potentialStretch = 0.0;
            for(int i = 0; i < activityList.size(); i++) {
                Activity temp = activityList.get(i);
                potentialStretch += temp.getTimeGap();
            }
        
            //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
            double maxStretch = (1/activityList.size()) * potentialStretch;
         
            if(maxStretch >= remainingTime && (totalCost >= minCost || availableMoney == 0)) {
                double stretchPercent = remainingTime/potentialStretch;
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    temp.setActualTime(temp.getIdealTime() + (stretchPercent * temp.getTimeGap()));
                }
            } else if((totalCost < minCost) && costPercent != 0 && availableMoney != 0) {
                //Try again!
                generatePlan(database, availableTime, availableMoney, Math.abs(costPercent - 0.05)); //make more modular
            } else {
                //Try again!
                this.tryCounter++; //Find a better way...
                generatePlan(database, availableTime, availableMoney, costPercent);
            }
        }
	}
    
    
    //Makes a deep copy of an Arraylist<Activity>
    public ArrayList<Activity> copyDatabase(ArrayList<Activity> database) {
        ArrayList<Activity> databaseCopy = new ArrayList<Activity>();
        for(int i = 0; i < database.size(); i++) {
            Activity temp = database.get(i);
            Activity tempCopy = new Activity();
            tempCopy.setName(temp.getName());
            tempCopy.setIdealTime(temp.getIdealTime());
            tempCopy.setMaxTime(temp.getMaxTime());
            tempCopy.setActualTime(temp.getActualTime());
            tempCopy.setMaxCost(temp.getMaxCost());
            
            databaseCopy.add(tempCopy);
        }
        return databaseCopy;
    }
    
	//Getters and setters
    
    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }
    
    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }
}
