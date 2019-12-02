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
	
	//Default Constructor
	public Plan() {
		this.activityList = new ArrayList<Activity>();
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
        boolean updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
    }
    
    
    //Divide activities into groups by activity type
    public ArrayList<ArrayList<Activity>> divideByActivityType(ArrayList<Activity> databaseCopy) {
        ArrayList<ArrayList<Activity>> activityGroups = new ArrayList<ArrayList<Activity>>();
        ArrayList<Activity> leftOverActivities = new ArrayList<Activity>();
        
        for(int activityIndex = 0; activityIndex < databaseCopy.size(); activityIndex++) {
            Activity currentActivity = databaseCopy.get(activityIndex);
            
            String activityType = currentActivity.getActivityType();
            
            if(activityType == null || activityType.equalsIgnoreCase("Misc") || activityType.equals("")) {
                leftOverActivities.add(currentActivity);
            } else {
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
                        displayErrorMessage("Activity Type Error. Please try again.");
                    }
                }
                if(!activityGroupFound) {
                    ArrayList<Activity> newActivityGroup = new ArrayList<Activity>();
                    newActivityGroup.add(currentActivity);
                    activityGroups.add(newActivityGroup);
                }
            }
        }
        activityGroups.add(0, leftOverActivities);
        return activityGroups;
    }
    
	public boolean generatePlan(ArrayList<Activity> database, double availableTime, double
	availableMoney, double costPercent){
        initializeActivityListAndUpdate();
        if(availableTime > findMaxPossibleActivityTime(database)) {
            displayErrorMessage("Unable to generate a long enough plan with the given activities.");
            return false;
        } else if(availableMoney < findMinPossibleActivityCost(database)){
            displayErrorMessage("Budget too low for any given activity.");
            return false;
        } else {
            ArrayList<Activity> databaseCopy = copyDatabase(database);
            boolean listSizeDecreased = false;
            
            ArrayList<ArrayList<Activity>> activityGroups = divideByActivityType(databaseCopy);
            
            ArrayList<Activity> leftOverActivities = activityGroups.get(0);
            activityGroups.remove(0);
            
                double totalTime = 0.0;
                double totalCost = 0.0;
                double minCost = costPercent * availableMoney;
            
                ArrayList<Integer> usedActivityGroupIndices = new ArrayList<Integer>();
                int currentActivityGroupIndex = 0;
                    while((activityGroups.size() > 0 || leftOverActivities.size() > 0) && totalTime < availableTime) {
                        ArrayList<Activity> currentActivityGroup;
                        if(activityGroups.size() == 0 && leftOverActivities.size() > 0) {
                            currentActivityGroup = new ArrayList<Activity>();
                        } else {
                            if(usedActivityGroupIndices.size() >= activityGroups.size()) {
                                usedActivityGroupIndices = new ArrayList<Integer>();
                                Random nextRand = new Random();
                                currentActivityGroupIndex = nextRand.nextInt(activityGroups.size());
                                usedActivityGroupIndices.add(currentActivityGroupIndex);
                            } else {
                                boolean nextIndexFound = false;
                                while(!nextIndexFound) {
                                    Random nextRand = new Random();
                                    int tempIndex = nextRand.nextInt(activityGroups.size());
                                    if(!usedActivityGroupIndices.contains(tempIndex)) {
                                        currentActivityGroupIndex = tempIndex;
                                        usedActivityGroupIndices.add(tempIndex);
                                        nextIndexFound = true;
                                    }
                                }
                            }
                            currentActivityGroup = activityGroups.get(currentActivityGroupIndex);
                        }
                    
                        int randomRange = currentActivityGroup.size() + leftOverActivities.size();
                        Random rn = new Random();
                        int randomIndex = rn.nextInt(randomRange);
                        
                        Activity currentActivity;
                        if(randomIndex < currentActivityGroup.size()) {
                            currentActivity = currentActivityGroup.get(randomIndex);
                        } else {
                            currentActivity = leftOverActivities.get(randomIndex - currentActivityGroup.size());
                        }
                        
                        double remainingTime = availableTime - totalTime;
                        double remainingMoney = availableMoney - totalCost;
                        
                        if(currentActivity.getIdealTime() <= remainingTime
                           && currentActivity.getMaxCost() <= remainingMoney) {
                            activityList.add(currentActivity);
                            
                            totalTime += currentActivity.getIdealTime();
                            totalCost += currentActivity.getMaxCost();
                        } else {
                            databaseCopy.remove(currentActivity);
                            listSizeDecreased = true;
                        }
                        if(randomIndex < currentActivityGroup.size()) {
                            currentActivityGroup.remove(randomIndex);
                            if(currentActivityGroup.size() <= 0) {
                                activityGroups.remove(currentActivityGroupIndex);
                            }
                        } else {
                            leftOverActivities.remove(randomIndex - currentActivityGroup.size());
                        }
                    }
            
                double remainingTime = availableTime - totalTime;
                double remainingMoney = availableMoney - totalCost;
            
            
                double potentialStretch = 0.0;
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    potentialStretch += temp.getTimeGap();
                }
            
                double maxStretch;
                if(activityList.size() == 0) {
                    maxStretch = -1;
                } else {
                    //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
                    maxStretch = (1/(double)activityList.size()) * potentialStretch;
                }
                //*/
            
                if(maxStretch >= remainingTime && (totalCost >= minCost || availableMoney == 0)) {
                    double stretchPercent = remainingTime/potentialStretch;
                    for(int i = 0; i < activityList.size(); i++) {
                        Activity temp = activityList.get(i);
                       temp.setActualTime(5 *(Math.round((temp.getIdealTime() + (stretchPercent * temp.getTimeGap()))/5)));
                   }
                    
                } else if((totalCost < minCost) && costPercent > 0 && availableMoney > 0) {
                    if(minCost >= 5.0) { //minimum cost other than free
                        generatePlan(databaseCopy, availableTime, availableMoney, Math.abs(costPercent/2)); //log decrease
                    } else {
                        generatePlan(databaseCopy, availableTime, availableMoney, 0.0); //goes to 0
                    }
                } else {
                    if(listSizeDecreased) {
                        //System.out.println("The list size decreased, trying again");
                        generatePlan(databaseCopy, availableTime, availableMoney, costPercent);
                    } else {
                        displayErrorMessage("Unable to generate activity plan to fit all parameters");
                        return false;
                    }
                }
            }
        return true;
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
