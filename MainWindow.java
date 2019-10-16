import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;

public class MainWindow extends Frame implements WindowListener, ActionListener
{
    private Button b;
    private newWindow genWindow = new newWindow();
    private currentActivities curAct = new currentActivities();
    //private TextField text = new TextField(25);
    private ArrayList<Activity> databaseCopy;

    public MainWindow(String title) {
        super(title);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        addWindowListener(this);
        JLabel text = new JLabel("Welcome to Plan-it!");
        add(text);
        text.setOpaque(true);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Helvetica", Font.ITALIC, 30));
        text.setMinimumSize(new Dimension(400, 100));
        text.setPreferredSize(new Dimension(400, 100));
        text.setMaximumSize(new Dimension(400, 100));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        add(Box.createVerticalGlue());

        JLabel picture = new JLabel(new ImageIcon("planit.jpg"));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picture);

        add(Box.createVerticalGlue());

        //JFrame timeScroll = new JFrame("Test");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Date dateStart = new Date();
        SpinnerDateModel sdmStart = new SpinnerDateModel(dateStart, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerStart = new JSpinner(sdmStart);
        JSpinner.DateEditor des = new JSpinner.DateEditor(spinnerStart, "HH:mm");
        spinnerStart.setEditor(des);
        JLabel labelStart = new JLabel("Please select start time: ");
        add(labelStart);
        labelStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelStart.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(spinnerStart);
        spinnerStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerStart.setMinimumSize(new Dimension(100, 30));
        spinnerStart.setPreferredSize(new Dimension(100, 30));
        spinnerStart.setMaximumSize(new Dimension(100, 30));
        //setSize(100,100);
        //setVisible(true);
        // JLabel label3 = new JLabel("                                  "+
        // "                                                             ");
        // add(label3);
        Date dateFinish = new Date();
        SpinnerDateModel sdmFinish = new SpinnerDateModel(dateFinish, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerFinish = new JSpinner(sdmFinish);
        JSpinner.DateEditor def = new JSpinner.DateEditor(spinnerFinish, "HH:mm");
        spinnerFinish.setEditor(def);
        JLabel labelFinish = new JLabel("Please select end time: ");
        add(labelFinish);
        labelFinish.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelFinish.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(spinnerFinish);
        spinnerFinish.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinnerFinish.setMinimumSize(new Dimension(100, 30));
        spinnerFinish.setPreferredSize(new Dimension(100, 30));
        spinnerFinish.setMaximumSize(new Dimension(100, 30));


        add(Box.createVerticalGlue());

        JButton b = new JButton("Generate Activity Plan");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(b);
        b.addActionListener(this);

        add(Box.createVerticalGlue());

        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setFont(new Font("Helvetica", Font.PLAIN, 14));
    }

    public void updateDatabase(ArrayList<Activity> database) {
        this.databaseCopy = database;
    }

    //
    public void actionPerformed(ActionEvent e) {
    	double availableTime = 0.0;
    	double availableMoney = 0.0;

        //prompt user for current time and money available and update
        //then...

        Plan myPlan = new Plan();
        myPlan.generatePlan(databaseCopy, availableTime, availableMoney);

        //then print result in new window
        genWindow.setVisible(true);
    }

    /**
     * Handles the closing of the Main window, re-setting settings to
     * their defaults
     *
     * @param e The WindowEvent triggered by closing the Main window
     */
    public void windowClosing(WindowEvent e)
    {
        try {
            System.out.println("Attempting to save data");
            PlanitRunner.saveData(databaseCopy);
            System.out.println("Data saved!");
            dispose();
            System.exit(0);
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
