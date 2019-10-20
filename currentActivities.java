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
import javax.swing.JButton;

public class currentActivities {
    JFrame f;
    JLabel allAct;
    JButton edit;
    String[][] data;
    JTable j;
    JScrollPane sp;

    currentActivities()
    {
        f = new JFrame(); //Window
        f.setLayout( new FlowLayout()); //Layout
        f.setTitle("Current Activities"); //Window title
        
        allAct = new JLabel("All Actitivies"); //Window header
        allAct.setFont(new Font("Helvetica", Font.PLAIN, 25)); //Edit header font
        allAct.setAlignmentY(Component.CENTER_ALIGNMENT); //Align header to center
        f.add(allAct); //Add header to window
        
        edit = new JButton("Edit"); //"Edit" button
        f.add(edit); //Add edit button
        
        // Data to be displayed in the JTable
        int rowSize = PlanitRunner.database.size();
        
        int columnSize = 3;
        data = new String[rowSize][columnSize];

        //Fill table with corresponding data from PlanitRunner.database
        //REPLACE WITH DATA FROM MYSQL DATABASE
        for(int row = 0; row < rowSize; row++) {
            for(int column = 1; column <= columnSize; column++) {
                Activity temp = PlanitRunner.database.get(row);
                if(column == 1) {
                    data[row][column - 1] = "" + temp.getName();
                } else if(column == 2) {
                    data[row][column - 1] = doubleToTime(temp.getIdealTime());
                } else if(column == 3) {
                    double cost = temp.getMaxCost();
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

        String[] columnNames = { "Activity", "Ideal Time", "Max Cost" }; //Column names

        j = new JTable(data, columnNames); //Create table with database data, and column names
        j.setBounds(30, 40, 200, 300); //Table size boundaries

        sp = new JScrollPane(j); //Scroll bar for window
        f.add(sp); //Add scroll bar to window

        f.setSize(500, 600);
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
    
    public void actionPerformed(ActionEvent e){

    }
}
