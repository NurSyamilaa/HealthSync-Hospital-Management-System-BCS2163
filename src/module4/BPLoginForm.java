package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPLoginForm extends JFrame {

    private JLabel lblTitle;
    private JLabel lblUsername;
    private JLabel lblPassword;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnBack;

    private String role;

    public BPLoginForm(String role) {

        this.role = role;

        setTitle(role + " Login");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        ImageIcon appIcon = new ImageIcon(
                getClass().getResource("/image/HealthSync.png"));

        setIconImage(appIcon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(214, 234, 248));

        // Title
        lblTitle = new JLabel(role + " Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 30, 300, 30);

        // Staff ID
        lblUsername = new JLabel("Staff ID:");
        lblUsername.setBounds(80, 100, 100, 25);

        txtUsername = new JTextField();
        txtUsername.setBounds(180, 100, 180, 25);

        // Password
        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(80, 150, 100, 25);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 150, 180, 25);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 220, 120, 35);
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);

        btnLogin.addActionListener(e -> {

            String staffID =
                    txtUsername.getText().trim();

            String password =
                    String.valueOf(
                            txtPassword.getPassword());

            // Billing Officer Login
            if(role.equals("Billing Officer")) {

                if(staffID.equals("B001")
                        && password.equals("1234")) {

                    BPUser user =
                            BPUserFactory.createUser(role);

                    user.displayRole();

                    new BPBillingDashboard()
                            .setVisible(true);

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid Billing Officer Login!\n\n"
                            + "Staff ID : B001\n"
                            + "Password : 1234");

                }

            }

            // Pharmacist Login
            else {

                if(staffID.equals("P001")
                        && password.equals("1234")) {

                    BPUser user =
                            BPUserFactory.createUser(role);

                    user.displayRole();

                    new BPPharmacistDashboard()
                            .setVisible(true);

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid Pharmacist Login!\n\n"
                            + "Staff ID : P001\n"
                            + "Password : 1234");

                }

            }

        });

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(260, 220, 120, 35);
        btnBack.setBackground(new Color(231, 76, 60));
        btnBack.setForeground(Color.WHITE);

        btnBack.addActionListener(e -> {

            new BPMainMenu()
                    .setVisible(true);

            dispose();

        });

        panel.add(lblTitle);
        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnBack);

        add(panel);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new BPLoginForm("Billing Officer")
                    .setVisible(true);

        });

    }
}