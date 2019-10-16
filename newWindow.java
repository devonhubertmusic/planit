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

// //going to change the class name asap to activityPlan
public class newWindow extends Frame implements ActionListener {

	private Button b;
	public newWindow(){
        setLayout( new FlowLayout());
        setTitle("Plan Generator");
        setSize(500, 350);
        setVisible(false);
	
      addWindowListener(new WindowAdapter()
      {
      public void windowClosing(WindowEvent e)
      {
         dispose();
     }
 });
     
//not sure what should be the title
      JLabel label3 = new JLabel("Activity Plan");
     add(label3);
      label3.setFont(new Font("Helvetica", Font.PLAIN, 25));
label3.setAlignmentY(Component.CENTER_ALIGNMENT);

 //dummy buttons!
	JButton save = new JButton("Save Plan");
save.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	add(save);
	//save.addActionListener();
	add(Box.createVerticalGlue());
	
	JButton print = new JButton("Print Plan");
	print.setAlignmentX(Component.CENTER_ALIGNMENT);
	add(print);

 }

    @Override
    public void actionPerformed(ActionEvent evt){

    }
}
