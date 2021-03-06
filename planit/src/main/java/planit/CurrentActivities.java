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

/**
* CurrentActivities handles the creation and editing
* of the activities in the database.
*/
public class CurrentActivities extends javax.swing.JFrame implements WindowListener{

    public boolean toggleTrue; //Global boolean for toggle event
    private MainWindow mWindow; //Main activity editing window

    //Constructor
    public CurrentActivities(MainWindow mWindow) {
        //initialize fields
        this.mWindow = mWindow;
        toggleTrue = true;
        addWindowListener(this);
        initComponents(); //initialize components
        
        //checks that table is loaded correctly
        boolean tableLoaded = Show_Activities_In_JTable();

        if(tableLoaded) {
            setMinimumSize(getSize());
            setTitle("Edit Acitivities");
            setVisible(true);
        }

    }

   // get the connection to server
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
       if(connection != null) {
       //connection worked!
           
           //Get data from database
           String query = "SELECT * FROM  activities";
           Statement st;
           ResultSet rs;

           try {
               //create and execute a series of MySQL queries
               st = connection.createStatement();
               rs = st.executeQuery(query);
               Activity activity;
               
               //Loop through all activities in the database, and convert to java Activity(fields)
               while(rs.next())
               {
                   activity = new Activity(rs.getString("name"),rs.getString("activityType"),rs.getInt("maxTime"),rs.getInt("idealTime"),rs.getInt("maxCost"));
                   activityList.add(activity);
                   idList.add(rs.getInt("id"));
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           
           return activityList;
       } else {
           //connection didn't work :(
           return null;
       }
   }

   // Display Data In JTable
   public boolean Show_Activities_In_JTable()
   {
       ArrayList<Activity> list = getactivityList();
       if(list != null) {
           //new table to fill
           DefaultTableModel model = (DefaultTableModel)jTable_Display_Activities.getModel();
           Object[] row = new Object[6];
           for(int i = 0; i < list.size(); i++)
           {
               //get table information from list
               row[0] = list.get(i).getName();
               row[1] = (int)list.get(i).getMaxTime();
               row[2] = (int)list.get(i).getIdealTime();
               row[3] = (int)list.get(i).getMaxCost();
               row[4] = "Edit";
               row[5] = "Delete";

               //add row to the table
               model.addRow(row);
           }
           
           //set width of cells
           jTable_Display_Activities.getColumnModel().getColumn(0).setPreferredWidth(200);

           //Add edit and delete buttons to the end of the table
           editButtonColumn = new ButtonColumn(jTable_Display_Activities, edit_action, 4);
           deleteButtonColumn = new ButtonColumn(jTable_Display_Activities, delete_action, 5);
           return true;
       } else {
           //No internet case
           JOptionPane.showMessageDialog(null, "Could not connect to Database. Please check internet connection.");
           return false;
       }
    }

   // Execute The Insert Update And Delete Queries
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
            timeOption[i] = Integer.toString(j);
        }
        costOption = new String[100];
        for (int i = 0, j = 0; i < 100; ++i, j+=5) {
            costOption[i] = Integer.toString(j);
        }

        String items[] = {"Misc", "Food", "Outdoor Adventures", "Fitness", "Music", "Games"};

        final java.awt.Image backgroundImage;
        try {
            backgroundImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/images/space.jpg"));
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        jPanel1 = new javax.swing.JPanel() {
          @Override public void paintComponent(java.awt.Graphics g) {
            java.awt.Dimension d = getSize();
            g.drawImage(backgroundImage, 2, 2, d.width-4, d.height-4, null);
          }
        };
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
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
              int actionIndex = Integer.valueOf(e.getActionCommand());
              currentID = idList.get(actionIndex);
              resetTextFields();
              jButton_DeleteActionPerformed(e);
            }
        };

        //Design of labels, buttons, input fields, and table

        jTable_Display_Activities.setFont(new java.awt.Font("Helvetica", 0, 15));
        jTable_Display_Activities.setRowHeight(30);

        jLabel1.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel1.setText("Name:");
        jLabel1.setForeground(java.awt.Color.WHITE);

        jLabel2.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel2.setText("Max Time (minutes):");
        jLabel2.setForeground(java.awt.Color.WHITE);

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel3.setText("Ideal Time (minutes):");
        jLabel3.setForeground(java.awt.Color.WHITE);

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel4.setText("Max Cost (dollars):");
        jLabel4.setForeground(java.awt.Color.WHITE);

        jLabel5.setFont(new java.awt.Font("Helvetica", 0, 18));
        jLabel5.setText("Activity Type:");
        jLabel5.setForeground(java.awt.Color.WHITE);

        jTextField_Name.setFont(new java.awt.Font("Helvetica", 0, 14));
        
        //Limits text field input to 25 characters
        jTextField_Name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (jTextField_Name.getText().length() == 25) {
                    e.consume();
                }
            }
        });
        
        //Add item listener to each combo box for action event handling:
        
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

        //Set default model of activities display on jTable
        jTable_Display_Activities.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Max Time", "Ideal Time", "Max Cost", "", ""
            })
            {
               @Override
               public boolean isCellEditable(int row, int column) {
                   //Only the fourth or fifth column are editable
                   return column == 4 || column == 5;
               }
            }
        );

        // Allow table to scroll
        jScrollPane1.setViewportView(jTable_Display_Activities);

        //Set the design of the create button
        jButton_Create.setFont(new java.awt.Font("Helvetica", 1, 14));
        jButton_Create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png")));
        jButton_Create.setText("Create Activity");
        jButton_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CreateActionPerformed(evt);
            }
        });

        //Set the design of the update button
        jButton_Update.setFont(new java.awt.Font("Helvetica", 1, 14));
        jButton_Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png")));
        jButton_Update.setText("Update Activity");
        jButton_Update.setEnabled(false);
        jButton_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UpdateActionPerformed(evt);
            }
        });

        //Java swing to create group layout for the horizantal and vertical components
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        //Horizantal group layout. Determines the horizontal placement of different components
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33) //Gap between each component
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, 20)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        
        //Vertical group layout. Determines the vertical placement for each component
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
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //Sets the horizantal and vertical group layouts for display
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
        // Not used, simply overriding
    }

    private void jComboBox_IdealTimeActionPerformed(java.awt.event.ItemEvent evt) {
        // Not used, simply overriding
    }

    private void jComboBox_MaxCostActionPerformed(java.awt.event.ItemEvent evt) {
        // Not used, simply overriding
    }

    private void jComboBox_ActivityTypeActionPerformed(java.awt.event.ItemEvent evt) {
        // Not used, simply overriding
    }

