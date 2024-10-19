import java.awt.*;
import javax.swing.*;

class GUI {
    JFrame frame;
    JPanel panel;


    public GUI(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 20, 20));
    }

    public JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    public void showUI() {
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
