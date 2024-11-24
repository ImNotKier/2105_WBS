package GUI;

import JDBC.DatabaseConnector;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class InvoicePopup extends JDialog {

    // Constructor to initialize the InvoicePopup with serialID
    public InvoicePopup(int serialID) {
        // Fetch data and display the invoice
        fetchAndDisplayInvoice(serialID);
    }

    // Method to fetch and display the invoice data
    private void fetchAndDisplayInvoice(int serialID) {
        Map<String, String> consumerData = new HashMap<>();
        Map<String, String> billingData = new HashMap<>();
        Map<String, String> paymentData = new HashMap<>();
        Map<String, String> meterData = new HashMap<>();
        Map<String, String> concessionaireData = new HashMap<>();

        // Fetch data from database based on the serialID
        try (Connection con = DatabaseConnector.getConnection()) {
            // Consumer Info
            String consumerQuery = "SELECT FirstName, LastName, Address, Email FROM consumerinfo WHERE SerialID = ?";
            try (PreparedStatement stmt = con.prepareStatement(consumerQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    consumerData.put("Name", rs.getString("FirstName") + " " + rs.getString("LastName"));
                    consumerData.put("Address", rs.getString("Address"));
                    consumerData.put("Email", rs.getString("Email"));
                }
            }

            // Billing Info
            String billingQuery = "SELECT BillingID, BillingAmount, DueDate FROM bill WHERE SerialID = ?";
            try (PreparedStatement stmt = con.prepareStatement(billingQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    billingData.put("Billing ID", String.valueOf(rs.getInt("BillingID")));
                    billingData.put("Amount", String.valueOf(rs.getDouble("BillingAmount")));
                    billingData.put("Due Date", rs.getString("DueDate"));
                }
            }

            // Payment Info
            String paymentQuery = "SELECT AmountPaid, PaymentDate FROM ledger WHERE SerialID = ?";
            try (PreparedStatement stmt = con.prepareStatement(paymentQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    paymentData.put("Amount Paid", String.valueOf(rs.getDouble("AmountPaid")));
                    paymentData.put("Payment Date", rs.getString("PaymentDate"));
                }
            }

            // Meter Info
            String meterQuery = "SELECT PresentReading, PreviousReading FROM watermeter WHERE MeterID = (SELECT MeterID FROM consumerinfo WHERE SerialID = ?)";
            try (PreparedStatement stmt = con.prepareStatement(meterQuery)) {
                stmt.setInt(1, serialID);  // assuming MeterID is linked with SerialID
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    meterData.put("Previous Reading", String.valueOf(rs.getDouble("PreviousReading")));
                    meterData.put("Present Reading", String.valueOf(rs.getDouble("PresentReading")));
                }
            }

            // Concessionaire Info
            String concessionaireQuery = "SELECT ConcessionaireName, PricePerCubicMeter FROM concessionaire WHERE ConcessionaireID = (SELECT ConcessionaireID FROM watermeter WHERE MeterID = (SELECT MeterID FROM consumerinfo WHERE SerialID = ?))";
            try (PreparedStatement stmt = con.prepareStatement(concessionaireQuery)) {
                stmt.setInt(1, serialID);  // Assuming ConcessionaireID is related to the MeterID
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    concessionaireData.put("Concessionaire", rs.getString("ConcessionaireName"));
                    concessionaireData.put("Price per Cubic Meter", String.valueOf(rs.getDouble("PricePerCubicMeter")));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Now, display the dynamic invoice popup
        SwingUtilities.invokeLater(() -> showDynamicInvoicePopup(consumerData, billingData, paymentData, meterData, concessionaireData));
    }

    // Method to show the dynamic invoice
    private void showDynamicInvoicePopup(Map<String, String> consumerData, Map<String, String> billingData,
                                     Map<String, String> paymentData, Map<String, String> meterData,
                                     Map<String, String> concessionaireData) {

        // Create the frame for the invoice popup
        JDialog invoiceDialog = new JDialog();
        invoiceDialog.setTitle("Invoice");
        invoiceDialog.setSize(450, 650);  // Increased height from 500 to 650
        invoiceDialog.setLocationRelativeTo(null);
        invoiceDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Invoice Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create dynamic panels for each section
        JPanel consumerInfoPanel = createInfoPanel("Consumer Information", consumerData);
        JPanel billingInfoPanel = createInfoPanel("Billing Information", billingData);
        JPanel paymentInfoPanel = createInfoPanel("Payment Information", paymentData);
        JPanel meterInfoPanel = createInfoPanel("Meter Information", meterData);
        JPanel concessionaireInfoPanel = createInfoPanel("Concessionaire Information", concessionaireData);

        // Total Section (displaying total paid dynamically)
        double totalAmount = Double.parseDouble(billingData.get("Amount"));
        double totalPaid = paymentData.containsKey("Amount Paid") ? Double.parseDouble(paymentData.get("Amount Paid")) : 0;
        double balanceDue = totalAmount - totalPaid;

        JLabel totalLabel = new JLabel("Total Amount: $" + formatAmount(totalAmount), SwingConstants.RIGHT);
        JLabel paidLabel = new JLabel("Amount Paid: $" + formatAmount(totalPaid), SwingConstants.RIGHT);
        JLabel balanceLabel = new JLabel("Balance Due: $" + formatAmount(balanceDue), SwingConstants.RIGHT);

        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        paidLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        totalLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        paidLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        balanceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Adding components to the panel with more space
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(consumerInfoPanel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(billingInfoPanel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(paymentInfoPanel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(meterInfoPanel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(concessionaireInfoPanel);
        panel.add(Box.createVerticalStrut(20));  // Increased vertical space
        panel.add(totalLabel);
        panel.add(paidLabel);
        panel.add(balanceLabel);

        // Adding panel to the dialog and displaying it
        invoiceDialog.add(panel);
        invoiceDialog.setVisible(true);
    }

        // Helper method to create information panels dynamically
        private static JPanel createInfoPanel(String title, Map<String, String> data) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createTitledBorder(title));

            // Dynamically create labels based on the data map
            for (Map.Entry<String, String> entry : data.entrySet()) {
                JLabel label = new JLabel(entry.getKey() + ": " + entry.getValue());
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(label);
            }

            return panel;
        }

    // Helper method to format amounts to two decimal places
    private static String formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(amount);
    }

    // For testing purpose (main method to call it with serialID)
    public static void main(String[] args) {
        int sampleSerialID = 1; // Sample SerialID
        new InvoicePopup(sampleSerialID); // Create and show the invoice popup
    }
}
