import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

public class test {
  private static final String url = "jdbc:mysql://localhost:3306/people";
  private static final String user = "root";
  private static final String password = "password";

  static JTable mysTable;

  private static Connection con;
  private static Statement stmt;
  private static ResultSet rs;


  public static void main(String[] args) {
    String[] columnNames = {"ID", "Name", "Major"};
    mysTable = new JTable(3,3);
    mysTable.setBounds(20,10,300,300);
    JFrame frame = new JFrame("Testing MySql");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(null);
    frame.setSize(500,500);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.add(mysTable);

    try {
      con = DriverManager.getConnection(url, user, password);
      String query = "SELECT * FROM people";
      stmt = con.createStatement();
      rs = stmt.executeQuery(query);
      int li_row = 0;
      while (rs.next()) {
        mysTable.setValueAt(rs.getString("name"),li_row,0);
        mysTable.setValueAt(rs.getInt("major"),li_row,1);
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int major = rs.getInt("major");
        System.out.println(major);
        li_row++;
      }
    } catch (SQLException sqlEx) {
      sqlEx.printStackTrace();
    } finally {
    try { con.close(); } catch(SQLException se) { /*can't do anything */}
    try { stmt.close(); } catch(SQLException se) { /*can't do anything */}
    try { rs.close(); } catch(SQLException se) { /*can't do anything */}

    }
  }
}
