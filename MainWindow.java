import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MainWindow extends Frame implements WindowListener, ActionListener
{
    Button b;
    TextField text = new TextField(25);
    
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
        
        Button b = new Button("Generate Activity Plan");
        add(b);
        b.addActionListener(this);
        
        //JLabel label = new JLabel("\nDeveloped by Team Rocket");
        //add(label);
        //label.setFont(new Font("Helvetica", Font.PLAIN, 18));
    }
    
    //
    public void actionPerformed(ActionEvent e) {
        
    }
    
    /**
     * Handles the closing of the Main window, re-setting settings to
     * their defaults
     * 
     * @param e The WindowEvent triggered by closing the Main window
     */
    public void windowClosing(WindowEvent e) {
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