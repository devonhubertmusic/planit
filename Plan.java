import java.util.*;

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
	availableMoney){
        this.activityList = new ArrayList<Activity>();
        if(this.tryCounter > PlanitRunner.database.size()) {
            Activity temp = new Activity();
            temp.setName("Unable to generate plan");
            this.activityList.add(temp);
        } else {
            ArrayList<Activity> databaseCopy = copyDatabase(database);
        
            double totalTime = 0.0;
            double totalCost = 0.0;
         
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
        
            double maxStretch = 0.25 * potentialStretch; //% DECIDES HOW FAR FROM IDEAL TIME WE ARE WILLING TO STRETCH
         
            if(maxStretch >= remainingTime) {
                double stretchPercent = remainingTime/potentialStretch;
                for(int i = 0; i < activityList.size(); i++) {
                    Activity temp = activityList.get(i);
                    temp.setActualTime(temp.getIdealTime() + (stretchPercent * temp.getTimeGap()));
                }
            } else {
                //Try again!
                //if there is remaining money but no remaining time, break from loop, this is ideal 
                if(remainingMoney > 0 && availableTime == 0)
                    return;
                else
                this.tryCounter++;
                generatePlan(database, availableTime, availableMoney);
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
