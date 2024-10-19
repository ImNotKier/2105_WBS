
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {
    
    public GUI(){
        JFrame frame = new JFrame("Water Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Light blue background (R:173, G:216, B:230)
        panel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, 10px gaps

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Log In");
        JButton adminLoginButton = new JButton("Admin Login");

        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(adminLoginButton);

        JLabel titleLabel = new JLabel("Water Billing System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    

}
