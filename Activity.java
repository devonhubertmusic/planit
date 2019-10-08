public class Activity {

	//Fields
	private String name; //Activity name
	private double maxTime; //Maximum time activity can take (while still being fun)
	private double idealTime; //Ideal time for the activity
	private double maxCost; //Upper bound of cost for this activity
	
	//Future field ideas: location, funLevel, etc.
	
	//Default constructor
	public Activity() {
		//Initialize fields
		this.name = "";
		this.maxTime = 0.0;
		this.idealTime = 0.0;
		this.maxCost = 0.0;
	}
	
	
	//Setters
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMaxTime(double maxTime) {
		this.maxTime = maxTime;
	}
	
	public void setIdealTime(double idealTime) {
		this.idealTime = idealTime;
	}
	
	public void setMaxCost(double maxCost) {
		this.maxCost = maxCost;
	}
	
	
	//Getters
	public String getName() {
		return this.name;
	}
	
	public double getMaxTime() {
		return this.maxTime;
	}
	
	public double getIdealTime() {
		return this.idealTime;
	}
	
	public double getMaxCost() {
		return this.maxCost;
	}
	
	//Other Methods
	
	public String toString() {
		return this.name;
	}
	
}
