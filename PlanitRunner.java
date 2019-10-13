import java.util.*;

public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    private static ArrayList<Activity> database;
    private static MainWindow mainWindow;

    public static void main(String[] args) {
    
    	//if no existing "save file" found, run gatherInfo() to prompt user
        
    	//else, run loadData() to load in relevant data from "save file"
        
        mainWindow = new MainWindow("Plan-it");
        mainWindow.setSize(500, 500);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);

    }

	//Methods
	public static void gatherInfo() {
		//initialize database as an empty list of activities
		database = new ArrayList<Activity>();
		
		//Loop: prompt the user for each activity they like, along with data fields 
		//for each activity (name, maxCost, maxTime, idealTime, etc.)
		
        //TEST ************************************
        Activity test1 = new Activity();
        test1.setName("Walk the Dog");
        test1.setMaxTime(60.0);
        test1.setIdealTime(30.0);
        test1.setMaxCost(0.0);
        database.add(test1);
        
        Activity test2 = new Activity();
        test2.setName("Go Shopping");
        test2.setMaxTime(120.0);
        test2.setIdealTime(60.0);
        test2.setMaxCost(200.0);
        database.add(test2);
        
        Activity test3 = new Activity();
        test3.setName("Do Yoga");
        test3.setMaxTime(90.0);
        test3.setIdealTime(50.0);
        test3.setMaxCost(0.0);
        database.add(test3);
        
        Activity test4 = new Activity();
        test4.setName("Go to the Movies");
        test4.setMaxTime(200.0);
        test4.setIdealTime(150.0);
        test4.setMaxCost(30.0);
        database.add(test4);
        
        Activity test5 = new Activity();
        test5.setName("Go Whalewatching");
        test5.setMaxTime(240.0);
        test5.setIdealTime(200.0);
        test5.setMaxCost(50.0);
        database.add(test5);
        //END TEST ********************************
        
        
		//updates the MainWindow with database data
        
		mainWindow.updateDatabase(database);
        
	}
	
    //Check for existing "save file", and if found, load in data to create database
    //and return true, else return false
	public static boolean loadData() { //throw fileNotFoundException
		//load data from existing database on user's computer (if it exists) 
		//to this.database
        
        return false;
	}
	
    //Overwrite "save file" with current database information
	public static void saveData() { //throw...
        //if(database != null) {
        String text = "";
        
        /*
        for(int i = 0; i < database.size(); i++) {
            Activity temp = database.get(i);
            
            text += "\n" + temp.getName();
            text += "\n" + temp.getMaxTime();
            text += "\n" + temp.getIdealTime();
            text += "\n" + temp.getMaxCost();
            text += "\n";
            
            System.out.println("Text is: \n" + text);
        }
        */
        
            FileWriter writer = new FileWriter();
            try {
                writer.writeToFile(text);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        //}
	}


}
