package GUI;

import JDBC.DatabaseConnector;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author catib
 */
public final class AdminUI extends javax.swing.JFrame {
    int serialID;
    /**
     * Creates new form UserUI
     */
    public AdminUI() {
        initComponents();
        fetchDataFromDatabase();
    }

    public void fetchDataFromDatabase() {
    try{
        // Get a database connection
        Connection con = DatabaseConnector.getConnection();
        // Create a statement
        Statement st = con.createStatement();
        // Execute the query (replace with your actual table name)
        ResultSet rs = st.executeQuery("SELECT SerialID, MeterID, FirstName, LastName, Address, ContactNumber, Email  FROM `consumerinfo`");
        // Get column names from ResultSetMetaData
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        // Set the table model to the JTable
        jTable1.setModel(model);
        
        // for jTable2
        rs = st.executeQuery("SELECT * FROM concessionaire");
        // Get column names from ResultSetMetaData
        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        // Set the table model to the JTable
        jTable2.setModel(model);
        
        // for forDisconnections
        String sql = "SELECT ci.SerialID, ci.FirstName, ci.LastName, " +
             "(COALESCE(SUM(c.Amount), 0) - COALESCE(SUM(d.Amount), 0)) AS Balance " +
             "FROM consumerinfo ci " +
             "LEFT JOIN charge c ON ci.SerialID = c.SerialID " +
             "LEFT JOIN debt d ON ci.SerialID = d.SerialID AND d.DueDate < CURDATE() " +  // Condition moved to JOIN
             "GROUP BY ci.SerialID " +
             "HAVING Balance > 0";


        rs = st.executeQuery(sql);

        // Get column names from ResultSetMetaData
        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        // Set the table model to the JTable
        jTable3.setModel(model);

        // Close resources
        rs.close();
        st.close();
        DatabaseConnector.closeConnection(con);

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        // Handle exceptions appropriately (e.g., display error message)
    }
}
    
public static int generateSerialID(String firstName, String lastName, String password) throws SQLException, ClassNotFoundException {
    int serialID = 0; // Default value for existing users

    try (Connection con = DatabaseConnector.getConnection()) {
        // 1. Check if the name combination exists in the database
        String query = "SELECT SerialID FROM consumerinfo WHERE FirstName = ? AND LastName = ?";
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(query);
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // 2. If a match is found, return the existing SerialID
            serialID = rs.getInt("SerialID");
        } else {
            // 3. If no match is found, predict the next available SerialID
            String maxSerialIDQuery = "SELECT MAX(SerialID) AS maxSerialID FROM consumerinfo";
            PreparedStatement maxStmt = (PreparedStatement) con.prepareStatement(maxSerialIDQuery);
            ResultSet maxRs = maxStmt.executeQuery();
            
            if (maxRs.next()) {
                serialID = maxRs.getInt("maxSerialID") + 1; // Predict next SerialID
            } else {
                serialID = 1; // If the table is empty, start with SerialID = 1
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return serialID;
}
public static int generateMeterID() throws SQLException, ClassNotFoundException {
    int meterID = 0; // Default value for new meters

    try (Connection con = DatabaseConnector.getConnection()) {
        // 1. Fetch the highest MeterID in the database to predict the next available MeterID
        String maxMeterIDQuery = "SELECT MAX(MeterID) AS maxMeterID FROM watermeter";
        PreparedStatement maxStmt = (PreparedStatement) con.prepareStatement(maxMeterIDQuery);
        ResultSet maxRs = maxStmt.executeQuery();
        
        if (maxRs.next()) {
            meterID = maxRs.getInt("maxMeterID") + 1; // Predict next MeterID
        } else {
            meterID = 1; // If the table is empty, start with MeterID = 1
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return meterID;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        contactNumField = new javax.swing.JTextField();
        consessionnaireBox = new javax.swing.JComboBox<>();
        Submit = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        serialIDField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        meterIDField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        passwordField = new javax.swing.JTextField();
        txt = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jButton5 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        disconnectionTable1 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        disconnectionTable = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jDialog1.setTitle("Add New Consumer");
        jDialog1.setResizable(false);
        jDialog1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Last  Name: ");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("First  Name: ");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Email: ");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Contact  Number: ");

        firstNameField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        firstNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameFieldActionPerformed(evt);
            }
        });

        lastNameField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lastNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameFieldActionPerformed(evt);
            }
        });

        emailField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        contactNumField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        contactNumField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactNumFieldActionPerformed(evt);
            }
        });

        consessionnaireBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NasugbuWaters", "BalayanWaterSystem", "LemeryWaterDistrict", "CalataganWaterElement"
            + "" }));
