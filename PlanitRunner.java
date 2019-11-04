import java.util.*;
import java.io.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    public static ArrayList<Activity> database = getactivityList();
	private static MainWindow mainWindow;

    public static void main(String[] args)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets user's screen size
            int width = screenSize.width; //width of user's screen
            int height = screenSize.height; //height of user's screen
            mainWindow = new MainWindow("Plan-it"); //creates main window for user
            mainWindow.setSize(width, height); //sets size of main window
            mainWindow.setResizable(true); //allows user to resize main window
            mainWindow.setVisible(true); //makes main window visible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the connection
  public static Connection getConnection()
  {
      Connection con;
      try {
          con = DriverManager.getConnection("jdbc:mysql://206.189.165.197:3306/activity"+
           "?verifyServerCertificate=false&useSSL=true&requireSSL=true","user","password");
          return con;
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }


// get a list of activities from mysql database
  public static ArrayList<Activity> getactivityList()
  {
      ArrayList<Activity> activityList = new ArrayList<Activity>();
      Connection connection = getConnection();
      
      String query = "SELECT * FROM  activities";
      Statement st;
      ResultSet rs;

      try {
          st = connection.createStatement();
          rs = st.executeQuery(query);
          Activity activity;
          while(rs.next())
          {
              activity = new Activity(rs.getString("name"),rs.getInt("maxTime"),rs.getInt("idealTime"),rs.getInt("maxCost"));
              activityList.add(activity);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return activityList;
  }
    //Overwrite "save file" with current database information
	public static void saveData(ArrayList<Activity> database)
    throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        try {
            if(database != null) {
                String text = "";
               for(int i = 0; i < database.size(); i++) {
                    Activity temp = database.get(i);
            
                    text += "\n" + temp.getName();
                    text += "\n" + temp.getMaxTime();
                    text += "\n" + temp.getIdealTime();
                    text += "\n" + temp.getMaxCost();
                    text += "\n";
                }
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    ("database.txt"), false), "utf-8"))) {
                    writer.write(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
