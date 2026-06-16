package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPPharmacistDashboard extends JFrame {

    private JLabel lblTitle;

    private JButton btnMedicine;
    private JButton btnPrescription;
    private JButton btnRecommendation;
    private JButton btnLogout;

    public BPPharmacistDashboard() {

        setTitle("Pharmacist Dashboard");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Window Icon
        ImageIcon appIcon = new ImageIcon(
                getClass().getResource("/image/HealthSync.png"));

        setIconImage(appIcon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(214, 234, 248));

        // Title
        lblTitle = new JLabel("PHARMACIST DASHBOARD");

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

        // Medicine Inventory Button
        btnMedicine = new JButton(
                "Medicine Inventory");

        btnMedicine.setBounds(
                180,
                130,
                220,
                40);

        btnMedicine.setBackground(
                new Color(52,152,219));

        btnMedicine.setForeground(
                Color.WHITE);
        btnMedicine.addActionListener(e -> {
            new BPMedicineInventoryForm()
                    .setVisible(true);
            dispose();
        });

        // Prescription Button
        btnPrescription = new JButton(
                "Dispense Prescription");

        btnPrescription.setBounds(
                180,
                200,
                220,
                40);

        btnPrescription.setBackground(
                new Color(52,152,219));

        btnPrescription.setForeground(
                Color.WHITE);
        
        btnPrescription.addActionListener(e -> {

    new BPPrescriptionForm()
            .setVisible(true);

    dispose();

});
        
        // Medicine Recommendation Button
btnRecommendation = new JButton(
        "Medicine Recommendation");

btnRecommendation.setBounds(
        180,
        270,
        220,
        40);

btnRecommendation.setBackground(
        new Color(52,152,219));

btnRecommendation.setForeground(
        Color.WHITE);

btnRecommendation.addActionListener(e -> {

    new BPTopsisMedicineForm()
            .setVisible(true);

    dispose();

});

        // Logout Button
        btnLogout = new JButton(
                "Logout");

        btnLogout.setBounds(
                180,
                340,
                220,
                40);

        btnLogout.setBackground(
                new Color(231,76,60));

        btnLogout.setForeground(
                Color.WHITE);

        // Logout Action
        btnLogout.addActionListener(e -> {

            new BPMainMenu().setVisible(true);

            dispose();

        });

        panel.add(lblTitle);
        panel.add(btnMedicine);
        panel.add(btnPrescription);
        panel.add(btnRecommendation);
        panel.add(btnLogout);

        add(panel);
    }
}