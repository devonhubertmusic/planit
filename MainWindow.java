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
    private TextField text = new TextField(25);
    private ArrayList<Activity> databaseCopy;

    public MainWindow(String title) {
        super(title);
        setLayout(new FlowLayout());
        addWindowListener(this);
        add(text);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Helvetica", Font.ITALIC, 30));
        text.setEditable(false);
        text.setText("        Welcome to Plan-it!");

        JLabel picture = new JLabel(new ImageIcon("planit.jpg"));
        add(picture);

        //Must be a better way to do spacing, haha
        JLabel label0 = new JLabel("                                  " +
        "                                                             ");
        add(label0);


        //JFrame timeScroll = new JFrame("Test");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Date dateStart = new Date();
        SpinnerDateModel sdmStart = new SpinnerDateModel(dateStart, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerStart = new JSpinner(sdmStart);
        JSpinner.DateEditor des = new JSpinner.DateEditor(spinnerStart, "HH:mm");
        spinnerStart.setEditor(des);
        JLabel labelStart = new JLabel("Please select start time: ");
        add(labelStart);
        labelStart.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(spinnerStart);
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
        labelFinish.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(spinnerFinish);

        JLabel label1 = new JLabel("                                  " +
        "                                                             ");
        add(label1);


        Button b = new Button("Generate Activity Plan");
        add(b);
        b.addActionListener(this);

        //Must be a better way to do spacing, haha
        JLabel label2 = new JLabel("                                  " +
        "                                                             ");
        add(label2);

        JLabel label3 = new JLabel("Developed by Team Rocket");
        add(label3);
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
