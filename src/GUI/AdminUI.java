package GUI;

import JDBC.DatabaseConnector;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
        String sql = "SELECT * FROM concessionaire";


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
public int getSerialID(String ID) throws SQLException, ClassNotFoundException {
    int serialID = -1;

    try (Connection con = DatabaseConnector.getConnection()) {
        String query = "SELECT SerialID FROM consumerinfo WHERE MeterID = ?";
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            serialID = rs.getInt("SerialID");
        } else {
            // Handle the case where no matching record is found
            System.out.println("No matching record found for MeterID: " + ID);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception, e.g., log the error or throw a custom exception
    }

    return serialID;
}
public static int generateSerialID() throws SQLException, ClassNotFoundException {
    int serialID = 0; 

    try (Connection con = DatabaseConnector.getConnection()) {

        String maxSerialIDQuery = "SELECT MAX(SerialID) AS maxSerialID FROM consumerinfo";
        PreparedStatement maxStmt = (PreparedStatement) con.prepareStatement(maxSerialIDQuery);
        ResultSet maxRs = maxStmt.executeQuery();

        if (maxRs.next()) {
            serialID = maxRs.getInt("maxSerialID") + 1; 
        } else {
            serialID = 1; // If the table is empty, start with SerialID = 1
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
public static int previousReading(String ID) throws SQLException, ClassNotFoundException {
    int reading = 0; 
    try (Connection con = DatabaseConnector.getConnection()) {       
        String query = "SELECT PresentReading FROM watermeter where MeterID = ?";
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(query);
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {reading = rs.getInt("PresentReading");
    }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reading;
}

public static int checkIfPaid(String ID) throws SQLException, ClassNotFoundException {
    try (Connection con = DatabaseConnector.getConnection()) {
        String query = "SELECT isPaid FROM watermeter WHERE MeterID = ?;";
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("isPaid");
        } else {
            return -1;
        }
    }
}
    
public int concessionaire(String name){
    switch(name){
        case "NasugbuWaters" -> {
            return 1;
            }
        case "BalayanWaterSystem" -> {
            return 2;
            }
        case "LemeryWaterDistrict" -> {
            return 3;
            }
        case "CalataganWaterElement" -> {
            return 4;
            }
    }
        return 0;
}
public void generatebills() {
    try (Connection con = DatabaseConnector.getConnection()) {       
        // Query to fetch necessary billing data
        String query = "SELECT ci.SerialID, d.DebtID, c.ChargeID, " +
                       "(COALESCE(c.ChargeAmount, 0) + COALESCE(d.AmountDue, 0)) AS TotalAmount, " +
                       "CURDATE() + INTERVAL 30 DAY AS DueDate " +
                       "FROM consumerinfo ci " +
                       "LEFT JOIN debt d ON ci.SerialID = d.MeterID " +
                       "LEFT JOIN charge c ON ci.SerialID = c.SerialID";    
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        String insertQuery = "INSERT INTO bill (SerialID, DebtID, ChargeID, BillingAmount, DueDate) " +
                             "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = (PreparedStatement) con.prepareStatement(insertQuery);
        while (rs.next()) {
            int SerialID = rs.getInt("SerialID");
            int DebtID = rs.getInt("DebtID");
            int ChargeID = rs.getInt("ChargeID");
            float Amount = rs.getFloat("TotalAmount");
            Date DueDate = rs.getDate("DueDate");

            // Insert the data into the bill table
            insertStmt.setInt(1, SerialID);
            insertStmt.setInt(2, DebtID);
            insertStmt.setInt(3, ChargeID);
            insertStmt.setFloat(4, Amount);
            insertStmt.setDate(5, DueDate);

            insertStmt.executeUpdate();
        }

        System.out.println("Billing data successfully inserted.");
    } catch (SQLException e) {
        e.printStackTrace(); // Print the stack trace for easier debugging
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
    }
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
        jLabel16 = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        txt = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jDialog3 = new javax.swing.JDialog();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        generatebills1 = new javax.swing.JButton();
        generatebills = new javax.swing.JButton();
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
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
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

    jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
    jLabel16.setText("Address:");

    addressField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    addressField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addressFieldActionPerformed(evt);
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
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(addressField))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contactNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(136, 136, 136)
                    .addComponent(Submit)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(firstNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addComponent(passwordField))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(48, 48, 48)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(serialIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(meterIDField)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(consessionnaireBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGap(118, 118, 118))))
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
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(meterIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(serialIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jLabel10)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(contactNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(consessionnaireBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(Submit)
                .addComponent(jLabel16)
                .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(38, Short.MAX_VALUE))
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

    jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel4.setText("Previous Reading:");
    jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 25));

    jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel13.setText("Current Reading:");
    jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

    jTextField2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField2ActionPerformed(evt);
        }
    });
    jPanel5.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 110, -1));

    jTextField3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField3ActionPerformed(evt);
        }
    });
    jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 110, -1));

    jTextField4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField4ActionPerformed(evt);
        }
    });
    jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField4KeyReleased(evt);
        }
    });
    jPanel5.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 110, -1));

    jButton4.setBackground(new java.awt.Color(208, 249, 255));
    jButton4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jButton4.setText("Submit");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });
    jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 97, -1));

    jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel14.setText("Meter ID:");
    jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, 24));

    jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg small version.jpg"))); // NOI18N
    jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 420, 250));

    jDialog2.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 230));

    jDialog3.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel15.setText("Serial ID:");
    jDialog3.getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 94, 60, -1));

    jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel19.setText("Amount:");
    jDialog3.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 70, -1));

    jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel20.setText("Meter ID:");
    jDialog3.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 88, -1, 25));

    jLabel21.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel21.setText("Account: ");
    jDialog3.getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 70, -1));

    jButton11.setBackground(new java.awt.Color(221, 249, 228));
    jButton11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
    jButton11.setText("Pay");
    jButton11.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton11ActionPerformed(evt);
        }
    });
    jDialog3.getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 50, 30));

    jTextField5.setEditable(false);
    jTextField5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jTextField5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField5ActionPerformed(evt);
        }
    });
    jDialog3.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 91, 80, -1));

    jTextField6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jTextField6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField6ActionPerformed(evt);
        }
    });
    jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextField6KeyReleased(evt);
        }
    });
    jDialog3.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 80, -1));

    jTextField7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jDialog3.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 131, 80, -1));

    jTextField8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jDialog3.getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 166, 80, -1));

    jComboBox1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gcash", "Maya", "Visa/Bancnet" }));
    jDialog3.getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 80, -1));

    jLabel22.setFont(new java.awt.Font("STKaiti", 3, 20)); // NOI18N
    jLabel22.setText("Payment  Method");
    jDialog3.getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 10, 180, -1));

    jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/maintenance (1).png"))); // NOI18N
    jLabel23.setText("jLabel23");
    jDialog3.getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 47, -1));

    jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/water-bill (1).png"))); // NOI18N
    jLabel24.setText("jLabel23");
    jDialog3.getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 47, -1));

    jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image-460x240.jpg"))); // NOI18N
    jLabel25.setText("jLabel25");
    jDialog3.getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 230));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setLocationByPlatform(true);
    setMinimumSize(new java.awt.Dimension(930, 540));
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jButton2.setBackground(new java.awt.Color(211, 252, 252));
    jButton2.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton2.setText("New Reading");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, -1, 30));

    generatebills1.setBackground(new java.awt.Color(211, 252, 252));
    generatebills1.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    generatebills1.setText("Add Charges");
    generatebills1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            generatebills1ActionPerformed(evt);
        }
    });
    getContentPane().add(generatebills1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 510, -1, 30));

    generatebills.setBackground(new java.awt.Color(211, 252, 252));
    generatebills.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    generatebills.setText("Generate Bills");
    generatebills.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            generatebillsActionPerformed(evt);
        }
    });
    getContentPane().add(generatebills, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, -1, 30));

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

    jButton1.setBackground(new java.awt.Color(211, 252, 252));
    jButton1.setFont(new java.awt.Font("STXinwei", 1, 12)); // NOI18N
    jButton1.setText("Add new consumer");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, 30));

    jButton3.setBackground(new java.awt.Color(255, 95, 95));
    jButton3.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton3.setForeground(new java.awt.Color(255, 255, 255));
    jButton3.setText("Log   out");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 510, 130, -1));

    jButton8.setBackground(new java.awt.Color(211, 252, 252));
    jButton8.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton8.setText("Open User");
    jButton8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton8ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, 110, 30));

    jButton9.setBackground(new java.awt.Color(209, 244, 210));
    jButton9.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/loading-arrow.png"))); // NOI18N
    jButton9.setText("Refresh");
    getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 25, 110, -1));

    jButton10.setBackground(new java.awt.Color(211, 252, 252));
    jButton10.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton10.setText("Payment");
    jButton10.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton10ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, 110, 30));

    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Water Systems Earth Science Presentation in Blue White Illustrated Style (1) (1).jpg"))); // NOI18N
    getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -8, 990, 560));

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
                int serialID = generateSerialID(); 
                serialIDField.setText(String.valueOf(serialID));
                int meterID = generateMeterID();
                meterIDField.setText(String.valueOf(meterID));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jDialog1.pack();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
        LoginPage loginUI = new LoginPage();
        loginUI.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed
    private void meterIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meterIDFieldActionPerformed

    }//GEN-LAST:event_meterIDFieldActionPerformed
    private void serialIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serialIDFieldActionPerformed
    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        try (Connection con = DatabaseConnector.getConnection()) {
            String fname = firstNameField.getText();
            String lname = lastNameField.getText();
            String pass = passwordField.getText();
            String add = addressField.getText();
            String email = emailField.getText();
            String contact = contactNumField.getText();
            int concessionaire = concessionaire((String) consessionnaireBox.getSelectedItem());
            int mID = generateMeterID();
            String insertConsumerQuery = """
                INSERT INTO consumerinfo 
                (Password, FirstName, LastName, Address, ContactNumber, Email, MeterID) 
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;
            try (PreparedStatement consumerStmt = (PreparedStatement) con.prepareStatement(insertConsumerQuery)) {
                consumerStmt.setString(1, pass);
                consumerStmt.setString(2, fname);
                consumerStmt.setString(3, lname);
                consumerStmt.setString(4, add);
                consumerStmt.setString(5, contact);
                consumerStmt.setString(6, email);
                consumerStmt.setInt(7, mID);
                consumerStmt.executeUpdate();
            }
            String insertWaterMeterQuery = """
                INSERT INTO watermeter 
                (PresentReading, PreviousReading, MeterID, ConcessionaireID) 
                VALUES (0, 0, ?, ?);
                """;
            try (PreparedStatement waterMeterStmt = (PreparedStatement) con.prepareStatement(insertWaterMeterQuery)) {
                waterMeterStmt.setInt(1, mID);
                waterMeterStmt.setInt(2, concessionaire);
                waterMeterStmt.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Consumer successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
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
        
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            Integer ID = (Integer) jTable1.getValueAt(selectedRow, 0);
            String idString = ID.toString();

            UserUI userFrame = new UserUI(idString,true);
            userFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row.");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void addressFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressFieldActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String ID = jTextField4.getText();
        try {
            int prevReading = previousReading(ID);
            jTextField2.setText(String.valueOf(prevReading));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String ID = jTextField4.getText();
        int paid = 0;

        try {
            paid = checkIfPaid(ID);  // Check payment status
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection con = DatabaseConnector.getConnection()) {
            if (paid == 1) {
                
                String currentReading = jTextField3.getText();  

                String getReadingQuery = "SELECT PresentReading FROM watermeter WHERE MeterID = ?;";
                PreparedStatement ps = (PreparedStatement) con.prepareStatement(getReadingQuery);
                ps.setString(1, ID);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    float presentReading = rs.getFloat("PresentReading");
                    String updateHistoryQuery = "INSERT INTO reading_history (MeterID, PreviousReading, DateRecorded) VALUES (?, ?, CURDATE())";
                    PreparedStatement historyStmt = (PreparedStatement) con.prepareStatement(updateHistoryQuery);
                    historyStmt.setString(1, ID);
                    historyStmt.setFloat(2, presentReading);
                    historyStmt.executeUpdate();
                }

                String updatePaidStatusQuery = "UPDATE watermeter SET isPaid = 0 WHERE MeterID = ?;";
                PreparedStatement updatePaidStmt = (PreparedStatement) con.prepareStatement(updatePaidStatusQuery);
                updatePaidStmt.setString(1, ID);
                updatePaidStmt.executeUpdate();

                String updateReadingQuery = "UPDATE watermeter SET PresentReading = ? WHERE MeterID = ?;";
                PreparedStatement updateReadingStmt = (PreparedStatement) con.prepareStatement(updateReadingQuery);
                updateReadingStmt.setString(1, currentReading);  // Assuming the current reading is a String, can be converted to float if necessary
                updateReadingStmt.setString(2, ID);
                updateReadingStmt.executeUpdate();

                System.out.println("Payment updated and readings saved.");

            } else {
                String currentReading = jTextField3.getText();

                String updateReadingQuery = "UPDATE watermeter SET PresentReading = ? WHERE MeterID = ?;";
                PreparedStatement updateReadingStmt = (PreparedStatement) con.prepareStatement(updateReadingQuery);
                updateReadingStmt.setString(1, currentReading);  // Can cast this to float if required
                updateReadingStmt.setString(2, ID);
                updateReadingStmt.executeUpdate();

                System.out.println("Reading updated, user has not paid.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    jDialog2.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed
        
    private void generatebillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebillsActionPerformed
        generatebills();
    }//GEN-LAST:event_generatebillsActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        String ID = jTextField6.getText();
        try {
            int sID = getSerialID(ID);
            jTextField5.setText(String.valueOf(sID));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jDialog3.pack();
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void generatebills1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebills1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generatebills1ActionPerformed

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
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> consessionnaireBox;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JScrollPane disconnectionTable;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JButton generatebills;
    private javax.swing.JButton generatebills1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JTextField meterIDField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField serialIDField;
    private javax.swing.JLabel txt;
    // End of variables declaration//GEN-END:variables
}
