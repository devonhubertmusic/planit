import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;


public class currentActivities extends javax.swing.JFrame {

  /**
   * @param args the command line arguments
   */
    public currentActivities() {
        initComponents();
        Show_Activities_In_JTable();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(currentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(currentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(currentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(currentActivities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        setVisible(true);

    }

     // get the connection
   public Connection getConnection()
   {
       Connection con;
       try {
           con = DriverManager.getConnection("jdbc:mysql://206.189.165.197:3306/activity"+
            "?verifyServerCertificate=false&useSSL=true&requireSSL=true","user","password");
           return con;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }


 // get a list of activities from mysql database
   public ArrayList<Activity> getactivityList()
   {
       ArrayList<Activity> activityList = new ArrayList<Activity>();
       Connection connection = getConnection();
       
       String query = "SELECT * FROM  activities";
       Statement st;
       ResultSet rs;

       try {
           st = connection.createStatement();
           rs = st.executeQuery(query);
           Activity activity;
           while(rs.next())
           {
               activity = new Activity(rs.getString("name"),rs.getInt("maxTime"),rs.getInt("idealTime"),rs.getInt("maxCost"));
               activityList.add(activity);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return activityList;
   }

   // Display Data In JTable
   public void Show_Activities_In_JTable()
   {
       ArrayList<Activity> list = getactivityList();
       DefaultTableModel model = (DefaultTableModel)jTable_Display_Activities.getModel();
       //Add JScrollPane?
       Object[] row = new Object[4];
       for(int i = 0; i < list.size(); i++)
       {
           row[0] = list.get(i).getName();
           row[1] = list.get(i).getMaxTime();
           row[2] = list.get(i).getIdealTime();
           row[3] = list.get(i).getMaxCost();

           model.addRow(row);
       }
    }

   // Execute The Insert Update And Delete Querys
   public void executeSQlQuery(String query, String message)
   {
       Connection con = getConnection();
       Statement st;
       try{
           st = con.createStatement();
           if((st.executeUpdate(query)) == 1)
           {
               // refresh jtable data
               DefaultTableModel model = (DefaultTableModel)jTable_Display_Activities.getModel();
               model.setRowCount(0);
               Show_Activities_In_JTable();

               JOptionPane.showMessageDialog(null, "Data "+message+" Succefully");
           }else{
               JOptionPane.showMessageDialog(null, "Data Not "+message);
           }
       }catch(Exception ex){
           ex.printStackTrace();
       }
   }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JTextField();
        jTextField_MaxTime = new javax.swing.JTextField();
        jTextField_IdealTime = new javax.swing.JTextField();
        jTextField_MaxCost = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Display_Activities = new javax.swing.JTable();
        jButton_Update = new javax.swing.JButton();
        jButton_Delete = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel2.setText("Max Time:");

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel3.setText("Ideal Time:");

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel4.setText("Max Cost:");

        jTextField_Name.setFont(new java.awt.Font("Helvetica", 0, 14));

        jTextField_MaxTime.setFont(new java.awt.Font("Helvetica", 0, 14));
        jTextField_MaxTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_MaxTimeActionPerformed(evt);
            }
        });

        jTextField_IdealTime.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        jTextField_IdealTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IdealTimeActionPerformed(evt);
            }
        });

        jTextField_MaxCost.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        jTextField_MaxCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_MaxCostActionPerformed(evt);
            }
        });

        jTable_Display_Activities.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Max Time", "Ideal Time", "Max Cost"
            }
        ));
        jTable_Display_Activities.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_Display_ActivitiesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Display_Activities);

        jButton_Update.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jButton_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("icons/edit.png"))); // NOI18N
        jButton_Update.setText("Edit");
        jButton_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UpdateActionPerformed(evt);
            }
        });

        jButton_Delete.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jButton_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("icons/trash.png"))); // NOI18N
        jButton_Delete.setText("Delete");
        jButton_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_MaxTime, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_IdealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_MaxCost, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Update)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jButton_Delete)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_MaxTime, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_IdealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_MaxCost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(192, 192, 192))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void jTextField_MaxTimeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField_IdealTimeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField_MaxCostActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

 // show jtable row data in jtextfields in the mouse clicked event
    private void jTable_Display_ActivitiesMouseClicked(java.awt.event.MouseEvent evt) {
       // Get The Index Of The Slected Row
        int i = jTable_Display_Activities.getSelectedRow();

        TableModel model = jTable_Display_Activities.getModel();

         // Display Slected Row In JTextFields
        jTextField_Name.setText(model.getValueAt(i,0).toString());

        jTextField_MaxTime.setText(model.getValueAt(i,1).toString());

        jTextField_IdealTime.setText(model.getValueAt(i,2).toString());

        jTextField_MaxCost.setText(model.getValueAt(i,3).toString());
    }


 // Button Update
    private void jButton_UpdateActionPerformed(java.awt.event.ActionEvent evt) {
        String query = "UPDATE `activities` SET `name`='"+jTextField_Name.getText()+"',`maxtime`='"
        +jTextField_MaxTime.getText()+"',`idealtime`='"+jTextField_IdealTime.getText()+"',`maxcost`='"
        +jTextField_MaxCost.getText()+"' WHERE `name` = '"+jTextField_Name.getText() +"'";
        System.out.println(query);
       executeSQlQuery(query, "Updated");
    }


 // Button Delete
    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String query = "DELETE FROM `activities` WHERE name = '"+jTextField_Name.getText() + "'";
         executeSQlQuery(query, "Deleted");
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton_Delete;
    private javax.swing.JButton jButton_Update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Display_Activities;
    private javax.swing.JTextField jTextField_MaxCost;
    private javax.swing.JTextField jTextField_MaxTime;
    private javax.swing.JTextField jTextField_Name;
    private javax.swing.JTextField jTextField_IdealTime;
    // End of variables declaration

    public void actionPerformed(ActionEvent e){

    }
}
