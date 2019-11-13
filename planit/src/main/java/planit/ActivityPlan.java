package planit;

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
    JFrame activityPlanFrame;
    double availableTime;
    double availableMoney;
    
    //Default constructor
    public ActivityPlan() {
        this.activityPlanFrame = null;
        this.availableTime = 0.0;
        this.availableMoney = 0.0;
    }
    
    //Explicit constructor
    public ActivityPlan(double availableTime, double availableMoney){
        this.availableTime = availableTime;
        this.availableMoney = availableMoney;
        this.activityPlanFrame = makeWindow();
        makeAndDisplayPlanTable(getPlanList(0.75));
        completeWindow();
    }
    
    //Returns a JFrame window with size, titles, and layout, etc. set
    public JFrame makeWindow() {
        JFrame activityPlanFrame = new JFrame(); //Window
        activityPlanFrame.setLayout(new FlowLayout()); //Layout
        activityPlanFrame.setTitle("Plan Generator"); //Window title
        activityPlanFrame.setSize(500, 600); //Size
        
        JLabel header = new JLabel("My Activity Plan"); //Title
        activityPlanFrame.add(header);
        header.setFont(new Font("Helvetica", Font.PLAIN, 25));
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        return activityPlanFrame;
    }
    
    //Returns a new activity plan ArrayList, given a minimum desired percent of available money
    public ArrayList<Activity> getPlanList(double minCostPercent) {
        Plan myPlan = new Plan();
        myPlan.generatePlan(PlanitRunner.database, this.availableTime, this.availableMoney, minCostPercent);
        ArrayList<Activity> myActivityList = myPlan.getActivityList();
        return myActivityList;
    }
    
    //Given an activity plan ArrayList, creates and displays a formatted table of these activities,
    //along with their total time and cost
    public void makeAndDisplayPlanTable(ArrayList<Activity> myActivityList) {
        int rowSize = myActivityList.size();
        int columnSize = 3;
        String[][] data = new String[rowSize][columnSize];
        
        double totalTime = 0.0;
        double totalCost = 0.0;
        
        //Fill table with data from activity plan
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
        
        String[] columnNames = {"Activity", "Time", "Cost"};
        
        JTable j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(j);
        this.activityPlanFrame.add(sp);
        
        JLabel timeLabel = new JLabel("Total Time: " + doubleToTime(totalTime));
        this.activityPlanFrame.add(timeLabel);
        timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        
        JLabel costLabel = new JLabel("Total Cost: $" + totalCost);
        this.activityPlanFrame.add(costLabel);
        costLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        costLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
    }
    
    //Adds final buttons to the main window, and makes the window visible
    public void completeWindow() {
        JButton save = new JButton("Save Plan");
        save.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new JavaPDF();
            }	
            });
        this.activityPlanFrame.add(save);
        this.activityPlanFrame.setVisible(true);
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
