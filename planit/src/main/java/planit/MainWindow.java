package planit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
import java.sql.*;
/**
* MainWindow is the main screen of the Plan-it
* application.
*/
public class MainWindow extends JFrame implements WindowListener, ItemListener
{   
    //User availablity information
    public double availableTime;
    public double availableMoney;
    public JButton windowLinker;

    //Dropdown boxes for user input
    private JComboBox timeSelector, moneySelector;
    
    //Labels to display maxTime, idealTime, maxCost, and availableMoney user input
    private JLabel time, money;

    public MainWindow(String title, final int screenHeight) {
        super(title); //Add title to window
        
        //initialize available time to half hour
        availableTime = 30.0;
        
        //initialize available money to 0.0
        availableMoney = 0.0;

        updateLookAndFeel();
        
        setBackgroundImage();
        
        createHeader(screenHeight);

        addLogo();
        
        addButtonsAndComponents(screenHeight);
        
        createFooter();
    }

     public void enableMyButton(){
        windowLinker.setEnabled(true);
    }

    public void disableMyButton(){
        windowLinker.setEnabled(false);
    }

    public void itemStateChanged(ItemEvent e) 
    { 
        // if the state combobox is changed 
        if (e.getSource() == timeSelector) {
            //Update user's availableTime variable based on current selection
            availableTime = 60.0 * Double.parseDouble("" + timeSelector.getSelectedItem());
        }
        else if (e.getSource() == moneySelector) {            
            //Update user's availableMoney variable based on current selection
            availableMoney = Double.parseDouble("" + moneySelector.getSelectedItem());
        }
    }

    public void showCurrentActivities(){
        CurrentActivities a = new CurrentActivities(this);
    }
    
    public void createHeader(final int screenHeight) {
        //Create and format header
        setMinimumSize(new Dimension(screenHeight, screenHeight));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        addWindowListener(this);
        JLabel text = new JLabel("Welcome to Plan-it!");
        add(text);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Rockwell", Font.ITALIC + Font.BOLD, 38));
        text.setMinimumSize(new Dimension(400, 150));
        text.setPreferredSize(new Dimension(400, 150));
        text.setMaximumSize(new Dimension(400, 150));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
    }
    
    public void createFooter() {
        //Footer label
        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Helvetica", Font.PLAIN, 14));
        add(Box.createVerticalGlue());
    }
    
    public void setBackgroundImage() {
        //Set background image of window
        try {
            final Image backgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/space.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 0, 0, d.width, d.height, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void addLogo() {
        //Add logo
        try {
            JLabel picture = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Planit.png"))));
            picture.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add(Box.createVerticalGlue());
    }
    
    public void updateLookAndFeel(){
        //Set Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            //Exception handling
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void addButtonsAndComponents(final int screenHeight) {
        //Initialize and format all remaining components/buttons
        
        //time options (in hours)
        String timeOptions[] = { "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6" };
        String[] costOptions = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOptions[i] = Integer.toString(j);
        }
        
        //Get user information to generate new activity plan
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Create and format all window components
        JLabel spacer = new JLabel("                                      ");
        spacer.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(spacer);
        spacer.setForeground(Color.WHITE);
        spacer.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        JLabel newActivityPlan = new JLabel("Generate a New Activity Plan:");
        newActivityPlan.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newActivityPlan);
        newActivityPlan.setForeground(Color.WHITE);
        newActivityPlan.setFont(new Font("Helvetica", Font.BOLD, 24));
        
        timeSelector = new JComboBox(timeOptions);
        timeSelector.addItemListener(this);
        JLabel userTime = new JLabel("Select number of free hours:");
        inputPanel.add(userTime);
        inputPanel.add(timeSelector);
        userTime.setForeground(Color.WHITE);
        userTime.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeSelector.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        moneySelector = new JComboBox(costOptions);
        moneySelector.addItemListener(this);
        JLabel userMoney = new JLabel("    Select current budget: $");
        inputPanel.add(userMoney);
        inputPanel.add(moneySelector);
        userMoney.setForeground(Color.WHITE);
        userMoney.setFont(new Font("Helvetica", Font.PLAIN, 20));
        moneySelector.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        add(inputPanel);
        inputPanel.setOpaque(false);
        add(Box.createVerticalGlue());
        
        JPanel viewButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        //"Generate Activity Plan" button
        JButton generatePlan = new JButton("Generate Plan");
        generatePlan.setFont(new Font("Helvetica", Font.PLAIN, 18));
        generatePlan.setPreferredSize(new Dimension(150, 50));
        generatePlan.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ActivityPlan(availableTime, availableMoney, screenHeight);
            }
        });
        viewButtons.add(generatePlan);
        
        //"View Current Activities" button
        final JButton editActivities = new JButton("Edit Activities");
        editActivities.setFont(new Font("Helvetica", Font.PLAIN, 18));
        editActivities.setPreferredSize(new Dimension(150, 50));
        editActivities.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCurrentActivities();
                windowLinker = editActivities;
            }
        });
        viewButtons.add(editActivities);
        
        add(viewButtons);
        
        viewButtons.setOpaque(false);
        add(Box.createVerticalGlue());
    }

    //Handles the closing of the Main window, re-setting settings to their defaults
    public void windowClosing(WindowEvent e)
    {
        try {
            dispose(); //Close window
            System.exit(0); //Exit program
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    //Overridden methods
    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
