package planit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;


public class CurrentActivities extends javax.swing.JFrame {

    public boolean toggleTrue;

  /**
   * @param args the command line arguments
   */
    public CurrentActivities() {
        toggleTrue = true;

        initComponents();
        Show_Activities_In_JTable();

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
       idList = new ArrayList<Integer>();
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
               idList.add(rs.getInt("id"));
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
       Object[] row = new Object[6];
       for(int i = 0; i < list.size(); i++)
       {
           row[0] = list.get(i).getName();
           row[1] = list.get(i).getMaxTime();
           row[2] = list.get(i).getIdealTime();
           row[3] = list.get(i).getMaxCost();
           row[4] = "Edit";
           row[5] = "Delete";

           model.addRow(row);
       }
       jTable_Display_Activities.getColumnModel().getColumn(0).setPreferredWidth(200);

       editButtonColumn = new ButtonColumn(jTable_Display_Activities, edit_action, 4);
       deleteButtonColumn = new ButtonColumn(jTable_Display_Activities, delete_action, 5);
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

               JOptionPane.showMessageDialog(null, "Activity "+message+"!");
           } else {
               JOptionPane.showMessageDialog(null, "Activity Not "+message);
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
        timeOption = new String[96];
        for (int i = 0, j = 5; i < 96; ++i, j+=5) {
            timeOption[i] = Double.toString((double)j);
        }
        costOption = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOption[i] = Double.toString((double)j);
        }

        String items[] = {"Misc", "Food", "Outdoor Adventures", "Fitness", "Music", "Games"};

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JTextField();
        jComboBox_MaxTime = new javax.swing.JComboBox(timeOption);
        jComboBox_IdealTime = new javax.swing.JComboBox(timeOption);
        jComboBox_MaxCost = new javax.swing.JComboBox(costOption);
        jComboBox_ActivityType = new javax.swing.JComboBox(items);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Display_Activities = new javax.swing.JTable();
        jButton_Update = new javax.swing.JButton();
        jButton_Create = new javax.swing.JButton();