// show jtable row data in jtextfields in the button clicked event
    private void jTable_Display_ActivitiesButtonClicked(int actionIndex, boolean differentSelection) {

        // if "edit mode" is true, or selecting different activity from table,
        // Update displayed data in left fields of edit window
        if(toggleTrue || differentSelection) {
            // Get The Index Of The Slected Row

            TableModel model = jTable_Display_Activities.getModel();
            ArrayList<Activity> list = getactivityList();

            // Display Selected Row In JTextFields
            
            // set name field
            jTextField_Name.setText(model.getValueAt(actionIndex,0).toString());

            // set max time field
            jComboBox_MaxTime.setSelectedItem(model.getValueAt(actionIndex,1).toString());

            // set ideal time field
            jComboBox_IdealTime.setSelectedItem(model.getValueAt(actionIndex,2).toString());

            // set max cost field
            jComboBox_MaxCost.setSelectedItem(model.getValueAt(actionIndex,3).toString());

            // set activity type field
            jComboBox_ActivityType.setSelectedItem(list.get(actionIndex).getActivityType());

            toggleTrue = false;
            jLabel1.setText("NOW EDITING:");
            jLabel1.setForeground(java.awt.Color.RED);
            jButton_Update.setEnabled(true);
            jButton_Create.setEnabled(false);
            
        // Edit toggled off, reset fields to blank for creating a new activity
        } else {
            resetTextFields();
            toggleTrue = true;
            jLabel1.setText("Name:");
            jLabel1.setForeground(java.awt.Color.WHITE);
            jButton_Update.setEnabled(false);
            jButton_Create.setEnabled(true);
        }
    }

    // Reset text fields/clears the text fields
    public void resetTextFields() {
        jComboBox_MaxTime.setSelectedIndex(0);

        jComboBox_IdealTime.setSelectedIndex(0);

        jComboBox_MaxCost.setSelectedIndex(0);

        jComboBox_ActivityType.setSelectedIndex(0);

        jTextField_Name.setText("");
    }


 // Button to create new activites
    private void jButton_CreateActionPerformed(java.awt.event.ActionEvent evt) {
        // Check for internet connection/succesful database update
        boolean updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
        
        // Execute MySQL insert statement for new activity
        String query = "INSERT INTO `activities` (name,maxtime,idealtime,maxcost,activityType) VALUES ('"
                        + jTextField_Name.getText() + "', " + jComboBox_MaxTime.getSelectedItem()
                        + ", " + jComboBox_IdealTime.getSelectedItem() + ", "
                        + jComboBox_MaxCost.getSelectedItem() + ", '" + jComboBox_ActivityType.getSelectedItem() + "')";
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
        
        // Check for internet connection/succesful database update
        updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
    }

 // Button to update activities
    private void jButton_UpdateActionPerformed(java.awt.event.ActionEvent evt) {
        jLabel1.setText("Name:");
        jLabel1.setForeground(java.awt.Color.WHITE);
        jButton_Update.setEnabled(false);
        jButton_Create.setEnabled(true);
        
        boolean updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
        String query = "UPDATE `activities` SET `name`='"+jTextField_Name.getText()+"',`maxtime`='"
        +jComboBox_MaxTime.getSelectedItem()+"',`idealtime`='"+jComboBox_IdealTime.getSelectedItem()+"',`maxcost`='"
        +jComboBox_MaxCost.getSelectedItem()+"', `activityType`='"+jComboBox_ActivityType.getSelectedItem()+"' WHERE `id` = "+currentID;
        resetTextFields();
        jButton_Update.setEnabled(false);
        jButton_Create.setEnabled(true);
        executeSQlQuery(query, "Updated");
        
        updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
    }


 // Button delete activities
    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        boolean updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
        jButton_Update.setEnabled(false);
        jButton_Create.setEnabled(true);
        String query = "DELETE FROM `activities` WHERE id = "+currentID;
         executeSQlQuery(query, "Deleted");
        updated = false;
        do {
            updated = PlanitRunner.updateActivityList();
        } while(!updated);
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton_Update;
    private javax.swing.JButton jButton_Create;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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

        public void windowClosing(WindowEvent e)
    {
        try {
            dispose(); //Close window
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void windowOpened(WindowEvent e) {
      this.mWindow.disableMyButton();
    }
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {
      this.mWindow.enableMyButton();
    }
}