consessionnaireBox.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        consessionnaireBoxActionPerformed(evt);
    }
    });

    Submit.setBackground(new java.awt.Color(214, 255, 231));
    Submit.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
    Submit.setText("SUBMIT");
    Submit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SubmitActionPerformed(evt);
        }
    });

    jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
    jLabel8.setText("Serial ID:");

    serialIDField.setEditable(false);
    serialIDField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    serialIDField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            serialIDFieldActionPerformed(evt);
        }
    });

    jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
    jLabel10.setText("Consessionnaire:");

    jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
    jLabel11.setText("Meter ID:");

    meterIDField.setEditable(false);
    meterIDField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    meterIDField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            meterIDFieldActionPerformed(evt);
        }
    });

    jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
    jLabel12.setText("Password:");

    passwordField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    passwordField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            passwordFieldActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(79, 79, 79)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(232, 232, 232))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(firstNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addComponent(passwordField))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(serialIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(83, 83, 83)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(meterIDField)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(118, 118, 118))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(consessionnaireBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel7))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(contactNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                    .addComponent(Submit)
                    .addGap(128, 128, 128))))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(53, 53, 53)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jLabel6))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(27, 27, 27)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(meterIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18))))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(serialIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(28, 28, 28)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9)
                .addComponent(contactNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(27, 27, 27)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(7, 7, 7)
                    .addComponent(jLabel10))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consessionnaireBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Submit)))
            .addContainerGap(51, Short.MAX_VALUE))
    );

    jDialog1.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 59, 730, 380));

    txt.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 24)); // NOI18N
    txt.setText("WATER  BILLING  SYSTEM");
    jDialog1.getContentPane().add(txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 400, 30));

    bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Water Systems Earth Science Presentation in Blue White Illustrated Style (1) (1).jpg"))); // NOI18N
    bg.setText("background");
    jDialog1.getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 950, 520));

    jDialog2.setTitle("Meter Reading");
    jDialog2.setBackground(new java.awt.Color(0, 155, 155));
    jDialog2.setLocationByPlatform(true);
    jDialog2.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanel5.setBackground(new java.awt.Color(0, 102, 102));
    jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel2.setText("Serial ID:");
    jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, -1, -1));

    jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel4.setText("Previous Reading:");
    jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, 25));

    jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel13.setText("Current Reading:");
    jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

    jTextField1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
        }
    });
    jPanel5.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 110, -1));
    jPanel5.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 110, -1));

    jTextField3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField3ActionPerformed(evt);
        }
    });
    jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 110, -1));

    jTextField4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField4ActionPerformed(evt);
        }
    });
    jPanel5.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 110, -1));

    jButton4.setBackground(new java.awt.Color(208, 249, 255));
    jButton4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jButton4.setText("Submit");
    jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 97, -1));

    jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel14.setText("Meter ID:");
    jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, 24));

    jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg small version.jpg"))); // NOI18N
    jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 420, 250));

    jDialog2.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 230));

    jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    jFrame1.setLocationByPlatform(true);
    jFrame1.setMinimumSize(new java.awt.Dimension(930, 540));
    jFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jButton5.setBackground(new java.awt.Color(224, 255, 255));
    jButton5.setFont(new java.awt.Font("STXinwei", 1, 12)); // NOI18N
    jButton5.setText("New Reading");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });
    jFrame1.getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, -1, 30));

    jTabbedPane2.setPreferredSize(new java.awt.Dimension(930, 540));

    jPanel6.setOpaque(false);

    jTable4.setAutoCreateRowSorter(true);
    jTable4.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jScrollPane3.setViewportView(jTable4);

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jTabbedPane2.addTab("                    Consumers                    ", jPanel6);

    jPanel7.setOpaque(false);

    jTable5.setAutoCreateRowSorter(true);
    jTable5.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jTable5.setOpaque(false);
    jScrollPane4.setViewportView(jTable5);

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(120, 120, 120))
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jTabbedPane2.addTab("                    Consessionaires                    ", jPanel7);

    jPanel8.setOpaque(false);

    disconnectionTable1.setViewportView(jTable6);

    javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(disconnectionTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(disconnectionTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
            .addContainerGap())
    );

    jTabbedPane2.addTab("                    For Disconnection                    ", jPanel8);

    jFrame1.getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 850, 440));

    jLabel16.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 24)); // NOI18N
    jLabel16.setText("WATER BILLING SYSTEM");
    jFrame1.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 360, 40));

    jButton6.setBackground(new java.awt.Color(224, 255, 255));
    jButton6.setFont(new java.awt.Font("STXinwei", 1, 12)); // NOI18N
    jButton6.setText("Add new consumer");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
        }
    });
    jFrame1.getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, 30));

    jButton7.setBackground(new java.awt.Color(224, 255, 255));
    jButton7.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton7.setText("Log   out");
    jButton7.setPreferredSize(new java.awt.Dimension(87, 25));
    jButton7.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton7ActionPerformed(evt);
        }
    });
    jFrame1.getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 130, -1));

    jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Water Systems Earth Science Presentation in Blue White Illustrated Style (1) (1).jpg"))); // NOI18N
    jFrame1.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -8, 990, 560));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setLocationByPlatform(true);
    setMinimumSize(new java.awt.Dimension(930, 540));
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jButton2.setBackground(new java.awt.Color(224, 255, 255));
    jButton2.setFont(new java.awt.Font("STXinwei", 1, 12)); // NOI18N
    jButton2.setText("New Reading");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, -1, 30));

    jTabbedPane1.setPreferredSize(new java.awt.Dimension(930, 540));

    jPanel1.setOpaque(false);

    jTable1.setAutoCreateRowSorter(true);
    jTable1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jScrollPane1.setViewportView(jTable1);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jTabbedPane1.addTab("                    Consumers                    ", jPanel1);

    jPanel2.setOpaque(false);

    jTable2.setAutoCreateRowSorter(true);
    jTable2.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jTable2.setOpaque(false);
    jScrollPane2.setViewportView(jTable2);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(120, 120, 120))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jTabbedPane1.addTab("                    Consessionaires                    ", jPanel2);

    jPanel3.setOpaque(false);

    disconnectionTable.setViewportView(jTable3);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(disconnectionTable, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(disconnectionTable, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
            .addContainerGap())
    );

    jTabbedPane1.addTab("                    For Disconnection                    ", jPanel3);

    getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 850, 440));

    jLabel1.setFont(new java.awt.Font("Rockwell Extra Bold", 1, 24)); // NOI18N
    jLabel1.setText("WATER BILLING SYSTEM");
    getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 360, 40));

    jButton1.setBackground(new java.awt.Color(224, 255, 255));
    jButton1.setFont(new java.awt.Font("STXinwei", 1, 12)); // NOI18N
    jButton1.setText("Add new consumer");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, 30));

    jButton3.setBackground(new java.awt.Color(224, 255, 255));
    jButton3.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton3.setText("Log   out");
    jButton3.setPreferredSize(new java.awt.Dimension(87, 25));
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 130, -1));

    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Water Systems Earth Science Presentation in Blue White Illustrated Style (1) (1).jpg"))); // NOI18N
    getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -8, 990, 560));

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialog1.pack();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed
    private void meterIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meterIDFieldActionPerformed

    }//GEN-LAST:event_meterIDFieldActionPerformed
    private void serialIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serialIDFieldActionPerformed
    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        jDialog1.dispose();
    }//GEN-LAST:event_SubmitActionPerformed
    private void consessionnaireBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consessionnaireBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consessionnaireBoxActionPerformed
    private void contactNumFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactNumFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactNumFieldActionPerformed
    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed
     private void lastNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameFieldActionPerformed
        String firstName = firstNameField.getText().trim(); // Get first name
        String lastName = lastNameField.getText().trim();   // Get last name
        try {
                int serialID = generateSerialID(firstName, lastName, null); 
                serialIDField.setText(String.valueOf(serialID));
                int meterID = generateMeterID();
                meterIDField.setText(String.valueOf(meterID));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lastNameFieldActionPerformed

    private void firstNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameFieldActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDialog2.pack();
        jDialog2.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submit;
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> consessionnaireBox;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JScrollPane disconnectionTable;
    private javax.swing.JScrollPane disconnectionTable1;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JTextField meterIDField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField serialIDField;
    private javax.swing.JLabel txt;
    // End of variables declaration//GEN-END:variables
}
