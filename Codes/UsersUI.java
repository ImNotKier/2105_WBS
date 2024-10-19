import javax.swing.JButton;

class UsersUI extends GUI {

    public UsersUI() {
        super("Water Billing System User");

        JButton waterMeterButton = createButton("Water Meter", null);
        JButton debtsButton = createButton("Debts", null);
        JButton chargesButton = createButton("Charges", null);
        JButton billingButton = createButton("Billing", null);

        panel.add(waterMeterButton);
        panel.add(debtsButton);
        panel.add(chargesButton);
        panel.add(billingButton);

        showUI();
    }
}
