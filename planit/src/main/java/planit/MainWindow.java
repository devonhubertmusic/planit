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

public class MainWindow extends JFrame implements WindowListener, ItemListener
{   
    //User availablity information
    public double availableTime;
    public double availableMoney;

    //Dropdown boxes for user input
    private JComboBox timeSelector, moneySelector;
    
    //Labels to display maxTime, idealTime, maxCost, and availableMoney user input
    private JLabel time, money;

    public MainWindow(String title) {
        super(title); //Add title to window
        
        availableTime = 30.0;
        availableMoney = 0.0;

        //Set Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurrentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
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
        
        //Create and format header
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        addWindowListener(this);
        JLabel text = new JLabel("Welcome to Plan-it!");
        add(text);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Rockwell", Font.ITALIC + Font.BOLD, 35));
        text.setMinimumSize(new Dimension(400, 150));
        text.setPreferredSize(new Dimension(400, 150));
        text.setMaximumSize(new Dimension(400, 150));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        //Add logo
        try {
        JLabel picture = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Planit.png"))));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        add(Box.createVerticalGlue());

        //time options (in hours)
        String timeOptions[] = { "0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6" }; 
        String[] costOptions = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOptions[i] = Integer.toString(j);
        }

        //Get user information to generate new activity plan
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JLabel spacer = new JLabel("                                      ");
        spacer.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(spacer);
        spacer.setForeground(Color.WHITE);
        spacer.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        JLabel newActivityPlan = new JLabel("Generate a New Activity Plan:");
        newActivityPlan.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newActivityPlan);
        newActivityPlan.setForeground(Color.WHITE);
        newActivityPlan.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        timeSelector = new JComboBox(timeOptions);
        timeSelector.addItemListener(this);
        JLabel userTime = new JLabel("Select amount of free time:");
        time = new JLabel("half hour selected");
        inputPanel.add(userTime);
        inputPanel.add(timeSelector);
        inputPanel.add(time);
        userTime.setForeground(Color.WHITE);
        userTime.setFont(new Font("Helvetica", Font.PLAIN, 16));
        time.setForeground(Color.WHITE);
        time.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        moneySelector = new JComboBox(costOptions);
        moneySelector.addItemListener(this);
        JLabel userMoney = new JLabel("     Select current budget:");
        money = new JLabel("0 dollars selected"); 
        inputPanel.add(userMoney);
        inputPanel.add(moneySelector);
        inputPanel.add(money);
        userMoney.setForeground(Color.WHITE);
        userMoney.setFont(new Font("Helvetica", Font.PLAIN, 16));
        money.setForeground(Color.WHITE);
        money.setFont(new Font("Helvetica", Font.PLAIN, 16));
 
        add(inputPanel);
        inputPanel.setOpaque(false);
        add(Box.createVerticalGlue());

        JPanel viewButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //"Generate Activity Plan" button
        JButton b = new JButton("Generate Activity Plan");
        b.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ActivityPlan(availableTime, availableMoney);
            }
        });
        viewButtons.add(b);

        //"View Current Activities" button
        JButton vb = new JButton("View Current Activities");
        vb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CurrentActivities();
            }
        });
        viewButtons.add(vb);
    
        add(viewButtons);
    
        viewButtons.setOpaque(false);
            add(Box.createVerticalGlue());

        //Footer label
        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Helvetica", Font.PLAIN, 14));
        add(Box.createVerticalGlue());
    }

    public void itemStateChanged(ItemEvent e) 
    { 
        // if the state combobox is changed 
        if (e.getSource() == timeSelector) {
            String selection = "" + timeSelector.getSelectedItem();
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
            time.setText(selection + " " + hours + " selected");
            
            //Update user's availableTime variable based on current selection
            availableTime = 60.0 * Double.parseDouble("" + timeSelector.getSelectedItem());
        }
        else if (e.getSource() == moneySelector) {
            money.setText(moneySelector.getSelectedItem() + " dollars selected");
            
            //Update user's availableMoney variable based on current selection
            availableMoney = Double.parseDouble("" + moneySelector.getSelectedItem());
        }
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

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
