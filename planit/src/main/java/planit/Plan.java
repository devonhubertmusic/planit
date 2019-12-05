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
* Plan contains the main algorithm behind generating a plan
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
            //List of cost/length is possible, but not necessarily with given parameters...
            
            //Deep copy of database, can be deleted from
            ArrayList<Activity> databaseCopy = copyDatabase(database);
            boolean listSizeDecreased = false; //Checks for list length decrease for recursion handling
            
            //Divides all activities into group by activity type
            ArrayList<ArrayList<Activity>> activityGroups = divideByActivityType(databaseCopy);
            
            //Remaining activities (no specified activity type, or "misc")
            ArrayList<Activity> leftOverActivities = activityGroups.get(0);
            activityGroups.remove(0);
            
                double totalTime = 0.0; //Total activity plan time
                double totalCost = 0.0; //Total activity plan cost
                double minCost = costPercent * availableMoney; //Ideal minimum cost of plan, starting at 75% of max cost
            
                //indices of "used" activity types (will cycle through when all used)
                ArrayList<Integer> usedActivityGroupIndices = new ArrayList<Integer>();
            
                int currentActivityGroupIndex = 0; //initialize to 0
            
                    //While there are still remaining activities to draw from
                    while((activityGroups.size() > 0 || leftOverActivities.size() > 0) && totalTime < availableTime) {
                        
                        //Current activity type group
                        ArrayList<Activity> currentActivityGroup;
                        
                        //If activity group activities run out, use a blank list in its place
                        if(activityGroups.size() == 0 && leftOverActivities.size() > 0) {
                            currentActivityGroup = new ArrayList<Activity>();
                        } else {
                            //If all activity group indices have been used...
                            if(usedActivityGroupIndices.size() >= activityGroups.size()) {
                                //Reset list of used indices
                                usedActivityGroupIndices = new ArrayList<Integer>();
                                
                                //Find a new random index to start at
                                Random nextRand = new Random();
                                currentActivityGroupIndex = nextRand.nextInt(activityGroups.size());
                                
                                //Add the new current index to the list of used indices
                                usedActivityGroupIndices.add(currentActivityGroupIndex);
                            } else {
                                //Not all indices have been used, finds a new one
                                
                                boolean nextIndexFound = false; //flag for finding new index
                                while(!nextIndexFound) {
                                    Random nextRand = new Random();
        
                                    //new random index to try (in range)
                                    int tempIndex = nextRand.nextInt(activityGroups.size());
                                    
                                    //If this index has not been used this time around
                                    if(!usedActivityGroupIndices.contains(tempIndex)) {
                                        //Index has been found!
                                        currentActivityGroupIndex = tempIndex;
                                        usedActivityGroupIndices.add(tempIndex);
                                        nextIndexFound = true;
                                    }
                                }
                            }
                            //Find the next activity group to pull from!
                            currentActivityGroup = activityGroups.get(currentActivityGroupIndex);
                        }
                    
                        //range of random integer for picking next activity
                        int randomRange = currentActivityGroup.size() + leftOverActivities.size();
                        Random rn = new Random();
                        int randomIndex = rn.nextInt(randomRange);
                        
                        //depending on the random number, draw from current activity group, or misc group
                        Activity currentActivity;
                        if(randomIndex < currentActivityGroup.size()) {
                            currentActivity = currentActivityGroup.get(randomIndex);
                        } else {
                            currentActivity = leftOverActivities.get(randomIndex - currentActivityGroup.size());
                        }
                        
                        //time and money left over after activity is added
                        double remainingTime = availableTime - totalTime;
                        double remainingMoney = availableMoney - totalCost;
                        
                        //Checks if current activity will fit remaining time and cost
                        if(currentActivity.getIdealTime() <= remainingTime
                           && currentActivity.getMaxCost() <= remainingMoney) {
                            //If it fits, add it!
                            activityList.add(currentActivity);
                            
                            //Update remaining time and cost based on new activity
                            totalTime += currentActivity.getIdealTime();
                            totalCost += currentActivity.getMaxCost();
                        } else {
                            //If it doesn't fit given time and budget, removes activity from database copy
                            if(currentActivity.getIdealTime() > availableTime || currentActivity.getMaxCost() > availableMoney) {
                                databaseCopy.remove(currentActivity);
                                
                                //List size was decreased
                                listSizeDecreased = true;
                            }
                        }
                        
                        //Remove current activity from correct group (whether or not it was chosen)
                        if(randomIndex < currentActivityGroup.size()) {
                            currentActivityGroup.remove(randomIndex);
                            if(currentActivityGroup.size() <= 0) {
                                activityGroups.remove(currentActivityGroupIndex);
                            }
                        } else {
                            leftOverActivities.remove(randomIndex - currentActivityGroup.size());
                        }
                    }
            
                //update remaining time and money
                double remainingTime = availableTime - totalTime;
                double remainingMoney = availableMoney - totalCost;
            
                //initialize potential time stretch to 0.0
                double potentialStretch = 0.0;
            
                //sum possible time increases (max time - ideal time) of all activities
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    potentialStretch += temp.getTimeGap();
                }
            
                //find max "stretch"
                double maxStretch;
                if(activityList.size() == 0) {
                    maxStretch = -1;
                } else {
                    //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
                    maxStretch = (1/(double)activityList.size()) * potentialStretch;
                }
    
                //suitable stretch is found!
                if(maxStretch >= remainingTime && (totalCost >= minCost || availableMoney == 0)) {
                    double stretchPercent = remainingTime/potentialStretch;
                    for(int i = 0; i < activityList.size(); i++) {
                        Activity temp = activityList.get(i);
                       temp.setActualTime(5 *(Math.round((temp.getIdealTime() + (stretchPercent * temp.getTimeGap()))/5)));
                   }
                    
                //if no solution found, recurse with more lenient minimum cost percent
                } else if((totalCost < minCost) && costPercent > 0 && availableMoney > 0) {
                    if(minCost >= 5.0) { //minimum cost other than free
                        generatePlan(databaseCopy, availableTime, availableMoney, Math.abs(costPercent/2)); //log decrease
                    } else {
                        generatePlan(databaseCopy, availableTime, availableMoney, 0.0); //goes to 0
                    }
                } else {
                    if(listSizeDecreased) {
                        //if still no solution found, recurse only if running on a smaller list
                        //(prevents infinite loop!)
                        generatePlan(databaseCopy, availableTime, availableMoney, costPercent);
                    } else {
                        //It cannot be done :(
                        displayErrorMessage("Unable to generate activity plan to fit all parameters");
                        return false;
                    }
                }
            }
        return true;
	}
    
    //Display the given error message as an alert
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }
    
    //Maximum time possible for an activity list
    public double findMaxPossibleActivityTime(ArrayList<Activity> activities) {
        double totalTime = 0.0;
        for(int index = 0; index < activities.size(); index++) {
            Activity currentActivity = activities.get(index);
            totalTime += currentActivity.getMaxTime();
        }
        return totalTime;
    }
    
    //Minimum possible cost for an activity list
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
    
    //Initiliazes the activity list, and updates the database
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
