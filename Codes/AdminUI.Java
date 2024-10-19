class AdminUI extends GUI {

    public AdminUI() {
        super("Water Billing System Admin");

        JButton consumersButton = createButton("Consumers", new Color(173, 216, 230));
        JButton duesButton = createButton("Dues", new Color(173, 216, 230));
        JButton disconnectButton = createButton("For Disconnection", new Color(173, 216, 230));

        panel.add(consumersButton);
        panel.add(duesButton);
        panel.add(disconnectButton);

        showUI();
    }
}
