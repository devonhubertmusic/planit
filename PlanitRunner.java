import java.util.*;

public class PlanitRunner
{
	//Fields:
	ArrayList<Activity> database = null;

    public static void main(String[] args) {
    
    	//if no external file... run gatherInfo()
    	//else, run loadData()
        
        MainWindow mainWindow = new MainWindow("Planit");
        mainWindow.setSize(500, 500);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);

    }

	//Methods

	public void gatherInfo() {
		//initialize new list of activities that form the database
		this.database = new ArrayList<Activity>();
		
		//Loop: prompt the user for each activity they like, along with data fields 
		//for each activity (name, maxCost, maxTime, idealTime, etc.)
		
		//updates the MainWindow with database data
		//mainWindow.updateDatabase(this.database);
	}
	
	public void loadData() { //throw...
		//load data from existing database on user's computer (if it exists) 
		//to this.database
	}
	
	public void saveData() { //throw...
		//save current data from database onto user's computer
	}


}
