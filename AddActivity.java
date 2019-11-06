import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import java.io.*;

public class AddActivity implements ItemListener {
	
	//New activity information
    String newActName;
    double newActMaxTime;
    double newActIdealTime;
    double newActMaxCost;
    
    JFrame f;

    //Dropdown boxes for user input
    JComboBox maxSelector, idealSelector, costSelector;
    //Labels to display maxTime, idealTime, maxCost, and availableMoney user input
    JLabel maxShow, idealShow, costShow;

    public AddActivity()
    {
    	//Initialize data fields
        newActName = "";
        newActMaxTime = 30.0;
        newActIdealTime = 30.0;
        newActMaxCost = 0.0;

        f = new JFrame(); //Window

        
        //Set background image of window
        try {
            final Image backgroundImage = ImageIO.read(getClass().getResourceAsStream("resources/images/space.jpg"));
            f.setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 1,1, d.width - 2, d.height- 2, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       

        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS)); //Layout
        f.setTitle("New Activity"); //Window title
        f.add(Box.createVerticalGlue());

        //Get data for new activity
        JLabel newActivity = new JLabel("Enter a New Activity:");
        newActivity.setAlignmentY(Component.CENTER_ALIGNMENT);
        f.add(newActivity);
        newActivity.setForeground(Color.WHITE);
        newActivity.setFont(new Font("Helvetica", Font.BOLD, 20));

        f.add(Box.createVerticalGlue());

