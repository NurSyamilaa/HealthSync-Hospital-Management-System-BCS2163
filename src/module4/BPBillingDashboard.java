package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPBillingDashboard extends JFrame {

    private JLabel lblTitle;

    private JButton btnMedicalBill;
    private JButton btnLogout;

    public BPBillingDashboard() {

        setTitle("Billing Officer Dashboard");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        ImageIcon appIcon =
                new ImageIcon(
                        getClass().getResource(
                                "/image/HealthSync.png"));

        setIconImage(appIcon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(
                new Color(214, 234, 248));

        // Title
        lblTitle = new JLabel(
                "BILLING OFFICER DASHBOARD");

        lblTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22));

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitle.setBounds(
                50,
                40,
                500,
                40);

        // Medical Bill Button
        btnMedicalBill =
                new JButton("Medical Bill");

        btnMedicalBill.setBounds(
                180,
                150,
                220,
                45);

        btnMedicalBill.setBackground(
                new Color(52, 152, 219));

        btnMedicalBill.setForeground(
                Color.WHITE);

        btnMedicalBill.addActionListener(e -> {

            new BPMedicalBillForm()
                    .setVisible(true);

            dispose();

        });

        // Logout Button
        btnLogout =
                new JButton("Logout");

        btnLogout.setBounds(
                180,
                240,
                220,
                45);

        btnLogout.setBackground(
                new Color(231, 76, 60));

        btnLogout.setForeground(
                Color.WHITE);

        btnLogout.addActionListener(e -> {

            new BPMainMenu()
                    .setVisible(true);

            dispose();

        });

        panel.add(lblTitle);
        panel.add(btnMedicalBill);
        panel.add(btnLogout);

        add(panel);
    }
}