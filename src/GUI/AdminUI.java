package GUI;

import JDBC.DatabaseConnector;
import com.mysql.jdbc.PreparedStatement;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;



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
        setupRowSorter();
    }

public void fetchDataFromDatabase() {
    try{
        Connection con = DatabaseConnector.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT SerialID, MeterID, FirstName, LastName, Address, ContactNumber, Email  "
                + "FROM `consumerinfo` WHERE isConnected = 1");
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        jTable1.setModel(model);

        //concessionaire
        rs = st.executeQuery("SELECT * FROM concessionaire");
        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        jTable2.setModel(model);
        
        // Arrears
        String sql = "SELECT b.SerialID, ci.FirstName, ci.LastName,ci.Address, b.BillingID, b.BillingAmount, b.DueDate, ci.isConnected "
                + "FROM bill b JOIN consumerinfo ci ON b.SerialID = ci.SerialID WHERE b.isPaid = 0 AND b.DueDate < CURDATE();";


        rs = st.executeQuery(sql);

        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }
        jTable5.setModel(model);
        
        String forDisc = "SELECT DISTINCT ci.SerialID, ci.FirstName, ci.LastName, ci.MeterID FROM bill b JOIN consumerinfo ci ON "
                + "b.SerialID = ci.SerialID WHERE b.DueDate < CURDATE() AND b.isPaid = 0 AND ci.isConnected = 1 AND b.SerialID IN "
                + "( SELECT SerialID FROM bill WHERE DueDate < CURDATE() AND isPaid = 0 GROUP BY SerialID HAVING COUNT(SerialID) = 3 ) "
                + "ORDER BY ci.SerialID;";

        rs = st.executeQuery(forDisc);

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

        String Disconnected = "SELECT SerialID, MeterID, FirstName, LastName, Address, ContactNumber, Email  "
                + "FROM `consumerinfo` WHERE isConnected = 0";

        rs = st.executeQuery(Disconnected);

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
        jTable4.setModel(model);
               
        String chrge = "SELECT ChargeID, SerialID, ChargeAmount, DateIncurred, Type FROM charge WHERE isDebt = 0";


        rs = st.executeQuery(chrge);

        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }
        chargesTable.setModel(model);
        
        String ledger = "SELECT LedgerID, BillingID, AmountPaid, PaymentDate FROM ledger";
        rs = st.executeQuery(ledger);

        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }
        allLedgerTable.setModel(model);
        
        String Bills = "SELECT BillingID, SerialID, DebtID, ChargeID, BillingAmount, DueDate from bill";
        rs = st.executeQuery(Bills);

        metaData = rs.getMetaData();
        columnCount = metaData.getColumnCount();
        columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }
        allBillsTable.setModel(model);
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
        String query = "SELECT SerialID FROM bill WHERE BillingID = ?";
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
public float getBillAmount(String ID) throws SQLException, ClassNotFoundException {
    int serialID = -1;

    try (Connection con = DatabaseConnector.getConnection()) {
        String query = "SELECT BillingAmount FROM bill WHERE BillingID = ?";
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
        ps.setString(1, ID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            serialID = rs.getInt("BillingAmount");
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
public void processPayment(String BillingID) {
    Connection con = null;
    try {
        con = DatabaseConnector.getConnection();
        con.setAutoCommit(false);

        // Validate BillingID and check if already paid
        String checkQuery = "SELECT isPaid FROM bill WHERE BillingID = ?";
        PreparedStatement checkStmt = (PreparedStatement) con.prepareStatement(checkQuery);
        checkStmt.setString(1, BillingID);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Bill Does Not Exists");
            throw new SQLException("Invalid BillingID: " + BillingID);
            
        }
        if (rs.getBoolean("isPaid")) {
            JOptionPane.showMessageDialog(null, "AlreadyPaid");
            throw new SQLException("Bill with BillingID " + BillingID + " is already paid.");
        }

        // Insert into ledger
        String insertLedgerQuery = "INSERT INTO ledger (BillingID, SerialID, AmountPaid, PaymentDate) " +
                                   "SELECT BillingID, SerialID, BillingAmount, CURDATE() " +
                                   "FROM bill WHERE BillingID = ?";
        PreparedStatement insertLedgerStmt = (PreparedStatement) con.prepareStatement(insertLedgerQuery);
        insertLedgerStmt.setString(1, BillingID);
        insertLedgerStmt.executeUpdate();

        // Update isPaid in bill
        String updateBillQuery = "UPDATE bill SET isPaid = 1 WHERE BillingID = ?";
        PreparedStatement updateStmt = (PreparedStatement) con.prepareStatement(updateBillQuery);
        updateStmt.setString(1, BillingID);
        updateStmt.executeUpdate();
        
        String updateChargeQuery = "UPDATE charge SET isDebt = 1 WHERE ChargeID = (SELECT ChargeID FROM bill WHERE BillingID = ?)";
        PreparedStatement updatStmt = (PreparedStatement) con.prepareStatement(updateChargeQuery);
        updatStmt.setString(1, BillingID); // Set the BillingID in the prepared statement
        updatStmt.executeUpdate(); // Execute the update

        con.commit();
        System.out.println("Payment processed successfully for BillingID: " + BillingID);
    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        if (con != null) {
            try {
                con.rollback();
                System.out.println("Transaction rolled back due to an error.");
            } catch (SQLException rollbackEx) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
        }
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException closeEx) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, closeEx);
            }
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
        // Query to fetch the last bill generation date
        String checkGenerationQuery = "SELECT generation_date FROM bill_generation_log ORDER BY generation_date DESC LIMIT 1";
        PreparedStatement checkStmt = (PreparedStatement)con.prepareStatement(checkGenerationQuery);
        ResultSet rs = checkStmt.executeQuery();

        // Get the current date using java.sql.Date
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); // Get current date

        if (rs.next()) {
            // If a previous generation date exists, compare it with the current date
            java.sql.Date lastGenerationDate = rs.getDate("generation_date");

            // Add one month to the last generation date
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastGenerationDate);
            cal.add(Calendar.MONTH, 1);

            // Check if the current date is after the next generation date
            if (cal.getTime().before(currentDate)) {
                generateBills(con); // Generate the bills

                // Log the bill generation
                String logGenerationQuery = "INSERT INTO bill_generation_log (generation_date) VALUES (CURDATE())";
                PreparedStatement logStmt = (PreparedStatement)con.prepareStatement(logGenerationQuery);
                logStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Billing data successfully inserted");
                System.out.println("Billing data successfully inserted.");
            } else {
                JOptionPane.showMessageDialog(null, "Bills have already been generated for this period.");
            }
        } else {
            // If no generation date exists, generate bills for the first time
            generateBills(con);

            // Log the bill generation
            String logGenerationQuery = "INSERT INTO bill_generation_log (generation_date) VALUES (CURDATE())";
            PreparedStatement logStmt = (PreparedStatement)con.prepareStatement(logGenerationQuery);
            logStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Billing data successfully inserted");
            System.out.println("Billing data successfully inserted.");
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle error
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void generateBills(Connection con) {
    try {
        // Query to fetch necessary billing data
        String query = "SELECT ci.SerialID, d.DebtID, c.ChargeID, " +
                       "AmountDue AS TotalAmount, " +
                       "CURDATE() + INTERVAL 30 DAY AS DueDate " +
                       "FROM consumerinfo ci " +
                       "LEFT JOIN debt d ON ci.SerialID = d.MeterID " +
                       "LEFT JOIN charge c ON ci.SerialID = c.SerialID";

        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        String insertQuery = "INSERT INTO bill (SerialID, DebtID, ChargeID, BillingAmount, DueDate, isPaid) " +
                             "VALUES (?, ?, ?, ?, ?, 0)";
        PreparedStatement insertStmt = (PreparedStatement) con.prepareStatement(insertQuery);

        while (rs.next()) {
            int SerialID = rs.getInt("SerialID");
            int DebtID = rs.getInt("DebtID");
            int ChargeID = rs.getInt("ChargeID");

            // Check if either DebtID or ChargeID is 0
            if (DebtID == 0 && ChargeID == 0) {
                System.out.println("Skipping bill generation for SerialID: " + SerialID + " due to DebtID or ChargeID being 0.");
                continue; // Skip this iteration and move to the next row
            }

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

        JOptionPane.showMessageDialog(null, "Billing data successfully inserted");
        System.out.println("Billing data successfully inserted.");
    } catch (SQLException e) {
        e.printStackTrace(); // Print the stack trace for easier debugging
    }
}


public void disconnect(int serialID) throws ClassNotFoundException {
    Connection con = null;
    PreparedStatement disconnectStmt = null;
    try {
        con = DatabaseConnector.getConnection();
        con.setAutoCommit(false);

        String disconnect = "UPDATE consumerinfo SET isConnected = 0 WHERE SerialID = ?;";
        disconnectStmt = (PreparedStatement) con.prepareStatement(disconnect);
        disconnectStmt.setInt(1, serialID);
        
        int rowsAffected = disconnectStmt.executeUpdate();
        con.commit();
        System.out.println("Disconnect successful. Rows affected: " + rowsAffected);
    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback();
                System.err.println("Transaction rolled back due to an error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
        } else {
            System.err.println("Error occurred: " + e.getMessage());
        }
    } finally {
        try {
            if (disconnectStmt != null) disconnectStmt.close();
            if (con != null) con.close();
        } catch (SQLException closeEx) {
            System.err.println("Failed to close resources: " + closeEx.getMessage());
        }
    }
}

public void reconnect(int serialID) throws ClassNotFoundException {
    Connection con = null;
    PreparedStatement disconnectStmt = null;
    try {
        con = DatabaseConnector.getConnection();
        con.setAutoCommit(false);

        String recon = "UPDATE consumerinfo SET isConnected = 1 WHERE SerialID = ?;";
        disconnectStmt = (PreparedStatement) con.prepareStatement(recon);
        disconnectStmt.setInt(1, serialID);
        
        int rowsAffected = disconnectStmt.executeUpdate();
        con.commit();
        System.out.println("Disconnect successful. Rows affected: " + rowsAffected);
    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback();
                System.err.println("Transaction rolled back due to an error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
        } else {
            System.err.println("Error occurred: " + e.getMessage());
        }
    } finally {
        try {
            if (disconnectStmt != null) disconnectStmt.close();
            if (con != null) con.close();
        } catch (SQLException closeEx) {
            System.err.println("Failed to close resources: " + closeEx.getMessage());
        }
    }
}

private void setupRowSorter() {
    // Assuming jTable1 is already initialized by NetBeans
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
}

private boolean existingArrears(int serialID) throws ClassNotFoundException {
    boolean exists = false;
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        con = DatabaseConnector.getConnection();
        String query = "SELECT COUNT(*) " +
                       "FROM bill b " +
                       "JOIN consumerinfo ci ON b.SerialID = ci.SerialID " +
                       "WHERE b.isPaid = 0 AND b.DueDate < CURDATE() AND b.SerialID = ?";
        stmt = (PreparedStatement) con.prepareStatement(query);
        stmt.setInt(1, serialID);
        rs = stmt.executeQuery();

        if (rs.next()) {
            exists = rs.getInt(1) > 0; // If count is greater than 0, arrears exist
        }
    } catch (SQLException e) {
        System.err.println("Error checking existing arrears: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException closeEx) {
            System.err.println("Failed to close resources: " + closeEx.getMessage());
        }
    }

    return exists;
}

public void addLateFees() throws ClassNotFoundException {
    Connection con = null;
    PreparedStatement selectStmt = null;
    PreparedStatement insertChargeStmt = null;
    PreparedStatement updateChargeStmt = null;
    PreparedStatement updateBillStmt = null;
    PreparedStatement lastRunStmt = null;
    PreparedStatement updateLastRunStmt = null;
    ResultSet rs = null;

    // Check if it's the right time of the month
    LocalDate today = LocalDate.now();
    int dayOfMonth = today.getDayOfMonth(); // Ensure this runs only on a specific day (e.g., 1st of the month)
    if (dayOfMonth != 1) {
        System.out.println("Late fees can only be added on the first day of the month.");
        return;
    }

    try {
        con = DatabaseConnector.getConnection();
        con.setAutoCommit(false);

        // Check when the process was last run
        String checkLastRunQuery = "SELECT LastRunDate FROM process_schedule WHERE ProcessName = 'AddLateFees'";
        lastRunStmt = (PreparedStatement) con.prepareStatement(checkLastRunQuery);
        ResultSet lastRunRs = lastRunStmt.executeQuery();

        LocalDate lastRunDate = null;
        if (lastRunRs.next()) {
            lastRunDate = lastRunRs.getDate("LastRunDate").toLocalDate();
        }

        if (lastRunDate != null && ChronoUnit.MONTHS.between(lastRunDate, today) < 1) {
            System.out.println("Late fees have already been added for this month.");
            return;
        }

        // Select overdue bills
        String selectQuery = """
            SELECT b.BillingID, b.SerialID, b.BillingAmount, c.ChargeID, c.ChargeAmount 
            FROM bill b 
            LEFT JOIN charge c ON b.ChargeID = c.ChargeID 
            WHERE b.isPaid = 0 AND b.DueDate < CURDATE();
            """;
        selectStmt = (PreparedStatement) con.prepareStatement(selectQuery);
        rs = selectStmt.executeQuery();

        // Prepare statements
        String insertChargeQuery = "INSERT INTO charge (SerialID, ChargeAmount, DateIncurred, Type) VALUES (?, 50, CURDATE(), 'LateFee')";
        insertChargeStmt = (PreparedStatement) con.prepareStatement(insertChargeQuery, Statement.RETURN_GENERATED_KEYS);

        String updateChargeQuery = "UPDATE charge SET ChargeAmount = ChargeAmount * 1.2 WHERE ChargeID = ?";
        updateChargeStmt = (PreparedStatement) con.prepareStatement(updateChargeQuery);

        String updateBillQuery = "UPDATE bill SET ChargeID = ?, BillingAmount = BillingAmount + ? WHERE BillingID = ?";
        updateBillStmt = (PreparedStatement) con.prepareStatement(updateBillQuery);

        while (rs.next()) {
            int billingID = rs.getInt("BillingID");
            int serialID = rs.getInt("SerialID");
            int chargeID = rs.getInt("ChargeID");
            double billingAmount = rs.getDouble("BillingAmount");

            if (chargeID == 0) {
                // No existing charge, insert new charge
                insertChargeStmt.setInt(1, serialID);
                insertChargeStmt.executeUpdate();

                // Retrieve generated chargeID
                ResultSet generatedKeys = insertChargeStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    chargeID = generatedKeys.getInt(1);
                }
                generatedKeys.close();

                // Update bill with new chargeID and amount
                updateBillStmt.setInt(1, chargeID);
                updateBillStmt.setDouble(2, 50); // Add 50 for late fee
                updateBillStmt.setInt(3, billingID);
                updateBillStmt.executeUpdate();
            } else {
                // Existing charge, increase by 20%
                updateChargeStmt.setInt(1, chargeID);
                updateChargeStmt.executeUpdate();

                // Update bill to reflect the increased charge
                updateBillStmt.setInt(1, chargeID);
                updateBillStmt.setDouble(2, billingAmount * 0.2); // Add 20% of the existing charge
                updateBillStmt.setInt(3, billingID);
                updateBillStmt.executeUpdate();
            }
        }

        // Update last run date
        String updateLastRunQuery = """
            INSERT INTO process_schedule (ProcessName, LastRunDate) 
            VALUES ('AddLateFees', ?) 
            ON DUPLICATE KEY UPDATE LastRunDate = VALUES(LastRunDate);
            """;
        updateLastRunStmt = (PreparedStatement) con.prepareStatement(updateLastRunQuery);
        updateLastRunStmt.setDate(1, java.sql.Date.valueOf(today));
        updateLastRunStmt.executeUpdate();

        con.commit();
        System.out.println("Late fees successfully added to overdue bills.");
    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
        System.err.println("Error adding late fees: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (selectStmt != null) selectStmt.close();
            if (insertChargeStmt != null) insertChargeStmt.close();
            if (updateChargeStmt != null) updateChargeStmt.close();
            if (updateBillStmt != null) updateBillStmt.close();
            if (lastRunStmt != null) lastRunStmt.close();
            if (updateLastRunStmt != null) updateLastRunStmt.close();
            if (con != null) con.close();
        } catch (SQLException closeEx) {
            System.err.println("Failed to close resources: " + closeEx.getMessage());
        }
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
        charges = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        CAmount = new javax.swing.JTextField();
        ChargeSID = new javax.swing.JTextField();
        CDesc = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        generatebills = new javax.swing.JButton();
        generatebills2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        search = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        lateFeeButton = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        chargesTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        disconnect = new javax.swing.JButton();
        disconnectionTable = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        reconnect = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        allBillsTable = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        allLedgerTable = new javax.swing.JTable();
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
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(serialIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jLabel10)
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
    jLabel20.setText("Bill ID:");
    jDialog3.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, -1, 25));

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

    jTextField7.setEditable(false);
    jTextField7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jTextField7.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField7ActionPerformed(evt);
        }
    });
    jDialog3.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 131, 80, -1));

    jTextField8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jTextField8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField8ActionPerformed(evt);
        }
    });
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

    charges.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 3, 18)); // NOI18N
    jLabel2.setText("Charges");
    jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

    jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel17.setText("Serial ID: ");
    jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

    jLabel26.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel26.setText("Amount:");
    jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

    jLabel27.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
    jLabel27.setText("Charges Description:");
    jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

    jButton5.setBackground(new java.awt.Color(224, 248, 228));
    jButton5.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
    jButton5.setText("SUBMIT");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });
    jPanel6.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, -1, -1));

    CAmount.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jPanel6.add(CAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 99, -1));

    ChargeSID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jPanel6.add(ChargeSID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 99, -1));

    CDesc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jPanel6.add(CDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 99, -1));

    jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image-460x240.jpg"))); // NOI18N
    jPanel6.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 230));

    charges.getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 230));

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
    getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 510, -1, 30));

    generatebills.setBackground(new java.awt.Color(211, 252, 252));
    generatebills.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    generatebills.setText("Generate Bills");
    generatebills.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            generatebillsActionPerformed(evt);
        }
    });
    getContentPane().add(generatebills, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, -1, 30));

    generatebills2.setBackground(new java.awt.Color(211, 252, 252));
    generatebills2.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    generatebills2.setText("Add Charges");
    generatebills2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            generatebills2ActionPerformed(evt);
        }
    });
    getContentPane().add(generatebills2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, -1, 30));

    jTabbedPane1.setPreferredSize(new java.awt.Dimension(930, 540));

    jPanel1.setOpaque(false);

    jTable1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jScrollPane1.setViewportView(jTable1);

    search.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            searchKeyReleased(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addComponent(search))
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
            .addContainerGap())
    );

    jTabbedPane1.addTab("   Consumers   ", jPanel1);

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(110, 110, 110))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jTabbedPane1.addTab("Concessionaires", jPanel2);

    jPanel8.setOpaque(false);

    jTable5.setAutoCreateRowSorter(true);
    jTable5.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
    jTable5.setOpaque(false);
    jScrollPane4.setViewportView(jTable5);

    lateFeeButton.setBackground(new java.awt.Color(211, 252, 252));
    lateFeeButton.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    lateFeeButton.setText("Add Late Fees");
    lateFeeButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            lateFeeButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lateFeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lateFeeButton)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("      Arrears     ", jPanel8);

    jScrollPane5.setViewportView(chargesTable);

    javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(35, 35, 35))
    );

    jTabbedPane1.addTab("       Charges       ", jPanel9);

    jPanel3.setOpaque(false);

    disconnect.setBackground(new java.awt.Color(211, 252, 252));
    disconnect.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    disconnect.setText("Disconnect Selected User");
    disconnect.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            disconnectActionPerformed(evt);
        }
    });

    disconnectionTable.setViewportView(jTable3);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(disconnectionTable, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addComponent(disconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(disconnectionTable, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(disconnect)
            .addContainerGap())
    );

    jTabbedPane1.addTab("     For Disconnection    ", jPanel3);

    jPanel7.setOpaque(false);

    jScrollPane3.setViewportView(jTable4);

    reconnect.setBackground(new java.awt.Color(211, 252, 252));
    reconnect.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    reconnect.setText("Reconnect Selected User");
    reconnect.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            reconnectActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3)
                .addComponent(reconnect, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(reconnect)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("Disconnected Consumers", jPanel7);

    jScrollPane6.setViewportView(allBillsTable);

    jScrollPane7.setViewportView(allLedgerTable);

    javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
    jPanel10.setLayout(jPanel10Layout);
    jPanel10Layout.setHorizontalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel10Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addComponent(jScrollPane6))
            .addContainerGap())
    );
    jPanel10Layout.setVerticalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(208, 208, 208))
    );

    jTabbedPane1.addTab("Bills and Ledger", jPanel10);

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
    getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, 30));

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
    getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, 110, 30));

    jButton9.setBackground(new java.awt.Color(209, 244, 210));
    jButton9.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/loading-arrow.png"))); // NOI18N
    jButton9.setText("Refresh");
    jButton9.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton9ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 25, 110, -1));

    jButton10.setBackground(new java.awt.Color(211, 252, 252));
    jButton10.setFont(new java.awt.Font("STXinwei", 1, 14)); // NOI18N
    jButton10.setText("Payment");
    jButton10.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton10ActionPerformed(evt);
        }
    });
    getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 110, 30));

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
        String fname = firstNameField.getText();
        String lname = lastNameField.getText();
        String pass = passwordField.getText();
        String add = addressField.getText();
        String email = emailField.getText();
        String contact = contactNumField.getText();

        try {
            // Validate input fields
            if (fname == null || fname.trim().isEmpty() ||
                lname == null || lname.trim().isEmpty() ||
                pass == null || pass.trim().isEmpty() ||
                add == null || add.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                contact == null || contact.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required and cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit if validation fails
            }

            try (Connection con = DatabaseConnector.getConnection()) {
                int concessionaire = concessionaire((String) consessionnaireBox.getSelectedItem());
                int mID = generateMeterID();
                int inspectorID = (int) (Math.random() * 6) + 1; // Generate a random InspectorID between 1 and 6

                // Insert into consumerinfo table
                String insertConsumerQuery = """
                    INSERT INTO consumerinfo 
                    (Password, FirstName, LastName, Address, ContactNumber, Email, MeterID, InspectorID) 
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                    """;
                try (PreparedStatement consumerStmt = (PreparedStatement) con.prepareStatement(insertConsumerQuery)) {
                    consumerStmt.setString(1, pass);
                    consumerStmt.setString(2, fname);
                    consumerStmt.setString(3, lname);
                    consumerStmt.setString(4, add);
                    consumerStmt.setString(5, contact);
                    consumerStmt.setString(6, email);
                    consumerStmt.setInt(7, mID);
                    consumerStmt.setInt(8, inspectorID); // Add InspectorID
                    consumerStmt.executeUpdate();
                }

                // Insert into watermeter table
                String insertWaterMeterQuery = """
                    INSERT INTO watermeter 
                    (PresentReading, PreviousReading, ReadingDate, PreviousReadingDate, ConcessionaireID) 
                    VALUES (0, 0, CURDATE(), CURDATE(), ?);
                    """;
                try (PreparedStatement waterMeterStmt = (PreparedStatement) con.prepareStatement(insertWaterMeterQuery)) {
                    waterMeterStmt.setInt(1, concessionaire);
                    waterMeterStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Consumer successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        emailField.setText("");
        contactNumField.setText("");
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
            try {
                Integer ID = (Integer) jTable1.getValueAt(selectedRow, 0);
                String idString = ID.toString();
                
                UserUI userFrame = new UserUI(idString);
                userFrame.setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        String meterID = jTextField4.getText();
        String presentReading = jTextField3.getText();
        Connection con = null;

        try {
            con = DatabaseConnector.getConnection();
            // Retrieve the previous reading using the previousReading method
            int previousReading = previousReading(meterID); // Call the previousReading method

            double currentReading = Double.parseDouble(presentReading);

            // Check if the current reading is less than the previous reading
            if (currentReading < previousReading) {
                JOptionPane.showMessageDialog(null, "Warning: Current reading cannot be less than previous reading.", 
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                return; // Stop the execution if the current reading is invalid
            }

            // Proceed with the updates and debt insertion if the reading is valid
            String updateWaterMeterQuery = """
                UPDATE watermeter
                SET 
                    PreviousReading = PresentReading,
                    PreviousReadingDate = ReadingDate,
                    PresentReading = ?,
                    ReadingDate = CURDATE()
                WHERE MeterID = ?;
            """;

            String insertDebtQuery = """
                INSERT INTO debt (MeterID, FromDate, PreviousReading, ToDate, LatestReading, AmountDue)
                SELECT 
                    wm.MeterID,
                    wm.PreviousReadingDate AS FromDate,
                    wm.PreviousReading,
                    wm.ReadingDate AS ToDate,
                    wm.PresentReading AS LatestReading,
                    wm.Consumption * c.PricePerCubicMeter AS AmountDue
                FROM 
                    watermeter wm
                JOIN 
                    concessionaire c ON wm.ConcessionaireID = c.ConcessionaireID
                WHERE 
                    wm.MeterID = ?;
            """;

            con.setAutoCommit(false);
            try (PreparedStatement updateStmt = (PreparedStatement) con.prepareStatement(updateWaterMeterQuery)) {
                updateStmt.setString(1, presentReading);
                updateStmt.setString(2, meterID);
                updateStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = (PreparedStatement) con.prepareStatement(insertDebtQuery)) {
                insertStmt.setString(1, meterID);
                insertStmt.executeUpdate();
            }

            con.commit();
            System.out.println("Debt successfully inserted and water meter updated for MeterID: " + meterID);
        } catch (SQLException e) {
            System.err.println("Error executing queries: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    System.err.println("Error during rollback: " + rollbackEx.getMessage());
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }

        jTextField3.setText("");
        jTextField4.setText("");
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
            float amount = getBillAmount(ID);
            jTextField5.setText(String.valueOf(sID));
            jTextField7.setText(String.valueOf(amount));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField6KeyReleased
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jDialog3.pack();
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String ID = jTextField6.getText();
        processPayment(ID);
        jTextField5.setText("");
        jTextField5.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jDialog3.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed
    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed
    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                Integer ID = (Integer) jTable3.getValueAt(selectedRow, 0);
                disconnect(ID);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row.");
        }
    }//GEN-LAST:event_disconnectActionPerformed
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            AdminUI newAdminUI = new AdminUI();
            newAdminUI.setVisible(true);
        }); 
    }//GEN-LAST:event_jButton9ActionPerformed
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try (Connection con = DatabaseConnector.getConnection()) {
        // Retrieving input values
        String ID = ChargeSID.getText(); // SerialID
        String amount = CAmount.getText(); // ChargeAmount
        String desc = CDesc.getText();
        int mID = generateMeterID();
        double billingAmount = Double.parseDouble(amount); // Convert to numeric value
        Date dueDate = new Date(System.currentTimeMillis()); // You can set the due date as needed
        
        // Query to insert into 'charge' table
        String insertChargeQuery = """
            INSERT INTO charge (SerialID, ChargeAmount, DateIncurred, Type)
            VALUES (?, ?, CURDATE(), ?);
        """;

        try (PreparedStatement chargeStmt = (PreparedStatement) con.prepareStatement(insertChargeQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Set parameters for the charge
            chargeStmt.setString(1, ID);  // SerialID
            chargeStmt.setString(2, amount);  // ChargeAmount
            chargeStmt.setString(3, desc);  // Type ('Description' is used here)

            // Execute the charge insertion
            int affectedRows = chargeStmt.executeUpdate();

            // Check if insertion was successful and retrieve the generated ChargeID
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = chargeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int chargeID = generatedKeys.getInt(1); // Get generated ChargeID

                        // Now insert the corresponding Bill record
                        String insertBillQuery = """
                            INSERT INTO bill (SerialID, DebtID, ChargeID, BillingAmount, DueDate, isPaid)
                            VALUES (?, 0, ?, ?, ?, 0);
                        """;

                        try (PreparedStatement billStmt = (PreparedStatement) con.prepareStatement(insertBillQuery)) {
                            // Set parameters for the bill
                            billStmt.setString(1, ID);  // SerialID
                            
                            billStmt.setInt(2, chargeID);  // ChargeID from the charge table
                            billStmt.setDouble(3, billingAmount);  // BillingAmount
                            billStmt.setDate(4, dueDate);  // DueDate

                            // Execute the bill insertion
                            billStmt.executeUpdate();
                        }
                    }
                }
            }

            // Show success message
            ChargeSID.setText("");
            CAmount.setText("");
            CDesc.setText("");
            charges.dispose();
            JOptionPane.showMessageDialog(this, "Charge and Bill successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

        }
    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton5ActionPerformed
    private void generatebills2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebills2ActionPerformed
        charges.pack();
        charges.setVisible(true);
    }//GEN-LAST:event_generatebills2ActionPerformed
    private void reconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectActionPerformed
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                Integer ID = (Integer) jTable4.getValueAt(selectedRow, 0);
                if(existingArrears(ID)){
                    JOptionPane.showMessageDialog(null, "Please settle your Arrears");
                }else{
                    reconnect(ID);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row.");
        }
    }//GEN-LAST:event_reconnectActionPerformed
    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        String searchText = search.getText(); 

        if (searchText.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }//GEN-LAST:event_searchKeyReleased

    private void lateFeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lateFeeButtonActionPerformed
        try {
            addLateFees();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lateFeeButtonActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

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
    private javax.swing.JTextField CAmount;
    private javax.swing.JTextField CDesc;
    private javax.swing.JTextField ChargeSID;
    private javax.swing.JButton Submit;
    private javax.swing.JTextField addressField;
    private javax.swing.JTable allBillsTable;
    private javax.swing.JTable allLedgerTable;
    private javax.swing.JLabel bg;
    private javax.swing.JDialog charges;
    private javax.swing.JTable chargesTable;
    private javax.swing.JComboBox<String> consessionnaireBox;
    private javax.swing.JTextField contactNumField;
    private javax.swing.JButton disconnect;
    private javax.swing.JScrollPane disconnectionTable;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JButton generatebills;
    private javax.swing.JButton generatebills2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JButton lateFeeButton;
    private javax.swing.JTextField meterIDField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JButton reconnect;
    private javax.swing.JTextField search;
    private javax.swing.JTextField serialIDField;
    private javax.swing.JLabel txt;
    // End of variables declaration//GEN-END:variables
    private TableRowSorter<DefaultTableModel> sorter;
}
