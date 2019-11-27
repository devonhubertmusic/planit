package planit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.GroupLayout.Alignment.*; //
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
import javax.swing.JOptionPane;

public class ActivityPlan
{
    JFrame activityPlanFrame;
    double availableTime;
    double availableMoney;
     double totalTime;
     double totalCost;
    
    //Default constructor
    public ActivityPlan() {
        this.activityPlanFrame = null;
        this.availableTime = 0.0;
        this.availableMoney = 0.0;
        double totalTime = 0.0;
        double totalCost = 0.0;
        
    }
    
    //Explicit constructor
    public ActivityPlan(double availableTime, double availableMoney){
        this.availableTime = availableTime;
        this.availableMoney = availableMoney;
       
      ArrayList<Activity> myList = getPlanList(0.75);
      this.activityPlanFrame = makeWindow(myList);
    }
    
    //Returns a JFrame window with size, titles, and layout, etc. set
    public JFrame makeWindow(final ArrayList<Activity> activityList) {
  
    	JFrame activityPlanFrame = new JFrame(); //Window
    	activityPlanFrame.setLayout(new FlowLayout()); //Layout
        activityPlanFrame.setTitle("Plan Generator"); //Window title
        activityPlanFrame.setSize(600, 700); //Size
        
        JLabel header = new JLabel("My Activity Plan"); //Title
        header.setFont(new Font("Helvetica", Font.PLAIN, 25));
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        JButton save = new JButton("Save Plan");
        save.setFont(new Font("Helvetica", 1, 14));
        //save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png")));
        JLabel timeLabel = new JLabel("Total Time: " + doubleToTime(totalTime));
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        String dataTable[][] = makeAndDisplayPlanTable(activityList);  
    String[] columnNames = {"Activity", "Time", "Cost"};
        
        JTable j = new JTable(dataTable, columnNames);
        j.setFont(new java.awt.Font("Helvetica", Font.PLAIN,15));
      //  j.setBackground(new java.awt.Color(40,40,40));
        j.setRowHeight(30);
        j.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(j);
     //  scrollPane.setViewportView(j);
        JLabel costLabel = new JLabel("Total Cost: $" + totalCost);
        costLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        
          save.addActionListener( new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
              	JavaPDF PlanPdf = new JavaPDF();
              	PlanPdf.printPdf(activityList);
              }
              });
          
        activityPlanFrame.setVisible(true);
  
    GroupLayout layout = new GroupLayout(activityPlanFrame.getContentPane());
    activityPlanFrame.getContentPane().setLayout(layout);
    
    layout.setHorizontalGroup(
    		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
    		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
    	       //      .addGap(25,25,25)
    	             .addComponent(header))
    			.addGroup(layout.createParallelGroup()
    					.addGap(100,100,100)
    					.addComponent(scrollPane))
    			.addComponent(costLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
    			.addGroup(layout.createParallelGroup()
    					.addGap(80,80,80))
    					//.addComponent(sp))
    				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
    				.addComponent(save))
    				//.addComponent(sp))
    				);

  //--NEED TO ADD SCROLL PANE STILL
            layout.setVerticalGroup( 
                layout.createSequentialGroup()
                .addGroup(layout.createSequentialGroup()
                		.addContainerGap()
                .addComponent(header))
                .addGroup(layout.createSequentialGroup()
          	             .addComponent(scrollPane))
                .addComponent(costLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
                .addGroup(layout.createSequentialGroup())
                .addGroup(layout.createSequentialGroup()
                		.addGap(100,100,100)
                 .addComponent(save))
            		);
            
            
          //activityPlanFrame.pack();
      //  activityPlan.getContentPane().setBackground(Color.GRAY);

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
    public String[][]  makeAndDisplayPlanTable(ArrayList<Activity> myActivityList) {
        int rowSize = myActivityList.size();
        int columnSize = 3;
        String[][] data = new String[rowSize][columnSize];
  
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
        return data;
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
