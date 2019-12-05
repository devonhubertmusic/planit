package planit;

import java.util.*;

/**
* A single activity, used to populate activity plans
*/
public class Activity implements Comparable<Activity>
{
	//Fields
	private String name; //Activity name
    private String activityType; //Activity type
	private double maxTime; //Maximum time activity can take (while still being fun)
	private double idealTime; //Ideal time for the activity
    private double actualTime; //Actual time activity will take
	private double maxCost; //Upper bound of cost for this activity
	
	//Default constructor
	public Activity() {
		//Initialize fields
		this.name = "";
        this.activityType = "";
		this.maxTime = 0.0;
		this.idealTime = 0.0;
        this.actualTime = 0.0;
		this.maxCost = 0.0;
	}
    
    public Activity(String name, String activityType, int maxTime, int idealTime, int maxCost) {
        //Set fields based on explicit data
        this.actualTime = 0.0;
        this.name = name;
        this.activityType = activityType;
        this.maxTime = maxTime;
        this.idealTime = idealTime;
        this.maxCost = maxCost;
    
    }
    
    //Methods:
    
    //Returns true if this Activity is equivalent to another Activity
    public boolean equals(Object x) {
        Activity that;
        if(x instanceof Activity) {
            that = (Activity) x;
        } else {
            return false;
        }
        
        if((this.name).equalsIgnoreCase(that.name)) {
            return true;
        } else {
            return false;
        }
    }
    
    //Returns a new Activity that has the same fields as the current Activity
    public Activity copy() {
        Activity copy = new Activity();
        copy.setName(this.name);
        copy.setActivityType(this.activityType);
        copy.setMaxTime(this.maxTime);
        copy.setIdealTime(this.idealTime);
        copy.setMaxCost(this.maxCost);
        return copy;
    }
	
    //String representation of this Activity
	public String toString() {
		return this.name;
	}
    
    //Setters:
    
    public void setName(String name) {
        this.name = name;
    }

    public void setActivityType(String activityType){
        this.activityType = activityType;
    }
    
    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }
    
    public void setIdealTime(double idealTime) {
        this.idealTime = idealTime;
    }
    
    public void setActualTime(double actualTime) {
        this.actualTime = actualTime;
    }
    
    public void setMaxCost(double maxCost) {
        this.maxCost = maxCost;
    }
    
    //Getters:
    
    public String getName() {
        return this.name;
    }

    public String getActivityType(){
        return this.activityType;
    }
    
    public double getMaxTime() {
        return this.maxTime;
    }
    
    public double getIdealTime() {
        return this.idealTime;
    }
    
    public double getActualTime() {
        return this.actualTime;
    }
    
    public double getTimeGap() {
        return (this.maxTime - this.idealTime);
    }
    
    public double getMaxCost() {
        return this.maxCost;
    }
    
    public double getCostPerHour() {
        return (this.maxCost/this.idealTime)/60.0;
    }
    
    //Compare to another activity (based on cost per hour) for sorting
    public int compareTo(Activity that) {
        if (this.getCostPerHour() == that.getCostPerHour()) {
            return 0;
        } else if(this.getCostPerHour() > that.getCostPerHour()){
            return 1;
        } else {
            return -1;
        }
    }
}
