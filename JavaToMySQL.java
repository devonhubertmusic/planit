import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

public class JavaToMySQL {
    // java -cp "/Users/devonhubert/Documents/mysql-connector-java-8.0.17.jar:." test
    
    private static final String url = "jdbc:mysql://localhost:3306/test"; //replace with server url
    private static final String user = "root";
    private static final String password = "planit";
    
    static JTable mysTable;
    
    private static Connection con;
    private static Statement stmt1;
    private static Statement stmt2;
    private static ResultSet rs;
    
    
    public static void main(String[] args) {
        String[] columnNames = {"ID", "Name", "Major"};
        
        mysTable = new JTable(21,4);
        mysTable.setBounds(0,0,500,500);
        JScrollPane scrollpane = new JScrollPane(mysTable);
        
        
        JFrame frame = new JFrame("Testing MySql");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(scrollpane);
        
        try {
            con = DriverManager.getConnection(url, user, password);
            String query1 = "USE test";
            stmt1 = con.createStatement();
            stmt1.executeQuery(query1);
            String query2 = "SELECT * FROM activities";
            stmt2 = con.createStatement();
            rs = stmt2.executeQuery(query2);
            
            int li_row = 0;
            while (rs.next()) {
                mysTable.setValueAt(rs.getString("name"),li_row,0);
                mysTable.setValueAt(rs.getString("maxtime"),li_row,1);
                mysTable.setValueAt(rs.getString("idealtime"),li_row,2);
                mysTable.setValueAt(rs.getString("maxcost"),li_row,3);
                li_row++;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */}
            try { stmt1.close(); } catch(SQLException se) { /*can't do anything */}
            try { stmt2.close(); } catch(SQLException se) { /*can't do anything */}
            try { rs.close(); } catch(SQLException se) { /*can't do anything */}
            
        }
    }
}
