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
    
    private String url;
    private String user;
    private String password;
    
    JTable mysTable;
    
    private Connection con;
    private Statement stmt1;
    private Statement stmt2;
    private ResultSet rs;
    
    public JavaToMySQL() {
        this.url = "jdbc:mysql://localhost:3306/test"; //replace with server url
        this.user = "root";
        this.password = "planit";
    }
    
    public JavaToMySQL(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        
    /*
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
            try { con.close(); } catch(SQLException se) {}
            try { stmt1.close(); } catch(SQLException se) {}
            try { stmt2.close(); } catch(SQLException se) {}
            try { rs.close(); } catch(SQLException se) {}
            
        }
    
    */
    }
}
