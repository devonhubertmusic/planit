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

public class activityPlan extends JFrame //implements ActionListener
{
    public activityPlan(){
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        setTitle("Plan Generator");
        setSize(500, 600);
        setVisible(false);
	
            addWindowListener(new WindowAdapter()
            {
                    public void windowClosing(WindowEvent e)
                {
                    dispose();
                }
            });
        
        
        //Title
        JLabel header = new JLabel("My Activity Plan");
        add(header);
        header.setFont(new Font("Helvetica", Font.PLAIN, 25));
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        
        //***********************************************************************
        //Activity plan generation and display
        
        double availableTime = 60.0; //FOR TESTING, NEEDS TO DRAW FROM USER INPUT
        double availableMoney = 20.0; //FOR TESTING, NEEDS TO DRAW FROM USER INPUT
        Plan myPlan = new Plan();
        myPlan.generatePlan(PlanitRunner.database, availableTime, availableMoney);
        ArrayList<Activity> myActivityList = myPlan.getActivityList();
        
        int rowSize = myActivityList.size();
        int columnSize = 3;
        String[][] data = new String[rowSize][columnSize];
        
        double totalTime = 0.0; //Keep track of total time of activity list, REPLACE WITH Plan.getTotalTime() METHOD
        double totalCost = 0.0; //Keep track of total cost of activity list, REPLACE WITH Plan.getTotalCost() METHOD
        
        
        //Fill table with data from generated activity plan
        for(int row = 0; row < rowSize; row++) {
            for(int column = 1; column <= columnSize; column++) {
                Activity temp = myActivityList.get(row);
                if(column == 1) {
                    data[row][column - 1] = "" + temp.getName();
                } else if(column == 2) {
                    
                    //convert raw time in minutes to hours, minutes, etc.
                    String timeString = "";
                    double rawTime = temp.getIdealTime();
                    totalTime += rawTime;
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
                    
                    data[row][column - 1] = timeString;
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
                
            } //end for loop 2
        } //end for loop 1
        
        String[] columnNames = { "Activity", "Time", "Cost" };
        
        JTable j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);
        
        JScrollPane sp = new JScrollPane(j);
        add(sp);
            
        JLabel timeLabel = new JLabel("Total Time: " + totalTime + " Minutes");
        add(timeLabel);
        timeLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            
        JLabel costLabel = new JLabel("Total Cost: $" + totalCost);
        add(costLabel);
        costLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        costLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            
        
        //***********************************************************************
        
     

        //dummy buttons!
	    JButton save = new JButton("Save Plan");
        add(save);
	
        JButton print = new JButton("Print Plan");
        add(print);
        
    }

    //@Override
    //public void actionPerformed(ActionEvent evt){
        
            
    //}
    
}
