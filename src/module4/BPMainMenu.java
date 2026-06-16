package module4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class BPMainMenu extends JFrame {

    private JLabel lblTitle;
    private JLabel lblWelcome;
    private JLabel lblLogo;

    private JButton btnBillingOfficer;
    private JButton btnPharmacist;
    private JButton btnExit;

    public BPMainMenu() {

        setTitle("HealthSync - Billing & Pharmacy System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Window Icon
        ImageIcon appIcon =
                new ImageIcon("src/image/HealthSync.png");
        setIconImage(appIcon.getImage());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(214, 234, 248));
        panel.setLayout(null);

      // Logo
lblLogo = new JLabel();
lblLogo.setBounds(245, 5, 200, 200);

ImageIcon logo = new ImageIcon(
        getClass().getResource("/image/HealthSync.png"));

Image img = logo.getImage().getScaledInstance(
        200,
        200,
        Image.SCALE_SMOOTH);

lblLogo.setIcon(new ImageIcon(img));


// Title
lblTitle = new JLabel(
        "HEALTHSYNC BILLING & PHARMACY SYSTEM");

lblTitle.setHorizontalAlignment(
        SwingConstants.CENTER);

lblTitle.setFont(
        new Font("Arial",
                Font.BOLD,
                22));

lblTitle.setForeground(
        new Color(46, 134, 193));

lblTitle.setBounds(
        50,
        190,
        600,
        40);


// Welcome Label
lblWelcome = new JLabel(
        "Welcome to the System");

lblWelcome.setHorizontalAlignment(
        SwingConstants.CENTER);

lblWelcome.setFont(
        new Font("Arial",
                Font.PLAIN,
                16));

lblWelcome.setBounds(
        220,
        235,
        260,
        30);


// Billing Officer Button
btnBillingOfficer =
        new JButton("Billing Officer Login");

btnBillingOfficer.setBounds(
        230,
        290,
        220,
        40);

btnBillingOfficer.setBackground(
        new Color(52, 152, 219));

btnBillingOfficer.setForeground(
        Color.WHITE);

btnBillingOfficer.addActionListener(e -> {

    new BPLoginForm("Billing Officer")
            .setVisible(true);
    dispose();

});


// Pharmacist Button
btnPharmacist =
        new JButton("Pharmacist Login");

btnPharmacist.setBounds(
        230,
        350,
        220,
        40);

btnPharmacist.setBackground(
        new Color(52, 152, 219));

btnPharmacist.setForeground(
        Color.WHITE);

btnPharmacist.addActionListener(e -> {

    new BPLoginForm("Pharmacist")
            .setVisible(true);

    dispose();

});


// Exit Button
btnExit =
        new JButton("Exit");

btnExit.setBounds(
        230,
        410,
        220,
        40);

btnExit.setBackground(
        new Color(231, 76, 60));

btnExit.setForeground(
        Color.WHITE);

        // Exit Button Action
        btnExit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(
                    ActionEvent e) {

                System.exit(0);

            }
        });

        // Add Components
        panel.add(lblLogo);
        panel.add(lblTitle);
        panel.add(lblWelcome);
        panel.add(btnBillingOfficer);
        panel.add(btnPharmacist);
        panel.add(btnExit);

        add(panel);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    new BPMainMenu()
                            .setVisible(true);

                });

    }
}