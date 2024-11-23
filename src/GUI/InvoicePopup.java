package GUI;

import JDBC.DatabaseConnector;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InvoicePopup extends JFrame {

    public InvoicePopup(String consumerID) {
        setTitle("Invoice Details");
        setSize(600, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));

        JLabel labelConsumerName = new JLabel("Consumer Name: ");
        JLabel labelSerialID = new JLabel("Serial ID: ");
        JLabel labelMeterID = new JLabel("Meter ID: ");
        JLabel labelConcessionaire = new JLabel("Concessionaire: ");
        JLabel labelRate = new JLabel("Rate per Cubic Meter: ");
        JLabel labelConsumption = new JLabel("Consumption (Cubic Meters): ");
        JLabel labelTotalCharges = new JLabel("Total Charges (Debit): ");
        JLabel labelTotalPayments = new JLabel("Total Payments (Credit): ");
        JLabel labelArrears = new JLabel("Outstanding Balance (Arrears): ");
        JLabel labelDueDate = new JLabel("Due Date: ");

        JTextField fieldConsumerName = new JTextField();
        JTextField fieldSerialID = new JTextField();
        JTextField fieldMeterID = new JTextField();
        JTextField fieldConcessionaire = new JTextField();
        JTextField fieldRate = new JTextField();
        JTextField fieldConsumption = new JTextField();
        JTextField fieldTotalCharges = new JTextField();
        JTextField fieldTotalPayments = new JTextField();
        JTextField fieldArrears = new JTextField();
        JTextField fieldDueDate = new JTextField();

        fieldConsumerName.setEditable(false);
        fieldSerialID.setEditable(false);
        fieldMeterID.setEditable(false);
        fieldConcessionaire.setEditable(false);
        fieldRate.setEditable(false);
        fieldConsumption.setEditable(false);
        fieldTotalCharges.setEditable(false);
        fieldTotalPayments.setEditable(false);
        fieldArrears.setEditable(false);
        fieldDueDate.setEditable(false);

        panel.add(labelConsumerName);
        panel.add(fieldConsumerName);
        panel.add(labelSerialID);
        panel.add(fieldSerialID);
        panel.add(labelMeterID);
        panel.add(fieldMeterID);
        panel.add(labelConcessionaire);
        panel.add(fieldConcessionaire);
        panel.add(labelRate);
        panel.add(fieldRate);
        panel.add(labelConsumption);
        panel.add(fieldConsumption);
        panel.add(labelTotalCharges);
        panel.add(fieldTotalCharges);
        panel.add(labelTotalPayments);
        panel.add(fieldTotalPayments);
        panel.add(labelArrears);
        panel.add(fieldArrears);
        panel.add(labelDueDate);
        panel.add(fieldDueDate);

        add(panel, BorderLayout.CENTER);

        fetchAndDisplayInvoiceData(consumerID, fieldConsumerName, fieldSerialID, fieldMeterID, fieldConcessionaire, fieldRate, fieldConsumption, fieldTotalCharges, fieldTotalPayments, fieldArrears, fieldDueDate);
    }

    private void fetchAndDisplayInvoiceData(String consumerID, JTextField fieldConsumerName, JTextField fieldSerialID, JTextField fieldMeterID, JTextField fieldConcessionaire, JTextField fieldRate, JTextField fieldConsumption, JTextField fieldTotalCharges, JTextField fieldTotalPayments, JTextField fieldArrears, JTextField fieldDueDate) {
        try (Connection con = DatabaseConnector.getConnection()) {
            String consumerQuery = "SELECT c.FirstName, c.LastName, c.SerialID, w.MeterID, co.ConsessionaireName, co.PricePerCubicMeter, " +
                                   "(w.PresentReading - w.PreviousReading) AS Consumption " +
                                   "FROM consumerinfo c " +
                                   "JOIN watermeter w ON c.SerialID = w.SerialID " +
                                   "JOIN concessionaire co ON w.ConsessionaireID = co.ConsessionaireID " +
                                   "WHERE c.SerialID = ?";
            PreparedStatement ps1 = con.prepareStatement(consumerQuery);
            ps1.setString(1, consumerID);

            try (ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next()) {
                    String fullName = rs1.getString("FirstName") + " " + rs1.getString("LastName");
                    fieldConsumerName.setText(fullName);
                    fieldSerialID.setText(rs1.getString("SerialID"));
                    fieldMeterID.setText(rs1.getString("MeterID"));
                    fieldConcessionaire.setText(rs1.getString("ConsessionaireName"));
                    fieldRate.setText(String.format("%.2f", rs1.getDouble("PricePerCubicMeter")));
                    fieldConsumption.setText(String.format("%.2f", rs1.getDouble("Consumption")));
                }
            }

            String chargesQuery = "SELECT SUM(Debit) AS TotalCharges FROM ledger WHERE SerialID = ?";
            PreparedStatement ps2 = con.prepareStatement(chargesQuery);
            ps2.setString(1, consumerID);

            try (ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) {
                    double totalCharges = rs2.getDouble("TotalCharges");
                    fieldTotalCharges.setText(String.format("%.2f", totalCharges));

                    String paymentsQuery = "SELECT SUM(Credit) AS TotalPayments FROM ledger WHERE SerialID = ?";
                    PreparedStatement ps3 = con.prepareStatement(paymentsQuery);
                    ps3.setString(1, consumerID);

                    try (ResultSet rs3 = ps3.executeQuery()) {
                        if (rs3.next()) {
                            double totalPayments = rs3.getDouble("TotalPayments");
                            fieldTotalPayments.setText(String.format("%.2f", totalPayments));

                            double arrears = totalCharges - totalPayments;
                            fieldArrears.setText(String.format("%.2f", arrears));

                            String dueDateQuery = "SELECT DueDate FROM debt WHERE SerialID = ? ORDER BY DueDate DESC LIMIT 1";
                            PreparedStatement ps4 = con.prepareStatement(dueDateQuery);
                            ps4.setString(1, consumerID);

                            try (ResultSet rs4 = ps4.executeQuery()) {
                                if (rs4.next()) {
                                    fieldDueDate.setText(rs4.getDate("DueDate").toString());
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading invoice: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