        JPanel inputName = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 25));
        JLabel activityField = new JLabel("Activity name:");
        JLabel activityName = new JLabel("");
        inputName.add(activityField);
        inputName.add(textField);
        inputName.add(activityName);
        activityField.setForeground(Color.WHITE);
        activityField.setFont(new Font("Helvetica", Font.PLAIN, 16));
        activityName.setForeground(Color.WHITE);
        activityName.setFont(new Font("Helvetica", Font.PLAIN, 16));
        f.add(inputName);
        inputName.setOpaque(false);

        //time options (in hours)
        String timeOptions[] = { "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6" }; 
        String[] costOptions = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOptions[i] = Integer.toString(j);
        }
        // create checkbox 
        maxSelector = new JComboBox(timeOptions); 
        idealSelector = new JComboBox(timeOptions);
        costSelector = new JComboBox(costOptions);
        
        // add ItemListener 
        maxSelector.addItemListener(this);
        idealSelector.addItemListener(this);
        costSelector.addItemListener(this);
        
        //LABELS:
        
        //maxTime input
        JPanel inputMax = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel max = new JLabel("Max time for activity:"); 
        maxShow = new JLabel("half hour selected");
        inputMax.add(max);
        inputMax.add(maxSelector);
        inputMax.add(maxShow);
        max.setForeground(Color.WHITE);
        max.setFont(new Font("Helvetica", Font.PLAIN, 16));
        maxShow.setForeground(Color.WHITE);
        maxShow.setFont(new Font("Helvetica", Font.PLAIN, 16));
        f.add(inputMax);
        inputMax.setOpaque(false);
        
        //idealTime input
        JPanel inputIdeal = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idealt = new JLabel("Ideal time for activity:"); 
        idealShow = new JLabel("half hour selected");
        inputIdeal.add(idealt);
        inputIdeal.add(idealSelector);
        inputIdeal.add(idealShow);
        idealt.setForeground(Color.WHITE);
        idealt.setFont(new Font("Helvetica", Font.PLAIN, 16));
        idealShow.setForeground(Color.WHITE);
        idealShow.setFont(new Font("Helvetica", Font.PLAIN, 16));
        f.add(inputIdeal);
        inputIdeal.setOpaque(false);

        //maxCost input
        JPanel inputCost = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel maxc = new JLabel("Max cost for activity:"); 
        costShow = new JLabel("0 dollars selected");
        inputCost.add(maxc);
        inputCost.add(costSelector);
        inputCost.add(costShow);
        maxc.setForeground(Color.WHITE);
        maxc.setFont(new Font("Helvetica", Font.PLAIN, 16));
        costShow.setForeground(Color.WHITE);
        costShow.setFont(new Font("Helvetica", Font.PLAIN, 16));
        f.add(inputCost);
        inputCost.setOpaque(false);
       
        f.add(Box.createVerticalGlue());

        //"Save Activity" Button
        JButton saveb = new JButton("Save Current Activity");
        saveb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Activity name entered
                newActName = textField.getText();
                Activity testAct = new Activity();
                testAct.setName(newActName);
                if(PlanitRunner.database.contains(testAct)) {
                    //Ensures activity has not already been saved
                    activityName.setText("Activity Already Saved!");
                } else if(newActMaxTime < newActIdealTime) {
                    //Ensures maxTime is larger than idealTime
                    activityName.setText("Max time must be larger than ideal time");
                } else if(newActName == null || newActName.isEmpty()) {
                    //Checks if user left the activity name field blank
                    activityName.setText("Please Enter a Name For Your Activity");
                } else {
                    //Add Activity to the database!
                    Connection con=null; 
                    try
                    {
                        String url = "jdbc:mysql://206.189.165.197:3306/activity"+
                            "?verifyServerCertificate=false"+
                            "&useSSL=true"+
                            "&requireSSL=true"; 
                        String user = "user"; 
                        String pass = "password";
                        String sql = "INSERT INTO activities (name,maxtime,idealtime,maxcost) VALUES ('"
                        + newActName + "', " + newActMaxTime + ", " + newActIdealTime + ", " + newActMaxCost + ")";

                        Class dbDriver = Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(url, user, pass);
                        
                        Statement st = con.createStatement(); 
                        int m = st.executeUpdate(sql);
                        PlanitRunner.updateActivityList();
                    } 
                    catch(Exception ex) 
                    { 
                        ex.printStackTrace();
                    }
                    activityName.setText("Activity Saved!");
                    maxSelector.setSelectedIndex(0);
                    idealSelector.setSelectedIndex(0);
                    costSelector.setSelectedIndex(0);
                    textField.setText("");
                }
            }
        });
        saveb.setAlignmentY(Component.CENTER_ALIGNMENT);
        f.add(saveb);

        f.setSize(500, 600);
        f.setResizable(true);
        f.setVisible(true);
    }
    
    public void itemStateChanged(ItemEvent e) 
    { 
        // if the state combobox is changed 
        if (e.getSource() == maxSelector) {
            String selection = "" + maxSelector.getSelectedItem();
            double selectionDouble = Double.parseDouble(selection);
            String hours = "";
            if(selectionDouble == 1.0) {
                hours = "hour";
            } else if(selectionDouble == 0.5) {
                selection = "";
                hours = "half hour";
            } else {
                hours = "hours";
            }
            maxShow.setText(selection + " " + hours + " selected");
            
            //Update new activity maxTime variable based on current selection
            newActMaxTime = 60.0 * Double.parseDouble("" + maxSelector.getSelectedItem());
        }
        else if (e.getSource() == idealSelector) {
            String selection = "" + idealSelector.getSelectedItem();
            double selectionDouble = Double.parseDouble(selection);
            String hours = "";
            if(selectionDouble == 1.0) {
                hours = "hour";
            } else if(selectionDouble == 0.5) {
                selection = "";
                hours = "half hour";
            } else {
                hours = "hours";
            }
            idealShow.setText(selection + " " + hours + " selected");
            
            //Update new activity idealTime variable based on current selection
            newActIdealTime = 60.0 * Double.parseDouble("" + idealSelector.getSelectedItem());
        }
        else if (e.getSource() == costSelector) {
            costShow.setText(costSelector.getSelectedItem() + " dollars selected");
            
            //Update new activity maxCost variable based on current selection
            newActMaxCost = Double.parseDouble("" + costSelector.getSelectedItem());
        }
    }

    public void actionPerformed(ActionEvent e){

    }
}
