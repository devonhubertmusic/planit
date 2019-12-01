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

/**
* Plan is the main algorithm behind generating a plan
*/
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
    
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
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
    
    
    //Divide activities into groups by activity type
    public ArrayList<ArrayList<Activity>> divideByActivityType(ArrayList<Activity> databaseCopy) {
        ArrayList<ArrayList<Activity>> activityGroups = new ArrayList<ArrayList<Activity>>();
        ArrayList<Activity> leftOverActivities = new ArrayList<Activity>();
        
        for(int activityIndex = 0; activityIndex < databaseCopy.size(); activityIndex++) {
            Activity currentActivity = databaseCopy.get(activityIndex);
            
            String activityType = currentActivity.getActivityType();
            System.out.println("Activity type of current activity is " + activityType);
            
            if(activityType == null || activityType.equalsIgnoreCase("Misc") || activityType.equals("")) {
                leftOverActivities.add(currentActivity);
                System.out.println("Activity added to leftovers");
            } else {
                System.out.println("Activity type is not null, it is " + activityType);
                boolean activityGroupFound = false;
                for(int activityGroupIndex = 0; activityGroupIndex < activityGroups.size(); activityGroupIndex++) {
                    ArrayList<Activity> currentActivityGroup = activityGroups.get(activityGroupIndex);
                    if(currentActivityGroup.size() > 0) {
                        String tempActivityType = currentActivityGroup.get(0).getActivityType();
                        if(tempActivityType.equalsIgnoreCase(activityType)) {
                            activityGroupFound = true;
                            currentActivityGroup.add(currentActivity);
                            break;
                        }
                    } else {
                        displayErrorMessage("Something went wrong, blame Devon");
                    }
                }
                if(!activityGroupFound) {
                    System.out.println("New activity group created");
                    ArrayList<Activity> newActivityGroup = new ArrayList<Activity>();
                    newActivityGroup.add(currentActivity);
                    activityGroups.add(newActivityGroup);
                }
            }
        }
        activityGroups.add(0, leftOverActivities);
        return activityGroups;
    }
    
	public void generatePlan(ArrayList<Activity> database, double availableTime, double 
	availableMoney, double costPercent){
        initializeActivityListAndUpdate();
        if(availableTime > findMaxPossibleActivityTime(database)) {
            displayErrorMessage("Unable to generate a long enough plan with the given activities, " +
                                "please choose a smaller time, or add longer activities!");
        } else if(availableMoney < findMinPossibleActivityCost(database)){
            displayErrorMessage("Not enough money for any given activity");
        } else {
            ArrayList<Activity> databaseCopy = copyDatabase(database);
            System.out.println("Database copy is " + databaseCopy);
            boolean listSizeDecreased = false;
            
            ArrayList<ArrayList<Activity>> activityGroups = divideByActivityType(databaseCopy);
            
            ArrayList<Activity> leftOverActivities = activityGroups.get(0);
            activityGroups.remove(0);
            
                double totalTime = 0.0;
                double totalCost = 0.0;
                double minCost = costPercent * availableMoney; //Change?
            
                ArrayList<Integer> usedActivityGroupIndices = new ArrayList<Integer>();
                int currentActivityGroupIndex = 0;
                    while(activityGroups.size() > 0 && leftOverActivities.size() > 0 && totalTime < availableTime) {
                        if(usedActivityGroupIndices.size() >= activityGroups.size()) {
                            System.out.println("All indices used");
                            usedActivityGroupIndices = new ArrayList<Integer>();
                            Random nextRand = new Random();
                            currentActivityGroupIndex = nextRand.nextInt(activityGroups.size());
                            usedActivityGroupIndices.add(currentActivityGroupIndex);
                        } else {
                            System.out.println("Not all indices used");
                            boolean nextIndexFound = false;
                            while(!nextIndexFound) {
                                Random nextRand = new Random();
                                int tempIndex = nextRand.nextInt(activityGroups.size());
                                if(!usedActivityGroupIndices.contains(tempIndex)) {
                                    currentActivityGroupIndex = tempIndex;
                                    usedActivityGroupIndices.add(tempIndex);
                                    nextIndexFound = true;
                                    System.out.println("New index is " + tempIndex);
                                }
                            }
                        }
                        
                        ArrayList<Activity> currentActivityGroup = activityGroups.get(currentActivityGroupIndex);
                        System.out.println("Current act group is " + currentActivityGroup);
                        
                        int randomRange = currentActivityGroup.size() + leftOverActivities.size();
                        Random rn = new Random();
                        int randomIndex = rn.nextInt(randomRange);
                        
                        System.out.println("size of current act group is " + currentActivityGroup.size()); //1
                        System.out.println("number of remaining activity types is " + activityGroups.size());
                        System.out.println("size of leftover acts is " + leftOverActivities.size()); //16
                        System.out.println("random range is " + randomRange); //17
                        System.out.println("random index is " + randomIndex); //14
                        
                        Activity currentActivity;
                        if(randomIndex < currentActivityGroup.size()) {
                            System.out.println("Drawing from currentActivityGroup");
                            currentActivity = currentActivityGroup.get(randomIndex);
                        } else {
                            System.out.println("Drawing from leftover activities");
                            currentActivity = leftOverActivities.get(randomIndex - currentActivityGroup.size());
                        }
                        
                        double remainingTime = availableTime - totalTime;
                        double remainingMoney = availableMoney - totalCost;
                        
                        
                        System.out.println("Ideal time for act is " + currentActivity.getIdealTime());
                        System.out.println("Remaining time is " + remainingTime);
                        
                        if(currentActivity.getIdealTime() <= remainingTime
                           && currentActivity.getMaxCost() <= remainingMoney) {
                            activityList.add(currentActivity);
                            System.out.println("Adding " + currentActivity + " to the activity list");
                            
                            totalTime += currentActivity.getIdealTime();
                            totalCost += currentActivity.getMaxCost();
                            System.out.println("Activity list is now " + activityList);
                        } else {
                            databaseCopy.remove(currentActivity); //works?
                            listSizeDecreased = true;
                            System.out.println("Activity " + currentActivity + " does not fit the parameters");
                        }
                        if(randomIndex < currentActivityGroup.size()) {
                            System.out.println("Current activity group is " + currentActivityGroup);
                            currentActivityGroup.remove(randomIndex);
                            System.out.println("Current activity group is now" + currentActivityGroup);
                            System.out.println("Number of activity groups is " + activityGroups.size());
                            if(currentActivityGroup.size() <= 0) {
                                activityGroups.remove(currentActivityGroupIndex);
                                System.out.println("Number of activity groups is now " + activityGroups.size());
                            }
                        } else {
                            leftOverActivities.remove(randomIndex - currentActivityGroup.size());
                            System.out.println("Removing " + currentActivity + " from leftovers");
                        }
                    }
                System.out.println("Out of while loop");
            
                double remainingTime = availableTime - totalTime;
                double remainingMoney = availableMoney - totalCost;
            
                double potentialStretch = 0.0;
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    potentialStretch += temp.getTimeGap();
                }
                System.out.println("Potential stretch is " + potentialStretch);
            
                double maxStretch;
                if(activityList.size() == 0) {
                    maxStretch = -1;
                } else {
                    //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
                    maxStretch = (1/activityList.size()) * potentialStretch;
                }
                if(maxStretch >= remainingTime && (totalCost >= minCost || availableMoney == 0)) {
                    double stretchPercent = remainingTime/potentialStretch;
                    for(int i = 0; i < activityList.size(); i++) {
                        Activity temp = activityList.get(i);
                        temp.setActualTime(temp.getIdealTime() + (stretchPercent * temp.getTimeGap()));
                    }
                } else if((totalCost < minCost) && costPercent > 0 && availableMoney > 0) {
                    System.out.println("Plan could not be created the first time");
                    generatePlan(databaseCopy, availableTime, availableMoney, Math.abs(costPercent - 0.05));
                } else {
                    if(listSizeDecreased) {
                        System.out.println("The list size decreased, trying again");
                        generatePlan(databaseCopy, availableTime, availableMoney, costPercent);
                    } else {
                        displayErrorMessage("Unable to generate activity plan with given parameters");
                    }
                }
            }
	}
    
    
    //Makes a deep copy of an Arraylist<Activity>
    public ArrayList<Activity> copyDatabase(ArrayList<Activity> database) {
        ArrayList<Activity> databaseCopy = new ArrayList<Activity>();
        for(int i = 0; i < database.size(); i++) {
            Activity temp = database.get(i);
            Activity tempCopy = temp.copy();
            
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
