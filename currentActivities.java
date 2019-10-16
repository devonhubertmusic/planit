import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.*;

public class currentActivities {
    JFrame f;
    JTable j;
    ArrayList<Activity> database;

    currentActivities()
    {
        f = new JFrame();
        f.setTitle("Current Activities");
	this.database = PlanitRunner.database;

        // Test Data to be displayed in the JTable
        int rowSize = database.size();
        int columnSize = 3;
        String[][] data = new String[rowSize][columnSize];
        for(int row = 0; row < rowSize; row++) {
	    for(int column = 1; column <= columnSize; column++) {
                Activity temp = database.get(row);
                if(column == 1) {
		    data[row][column - 1] = "" + temp.getName();
		} else if(column == 2) {
            data[row][column - 1] = "" +  temp.getIdealTime() + " minutes";
		} else if(column == 3) {
                    data[row][column - 1] = "$" + temp.getMaxCost();
		} else {

		}
	    }
	}


        String[] columnNames = { "Activity", "Ideal Time", "Max Cost" };

        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        f.setSize(500, 200);
        f.setVisible(true);
    }
}