        //EDIT ACTION
        edit_action = new javax.swing.AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int prevCurrentID = currentID;
                int actionIndex = Integer.valueOf(e.getActionCommand());
                currentID = idList.get(actionIndex);
                if(prevCurrentID != currentID) {
                    jTable_Display_ActivitiesButtonClicked(actionIndex, true);
                } else {
                    jTable_Display_ActivitiesButtonClicked(actionIndex, false);
                }
            }
        };

        //DELETE ACTION
        delete_action = new javax.swing.AbstractAction() {
            public void actionPerformed(ActionEvent e) {
              resetTextFields();
              jButton_DeleteActionPerformed(e);
            }
        };


        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel2.setText("Max Time (minutes):");

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel3.setText("Ideal Time (minutes):");

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel4.setText("Max Cost (dollars):");

        jLabel5.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel5.setText("Activity Type:");

        jLabel6.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel6.setText("Test:");

        jTextField_Name.setFont(new java.awt.Font("Helvetica", 0, 14));

        jComboBox_MaxTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_MaxTimeActionPerformed(evt);
            }
        });

        jComboBox_IdealTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_IdealTimeActionPerformed(evt);
            }
        });

        jComboBox_MaxCost.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_MaxCostActionPerformed(evt);
            }
        });

        jComboBox_ActivityType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_ActivityTypeActionPerformed(evt);
            }
        });

        jTable_Display_Activities.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Max Time", "Ideal Time", "Max Cost", "", ""
            })
            {
               @Override
               public boolean isCellEditable(int row, int column) {
                   //Only the fourth and fifth column
                   return column == 4 || column == 5;
               }
            }
        );

        jScrollPane1.setViewportView(jTable_Display_Activities);

        jButton_Create.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jButton_Create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png")));
        jButton_Create.setText("Create Activity");
        jButton_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CreateActionPerformed(evt);
            }
        });

        jButton_Update.setFont(new java.awt.Font("Helvetica", 1, 14));
        jButton_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png")));
        jButton_Update.setText("Update Activity");
        jButton_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UpdateActionPerformed(evt);
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
                                    .addComponent(jLabel5)
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
                            .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_MaxTime, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_IdealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_MaxCost, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_ActivityType, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            ))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Create)
                        .addComponent(jButton_Update)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)))
                .addGap(5, 5 , 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox_MaxTime, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox_IdealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox_MaxCost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox_ActivityType, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Create, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(192, 192, 192))
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jComboBox_MaxTimeActionPerformed(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox_IdealTimeActionPerformed(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox_MaxCostActionPerformed(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox_ActivityTypeActionPerformed(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

// show jtable row data in jtextfields in the button clicked event
    private void jTable_Display_ActivitiesButtonClicked(int actionIndex, boolean differentSelection) {

        if(toggleTrue || differentSelection) {
            // Get The Index Of The Slected Row

            TableModel model = jTable_Display_Activities.getModel();

             // Display Slected Row In JTextFields
            jTextField_Name.setText(model.getValueAt(actionIndex,0).toString());

            jComboBox_MaxTime.setSelectedItem(model.getValueAt(actionIndex,1).toString());

            jComboBox_IdealTime.setSelectedItem(model.getValueAt(actionIndex,2).toString());

            jComboBox_MaxCost.setSelectedItem(model.getValueAt(actionIndex,3).toString());

            toggleTrue = false;
        } else {
            resetTextFields();
            toggleTrue = true;
        }
    }

    public void resetTextFields() {
        jComboBox_MaxTime.setSelectedIndex(0);

        jComboBox_IdealTime.setSelectedIndex(0);

        jComboBox_MaxCost.setSelectedIndex(0);

        jTextField_Name.setText("");
    }


 // Button Create
    private void jButton_CreateActionPerformed(java.awt.event.ActionEvent evt) {
        String query = "INSERT INTO `activities` (name,maxtime,idealtime,maxcost) VALUES ('"
                        + jTextField_Name.getText() + "', " + jComboBox_MaxTime.getSelectedItem()
                        + ", " + jComboBox_IdealTime.getSelectedItem() + ", " + jComboBox_MaxCost.getSelectedItem() + ")";
        String newActName = jTextField_Name.getText();
        double newActMaxTime = Double.parseDouble("" + jComboBox_MaxTime.getSelectedItem());
        double newActIdealTime = Double.parseDouble("" + jComboBox_IdealTime.getSelectedItem());
        Activity testAct = new Activity();
        testAct.setName(newActName);
        ArrayList<Activity> list = getactivityList();
        if(list.contains(testAct)) {
            //Ensures activity has not already been saved
            resetTextFields();
            JOptionPane.showMessageDialog(null, "Activity Already Saved!");
        } else if(newActMaxTime < newActIdealTime) {
            //Ensures maxTime is larger than idealTime
            jComboBox_MaxTime.setSelectedIndex(0);
            jComboBox_IdealTime.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Max time must be larger than ideal time");
        } else if(newActName == null || newActName.isEmpty()) {
            //Checks if user left the activity name field blank
            JOptionPane.showMessageDialog(null, "Please Enter a Name For Your Activity");
        } else {
            //Add Activity to the database!
            resetTextFields();
            executeSQlQuery(query, "Inserted");
        }
    }

 // Button Update
    private void jButton_UpdateActionPerformed(java.awt.event.ActionEvent evt) {
        String query = "UPDATE `activities` SET `name`='"+jTextField_Name.getText()+"',`maxtime`='"
        +jComboBox_MaxTime.getSelectedItem()+"',`idealtime`='"+jComboBox_IdealTime.getSelectedItem()+"',`maxcost`='"
        +jComboBox_MaxCost.getSelectedItem()+"' WHERE `id` = "+currentID;
        jComboBox_MaxTime.setSelectedIndex(0);
        jComboBox_IdealTime.setSelectedIndex(0);
        jComboBox_MaxCost.setSelectedIndex(0);
        jTextField_Name.setText("");
        executeSQlQuery(query, "Updated");
    }


 // Button Delete
    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String query = "DELETE FROM `activities` WHERE id = "+currentID;
         executeSQlQuery(query, "Deleted");
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton_Update;
    private javax.swing.JButton jButton_Create;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Display_Activities;
    private javax.swing.JComboBox jComboBox_MaxCost;
    private javax.swing.JComboBox jComboBox_ActivityType;
    private javax.swing.JComboBox jComboBox_MaxTime;
    private javax.swing.JTextField jTextField_Name;
    private javax.swing.JComboBox jComboBox_IdealTime;
    private String[] timeOption;
    private String[] costOption;
    private String[] typeOption;
    private String[] items;
    private ButtonColumn editButtonColumn;
    private ButtonColumn deleteButtonColumn;
    private javax.swing.Action edit_action;
    private javax.swing.Action delete_action;
    private ArrayList<Integer> idList;
    private int currentID;
    // End of variables declaration

    public void actionPerformed(ActionEvent e){

    }
}
