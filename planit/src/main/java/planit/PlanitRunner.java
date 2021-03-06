package planit;

import java.util.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
* PlanitRunner is the main class that creates
* the main window.
*/
public class PlanitRunner
{
	//Fields
    
    //The main "database" of activities, collected from user input using gatherInfo()
    public static ArrayList<Activity> database;
	private static MainWindow mainWindow;

    public static void main(String[] args)
    {
        
        try {
            boolean updated = false;
            do {
                updated = updateActivityList();
            } while(!updated);
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets user's screen size
            final int width = screenSize.width; //width of user's screen
            final int height = screenSize.height; //height of user's screen
            mainWindow = new MainWindow("Plan-it", height); //creates main window for user
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

// update current list of activities from mysql database
  public static boolean updateActivityList()
  {
      database = new ArrayList<Activity>();
      ArrayList<Activity> activityList = new ArrayList<Activity>();
      Connection connection = getConnection();
      
      if(connection != null) {
          String query = "SELECT * FROM  activities";
          Statement st;
          ResultSet rs;

          try {
              st = connection.createStatement();
              rs = st.executeQuery(query);
              Activity activity;
              while(rs.next())
              {
                  activity = new Activity(rs.getString("name"), rs.getString("activityType"),
                             rs.getInt("maxTime"),rs.getInt("idealTime"),rs.getInt("maxCost"));
                  activityList.add(activity);
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          database = activityList;
          return true;
      } else {
          JOptionPane.showMessageDialog(null, "Unable to connect to Database. Please check internet connection.");
          return false;
      }
  }
}
