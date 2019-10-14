import java.util.*;

public class Plan {

	//Fields
    
    //List of ordered Activities that make up the plan
	private ArrayList<Activity> activityList;
	
	//Default Constructor
	public Plan() {
		this.activityList = new ArrayList<Activity>();
	}
	
	//Methods
	public void generatePlan(ArrayList<Activity> database, double availableTime, double 
	availableMoney){
        //replace with our algorithm
		
		double totalCost = 0.0;
		double totalTime = 0.0;
		
		//while(totalTime <= availableTime && totalCost <= availableMoney) {
			//Add activities to this.activityList based on smart decisions
		//}
        
        System.out.println("Button Pressed"); //TEST *****
        //End TEST *****
	}
	
	//Getters and setters
    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }
    
    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }
}
