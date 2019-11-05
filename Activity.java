import java.util.*;

public class Activity {

	//Fields
	private String name; //Activity name
	private double maxTime; //Maximum time activity can take (while still being fun)
	private double idealTime; //Ideal time for the activity
    private double actualTime; //Actual time activity will take
	private double maxCost; //Upper bound of cost for this activity
	
	//Future field ideas: location, funLevel, etc.
	
	//Default constructor
	public Activity() {
		//Initialize fields
		this.name = "";
		this.maxTime = 0.0;
		this.idealTime = 0.0;
        this.actualTime = 0.0;
		this.maxCost = 0.0;
	}
    
    public Activity(String name, int maxTime, int idealTime, int maxCost) {
        //Set fields based on explicit data
        this.actualTime = 0.0;
        this.name = name;
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
        
        if((this.name).equalsIgnoreCase(that.name) /*&& this.maxTime == that.maxTime
           && this.idealTime == that.idealTime && this.actualTime == that.actualTime
           && this.maxCost == that.maxCost */) {
            return true;
        } else {
            return false;
        }
    }
    
    //Returns a new Activity that has the same fields as the current Activity
    public Activity copy() {
        Activity copy = new Activity();
        copy.setName(this.name);
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
}
