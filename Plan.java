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
        /*******************_MAIN_ALGORITHM_PSEUDOCODE_*********************/
        /*
         
        ArrayList<Activity> databaseCopy = DEEP COPY OF DATABASE LIST;
        ArrayList<Activity> timeSorted = DATABASE LIST, SORTED BY IDEAL TIME; >>NEEDED?
         
        double totalTime = 0.0;
        double totalCost = 0.0;
         
        while(DATABASE LIST COPY IS NOT EMPTY) {
            double remainingTime = availableTime - totalTime;
            double remainingMoney = availableMoney - totalCost;
         
            Activity currentActivity = RANDOM ACTIVITY FROM DATABASE LIST COPY
            if(currentActivity.getIdealTime() <= remainingTime
               && currentActivity.getMaxCost() <= remainingMoney) {
                activityList.add(currentActivity);
                totalTime += currentActivity.getIdealTime();
                totalCost += currentActivity.getMaxCost();
            }
            REMOVE CURRENT ACTIVITY FROM DATABASE LIST COPY
        }
        (Database list copy is now empty)
         
        double potentialStretch = 0.0;
        for(int i = 0; i < activityList.size(); i++) {
            Activity temp = activityList.get(i);
            potentialStretch += temp.getTimeGap();
        }
        
        double maxStretch = 0.25 * potentialStretch; //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
         
        if(maxStretch >= remainingTime) {
            double stretchPercent = remainingTime/potentialStretch;
            for(int i = 0; i < activityList.size(); i++) {
                Activity temp = activityList.get(i);
                temp.setActualTime(temp.getIdealTime() + (stretchPercent * temp.getTimeGap()));
            }
        } else {
            REMOVE MOST EXPENSIVE ACTIVITY AND REPEAT ALGORITHM ON DATABASE - ACTIVITY REMOVED >> Make Recursive? Maybe...
        }
       */
       /**************************_END_PSEUDOCODE_**************************/
        
	}
	
	//Getters and setters
    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }
    
    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }
}
