import java.util.*;

public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    private static ArrayList<Activity> database;

    public static void main(String[] args) {
    
    	//if no existing "save file" found, run gatherInfo() to prompt user
        
    	//else, run loadData() to load in relevant data from "save file"
        
        MainWindow mainWindow = new MainWindow("Plan-it");
        mainWindow.setSize(500, 500);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);

    }

	//Methods
	public void gatherInfo() {
		//initialize database as an empty list of activities
		this.database = new ArrayList<Activity>();
		
		//Loop: prompt the user for each activity they like, along with data fields 
		//for each activity (name, maxCost, maxTime, idealTime, etc.)
		
		//updates the MainWindow with database data
		//mainWindow.updateDatabase(this.database);
	}
	
    //Check for existing "save file", and if found, load in data to create database
    //and return true, else return false
	public boolean loadData() { //throw...
		//load data from existing database on user's computer (if it exists) 
		//to this.database
        
        return false;
	}
	
    //Create/overwrite "save file" with current database information
	public static void saveData() { //throw...
		//save current data from database onto user's computer
        //if(database != null) {
            String text = "Test";
            FileWriter writer = new FileWriter();
            try {
                System.out.println("Saving data 2");
                writer.writeToFile(text);
            } catch (java.io.IOException e1) {
                throw new RuntimeException(e1);
            }
        //}
	}


}
