import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MainWindow extends Frame implements WindowListener, ActionListener
{
    private Button b;
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
        
        Button b = new Button("Generate Activity Plan");
        add(b);
        b.addActionListener(this);
        
        //Must be a better way to do spacing, haha
        JLabel label1 = new JLabel("                                  " +
        "                                                             ");
        add(label1);
        
        JLabel label2 = new JLabel("Developed by Team Rocket");
        add(label2);
        label2.setFont(new Font("Helvetica", Font.PLAIN, 14));
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
    }
    
    /**
     * Handles the closing of the Main window, re-setting settings to
     * their defaults
     * 
     * @param e The WindowEvent triggered by closing the Main window
     */
    public void windowClosing(WindowEvent e) {
        System.out.println("Saving data 1");
        PlanitRunner.saveData();
        System.out.println("Saving to file 5");
        dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
