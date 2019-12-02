package planit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.GroupLayout.Alignment.*; 
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

/**
* ActivityPlan handles displaying generated plans
* with an option to save the information as pdf.
*/
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
    public ActivityPlan(double availableTime, double availableMoney, final int screenHeight){
        this.availableTime = availableTime;
        this.availableMoney = availableMoney;
        ArrayList<Activity> myList = getPlanList(0.75); //Not a magic number, I swear
        if(myList.size() > 0) {
            this.activityPlanFrame = makeWindow(myList, screenHeight);
        }
    }
    
    //Returns a JFrame window with size, titles, and layout, etc. set
    public JFrame makeWindow(final ArrayList<Activity> activityList, final int screenHeight) {
    	JFrame activityPlanFrame = new JFrame(); //Window
        //Set background image of window
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/images/space.jpg"));
            activityPlanFrame.setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 2, 2, d.width-4, d.height-4, null);
                }
            });
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        int windowHeight = (7*screenHeight)/8;
    	activityPlanFrame.setLayout(new FlowLayout()); //Layout
        activityPlanFrame.setTitle("Plan Generator"); //Window title
        activityPlanFrame.setSize(windowHeight, windowHeight); //Size
        activityPlanFrame.setMinimumSize(new Dimension(windowHeight, windowHeight));
     
        //creates the table with data from generated plan
        String dataTable[][] = makeAndDisplayPlanTable(activityList);  
        String[] columnNames = {"Activity", "Time", "Cost"};
        JTable displayPlan = new JTable(dataTable, columnNames);
        displayPlan.setFont(new java.awt.Font("Helvetica", Font.PLAIN,15));
        displayPlan.setRowHeight(30);
        displayPlan.getColumnModel().getColumn(0).setPreferredWidth(200);
        displayPlan.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(displayPlan);
        scrollPane.setViewportView(displayPlan);

       //creates title for the page and sets its size,title, color,etc.
        JLabel header = new JLabel("My Activity Plan");
        header.setFont(new Font("Helvetica", Font.PLAIN, 25));
        header.setForeground(java.awt.Color.WHITE);
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        //sets up the save button that will call JFileChooser and iText functions
        JButton save = new JButton("Save Plan");
        save.setFont(new Font("Helvetica", 1, 14));
        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png")));
        JLabel timeLabel = new JLabel("Total Time: " + doubleToTime(totalTime));
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        timeLabel.setForeground(java.awt.Color.WHITE);
        JLabel costLabel = new JLabel("Total Cost: $" + totalCost);
        costLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        costLabel.setForeground(java.awt.Color.WHITE);
        
        save.addActionListener( new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
          	JavaPDF PlanPdf = new JavaPDF();
          	PlanPdf.printPdf(activityList);
          }
        });
          
        activityPlanFrame.setVisible(true);
  
        //sets the layout to add the components, so that they are neatly placed.
        GroupLayout layout = new GroupLayout(activityPlanFrame.getContentPane());
        activityPlanFrame.getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
    		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
    		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(header)             
    			.addGroup(layout.createSequentialGroup()
    					.addGap(50,50,50)
    					.addComponent(scrollPane)
                        .addGap(50,50,50))
    			.addComponent(costLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
                .addComponent(timeLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
    			.addComponent(save))
    	);

        layout.setVerticalGroup( 
            layout.createSequentialGroup()
            .addGroup(layout.createSequentialGroup()
                .addGap(20,20,20)
                .addComponent(header)
                .addGap(20,20,20)
                .addContainerGap()
                .addComponent(scrollPane)
                .addGap(30,30,30)
                .addComponent(costLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
                .addComponent(timeLabel,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE)
                .addGap(60,60,60)
                .addComponent(save)
                .addGap(30,30,30))
        );
            
            
        activityPlanFrame.pack();

        return activityPlanFrame;
    }
    
    //Returns a new activity plan ArrayList, given a minimum desired percent of available money
    public ArrayList<Activity> getPlanList(double minCostPercent) {
        Plan myPlan = new Plan();
        boolean generated = myPlan.generatePlan(PlanitRunner.database, this.availableTime, this.availableMoney, minCostPercent);
        ArrayList<Activity> myActivityList = new ArrayList<Activity>();
        if(generated) {
            myActivityList = myPlan.getActivityList();
        }
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
