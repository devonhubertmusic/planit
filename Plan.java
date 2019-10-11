import java.util.*;

public class Plan {

	//Fields
	ArrayList<Activity> activityList;
	
	//Default Constructor
	public Plan() {
		this.activityList = new ArrayList<Activity>();
	}
	
	//Methods
	public void generatePlan(ArrayList<Activity> database, double availableTime, double 
	availableMoney){
		this.activityList = new ArrayList<Activity>(); //replace with our algorithm
		
		double totalCost = 0.0;
		double totalTime = 0.0;
		
		while(totalTime <= availableTime && totalCost <= availableMoney) {
			//Add activity to this.activityList
		}
	}
	
	//Getters and setters
	

}
