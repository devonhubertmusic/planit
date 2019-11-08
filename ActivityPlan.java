import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ActivityPlan
{
    JFrame f;
    double availableTime;
    double availableMoney;
    
    //Default constructor
    public ActivityPlan() {
        this.f = null;
        this.availableTime = 0.0;
        this.availableMoney = 0.0;
    }
    
    //Explicit constructor
    public ActivityPlan(double availableTime, double availableMoney){
        this.availableTime = availableTime;
        this.availableMoney = availableMoney;
        
        f = new JFrame(); //Window
        f.setLayout(new FlowLayout()); //Layout
        f.setTitle("Plan Generator"); //Window title
        f.setSize(500, 600); //Size
        
        //Title
        JLabel header = new JLabel("My Activity Plan");
        f.add(header);
        header.setFont(new Font("Helvetica", Font.PLAIN, 25));
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        //***********************************************************************
        //Activity plan generation and display
        Plan myPlan = new Plan();
        myPlan.generatePlan(PlanitRunner.database, this.availableTime, this.availableMoney, 0.75);
        ArrayList<Activity> myActivityList = myPlan.getActivityList();
        
        int rowSize = myActivityList.size();
        int columnSize = 3;
        String[][] data = new String[rowSize][columnSize];
        
        double totalTime = 0.0;
        double totalCost = 0.0;
        
        //Fill table with data from generated activity plan
        for(int row = 0; row < rowSize; row++) {
            for(int column = 1; column <= columnSize; column++) {
                Activity temp = myActivityList.get(row);
                if(column == 1) {
                    data[row][column - 1] = "" + temp.getName();
                } else if(column == 2) {
                    double time = temp.getActualTime();
                    totalTime += time;
                    data[row][column - 1] = doubleToTime(time);
                } else if(column == 3) {
                    double cost = temp.getMaxCost();
                    totalCost += cost;
                    if(cost == 0) {
                        data[row][column - 1] = "Free!";
                    } else {
                        data[row][column - 1] = "$" + cost;
                    }
                } else {
                    System.out.println("Invalid Column Number");
                }
            } 
        }
        
        String[] columnNames = { "Activity", "Time", "Cost" };
        
        JTable j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);
        
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
            
        JLabel timeLabel = new JLabel("Total Time: " + doubleToTime(totalTime));
        f.add(timeLabel);
        timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            
        JLabel costLabel = new JLabel("Total Cost: $" + totalCost);
        f.add(costLabel);
        costLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        costLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        //***********************************************************************
       
        JButton save = new JButton("Save Plan");
        f.add(save);
        
        f.setVisible(true);
    
}

    //Converts a double time value in minutes to hours, minutes, etc.
   public String doubleToTime(double rawTime){
        String timeString = "";
        int hours = (int) (rawTime/60);
        int minutes = (int) (rawTime % 60);
        if(hours > 0) {
            if(hours == 1) {
                timeString += "" + hours + " hour";
            } else {
                timeString += "" + hours + " hours";
            }
            if(minutes != 0) {
                timeString += ", ";
            }
        }
        if(minutes > 0) {
            timeString += "" + minutes + " minutes";
        }
        
        return timeString;
    }
}
