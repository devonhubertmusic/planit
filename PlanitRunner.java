import java.util.*;

public class PlanitRunner
{
	//Fields:
	ArrayList<Activity> database = null;


    public static void main(String[] args) {
    
    	MainWindow mainWindow = new MainWindow("Planit");
        mainWindow.setSize(500, 500);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        
        System.out.println("It Works!"); //Test
        
        Activity goShopping = new Activity();
        goShopping.setName("Go Shopping");
        goShopping.setMaxTime(120.00);
        goShopping.setIdealTime(90.00);
        
        Plan testPlan = new Plan();
        
        testPlan.generatePlan();
        
        ArrayList<Activity> activityList = testPlan.activityList;
        
        activityList.add(goShopping);
        
        System.out.println(activityList);

    }

//Methods

	public void getInfo() {
		this.database = new ArrayList<Activity>();
		//Loop: prompt the user for each activity they like, along with data fields 
		//for each activity (name, maxCost, maxTime, idealTime, etc.)
	}
	
	public void loadData() { //throw...
		//load data from existing database on user's computer (if it exists) 
		//to this.database
	}
	
	public void saveData() { //throw...
		//save current data from database onto user's computer
	}


}
