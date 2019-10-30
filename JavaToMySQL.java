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
        this.url = "jdbc:mysql://206.189.165.197:3306/activity"+
        "?verifyServerCertificate=false&useSSL=true"+
        "&requireSSL=true"; 
        this.user = "root";
        this.password = "password";
    }
    
    public JavaToMySQL(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        
        
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) {}
            try { stmt1.close(); } catch(SQLException se) {}
            try { stmt2.close(); } catch(SQLException se) {}
            try { rs.close(); } catch(SQLException se) {}
            
        }
    
    
    }
}
