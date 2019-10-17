import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;

public class MainWindow extends JFrame implements WindowListener, ActionListener
{
    private Button b;
    private newWindow genWindow = new newWindow();
    //private TextField text = new TextField(25);
    private ArrayList<Activity> databaseCopy;

    public MainWindow(String title) {
        super(title);
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File("space.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    Dimension d = getSize();
                    g.drawImage(backgroundImage, 0, 0, d.width, d.height, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        JLabel picture = new JLabel(new ImageIcon("Planit.png"));
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picture);

        add(Box.createVerticalGlue());

        //JFrame timeScroll = new JFrame("Test");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Date dateStart = new Date();
        SpinnerDateModel sdmStart = new SpinnerDateModel(dateStart, null, null, Calendar. HOUR_OF_DAY);
        JSpinner spinnerStart = new JSpinner(sdmStart);
        JSpinner.DateEditor des = new JSpinner.DateEditor(spinnerStart, "HH:mm");
        spinnerStart.setEditor(des);
        JLabel labelStart = new JLabel("Please select start time: ");
        inputPanel.add(labelStart);
        labelStart.setForeground(Color.WHITE);
        labelStart.setFont(new Font("Helvetica", Font.PLAIN, 16));
        inputPanel.add(spinnerStart);
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
        inputPanel.add(labelFinish);
        labelFinish.setForeground(Color.WHITE);
        labelFinish.setFont(new Font("Helvetica", Font.PLAIN, 16));
        inputPanel.add(spinnerFinish);
        spinnerFinish.setMinimumSize(new Dimension(100, 30));
        spinnerFinish.setPreferredSize(new Dimension(100, 30));
        spinnerFinish.setMaximumSize(new Dimension(100, 30));

        add(inputPanel);
        inputPanel.setOpaque(false);


        add(Box.createVerticalGlue());

        JButton b = new JButton("Generate Activity Plan");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(b);
        b.addActionListener(this);

        add(Box.createVerticalGlue());

        JButton vb = new JButton("View Current Activities");
        vb.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new currentActivities();
            }
        });
        vb.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(vb);

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
            PlanitRunner.saveData(databaseCopy);
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
