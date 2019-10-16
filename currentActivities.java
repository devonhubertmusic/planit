import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class currentActivities extends Frame implements ActionListener {

    public currentActivities(){
        setLayout( new FlowLayout());

        setTitle("Current Actiivies");
        setSize(500, 350);
        setVisible(false);

        addWindowListener(new WindowAdapter()
      {
      public void windowClosing(WindowEvent e)
      {
         dispose();
      }
      });

      JLabel label3 = new JLabel("All Activities");
      add(label3);
      label3.setFont(new Font("Helvetica", Font.PLAIN, 25));
    }

    @Override
    public void actionPerformed(ActionEvent evt){

    }
}
