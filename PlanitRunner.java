import java.util.*;
import java.io.*;
import java.awt.Toolkit;
import java.awt.Dimension;

public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    public static ArrayList<Activity> database = new ArrayList<Activity>();
    private static MainWindow mainWindow;

    public static void main(String[] args)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;
            mainWindow = new MainWindow("Plan-it");
            mainWindow.setSize(width, height);
            mainWindow.setResizable(true);
            mainWindow.setVisible(true);

            boolean dataLoaded = loadData(); //true if data loaded successfully
        
            System.out.println(database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
    //Check for existing "save file", and if found, load in data to create database
    //and return true, else return false
	public static boolean loadData()
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
		//load data from existing database on user's computer (if it exists) 
		//to this.database
        try {
            File file = new File("Database.txt");
            if(file.exists()) {
                Scanner scanner = new Scanner(file);
            
                while(scanner.hasNextLine()) {
                    scanner.nextLine();
                
                    Activity temp = new Activity();
                    String name = scanner.nextLine();
                    //System.out.println(name);
                    temp.setName(name);
                
                    double maxTime = Double.parseDouble(scanner.nextLine());
                    //System.out.println(maxTime);
                    temp.setMaxTime(maxTime);
                
                    double idealTime = Double.parseDouble(scanner.nextLine());
                    //System.out.println(idealTime);
                    temp.setIdealTime(idealTime);
                
                    double maxCost = Double.parseDouble(scanner.nextLine());
                    //System.out.println(maxCost);
                    temp.setMaxCost(maxCost);
                
                    database.add(temp);
                }
                return true;
            } else {
                return false;
            }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        return false;
	}
	
    //Overwrite "save file" with current database information
	public static void saveData(ArrayList<Activity> database)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            if(database != null) {
            
                String text = "\n";
                for(int i = 0; i < database.size(); i++) {
                    Activity temp = database.get(i);
            
                    text += "\n" + temp.getName();
                    text += "\n" + temp.getMaxTime();
                    text += "\n" + temp.getIdealTime();
                    text += "\n" + temp.getMaxCost();
                    text += "\n";
                }
        
            
                Writer writer = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(("Database.txt"), false), "utf-8"));
                writer.write(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}


}
