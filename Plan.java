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
