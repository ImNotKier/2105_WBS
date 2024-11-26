package GUI;

import JDBC.DatabaseConnector;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class InvoicePopup extends JDialog {

    public InvoicePopup(int serialID) {
        setTitle("Invoice Details");
        setModal(true);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        fetchAndDisplayInvoice(serialID);
    }

    private void fetchAndDisplayInvoice(int serialID) {
        Map<String, String> consumerData = new HashMap<>();
        Map<String, String> billingData = new HashMap<>();
        Map<String, String> paymentData = new HashMap<>();
        Map<String, String> meterData = new HashMap<>();
        Map<String, String> concessionaireData = new HashMap<>();

        try (Connection con = DatabaseConnector.getConnection()) {
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

            String paymentQuery = "SELECT AmountPaid, PaymentDate FROM ledger WHERE SerialID = ?";
            try (PreparedStatement stmt = con.prepareStatement(paymentQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    paymentData.put("Amount Paid", String.valueOf(rs.getDouble("AmountPaid")));
                    paymentData.put("Payment Date", rs.getString("PaymentDate"));
                }
            }

            String meterQuery = "SELECT PresentReading, PreviousReading FROM watermeter WHERE MeterID = (SELECT MeterID FROM consumerinfo WHERE SerialID = ?)";
            try (PreparedStatement stmt = con.prepareStatement(meterQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    meterData.put("Previous Reading", String.valueOf(rs.getDouble("PreviousReading")));
                    meterData.put("Present Reading", String.valueOf(rs.getDouble("PresentReading")));
                }
            }

            String concessionaireQuery = "SELECT ConcessionaireName, PricePerCubicMeter FROM concessionaire WHERE ConcessionaireID = (SELECT ConcessionaireID FROM watermeter WHERE MeterID = (SELECT MeterID FROM consumerinfo WHERE SerialID = ?))";
            try (PreparedStatement stmt = con.prepareStatement(concessionaireQuery)) {
                stmt.setInt(1, serialID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    concessionaireData.put("Concessionaire", rs.getString("ConcessionaireName"));
                    concessionaireData.put("Price per Cubic Meter", String.valueOf(rs.getDouble("PricePerCubicMeter")));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> showDynamicInvoicePopup(consumerData, billingData, paymentData, meterData, concessionaireData));
    }

    private void showDynamicInvoicePopup(Map<String, String> consumerData, Map<String, String> billingData,
                                         Map<String, String> paymentData, Map<String, String> meterData,
                                         Map<String, String> concessionaireData) {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Invoice Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 150, 243));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel consumerInfoPanel = createInfoPanel("Consumer Information", consumerData);
        JPanel billingInfoPanel = createInfoPanel("Billing Information", billingData);
        JPanel paymentInfoPanel = createInfoPanel("Payment Information", paymentData);
        JPanel meterInfoPanel = createInfoPanel("Meter Information", meterData);
        JPanel concessionaireInfoPanel = createInfoPanel("Concessionaire Information", concessionaireData);

        double totalAmount = Double.parseDouble(billingData.get("Amount"));
        double totalPaid = paymentData.containsKey("Amount Paid") ? Double.parseDouble(paymentData.get("Amount Paid")) : 0;
        double balanceDue = totalAmount - totalPaid;

        JLabel totalLabel = new JLabel("Total Amount: $" + formatAmount(totalAmount), SwingConstants.RIGHT);
        JLabel paidLabel = new JLabel("Amount Paid: $" + formatAmount(totalPaid), SwingConstants.RIGHT);
        JLabel balanceLabel = new JLabel("Balance Due: $" + formatAmount(balanceDue), SwingConstants.RIGHT);

        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        paidLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balanceLabel.setForeground(balanceDue > 0 ? Color.RED : new Color(76, 175, 80));

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(consumerInfoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(billingInfoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(paymentInfoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(meterInfoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(concessionaireInfoPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(totalLabel);
        mainPanel.add(paidLabel);
        mainPanel.add(balanceLabel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
        setVisible(true);
    }

    private static JPanel createInfoPanel(String title, Map<String, String> data) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 1), title));

        for (Map.Entry<String, String> entry : data.entrySet()) {
            JLabel label = new JLabel(entry.getKey() + ": " + entry.getValue());
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            panel.add(label);
        }

        return panel;
    }

    private static String formatAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(amount);
    }

    public static void main(String[] args) {
        int sampleSerialID = 1;
        new InvoicePopup(sampleSerialID);
    }
}
