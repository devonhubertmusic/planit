import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class newWindow extends Frame implements ActionListener {

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

      JLabel label3 = new JLabel("Testing Window");
      add(label3);
      label3.setFont(new Font("Helvetica", Font.PLAIN, 25));
    }

    @Override
    public void actionPerformed(ActionEvent evt){

    }
}
