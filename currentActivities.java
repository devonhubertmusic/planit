import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class currentActivities {
    JFrame f;
    JTable j;

    currentActivities()
    {
        f = new JFrame();
        f.setTitle("Current Activities");

        // Test Data to be displayed in the JTable
        String[][] data = {
            { "Picnic", "1:30", "25" },
        };
        String[] columnNames = { "Activity", "Ideal Time", "Max Cost" };

        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        f.setSize(500, 200);
        f.setVisible(true);
    }
}
