package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPTopsisMedicineForm extends JFrame {

    private JLabel lblTitle;
    private JLabel lblQuantityWeight;
    private JLabel lblCostWeight;
    private JLabel lblCriteria;

    private JTextField txtQuantityWeight;
    private JTextField txtCostWeight;

    private JTextArea txtResult;

    private JButton btnCalculate;
    private JButton btnBack;

    public BPTopsisMedicineForm() {

        setTitle("Medicine Recommendation");
        setSize(700, 550);
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
        lblTitle = new JLabel(
                "MEDICINE RECOMMENDATION SYSTEM");

        lblTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22));

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitle.setBounds(
                80,
                20,
                520,
                40);

        // Criteria Label
        lblCriteria = new JLabel(
                "<html><b>Selected Criteria:</b><br>"
                + "Quantity (Benefit)<br>"
                + "Cost (Cost)</html>");

        lblCriteria.setBounds(
                80,
                80,
                250,
                80);

        // Quantity Weight
        lblQuantityWeight =
                new JLabel(
                        "Quantity Weight (%):");

        lblQuantityWeight.setBounds(
                80,
                180,
                150,
                25);

        txtQuantityWeight =
                new JTextField();

        txtQuantityWeight.setBounds(
                250,
                180,
                120,
                25);

        // Cost Weight
        lblCostWeight =
                new JLabel(
                        "Cost Weight (%):");

        lblCostWeight.setBounds(
                80,
                230,
                150,
                25);

        txtCostWeight =
                new JTextField();

        txtCostWeight.setBounds(
                250,
                230,
                120,
                25);

        // Calculate Button
        btnCalculate =
                new JButton(
                        "Calculate Recommendation");
        
        btnCalculate.addActionListener(e -> {
            
            try {
                
                double quantityWeight =
                        Double.parseDouble(
                                txtQuantityWeight.getText()) / 100.0;

                double costWeight =
                        Double.parseDouble(
                                txtCostWeight.getText()) / 100.0;
                
                java.sql.Connection conn =
                        BPDatabaseConnection.getConnection();
                
                String sql =
                        "SELECT * FROM medicine";

                java.sql.PreparedStatement pst =
                        conn.prepareStatement(sql);

                java.sql.ResultSet rs =
                pst.executeQuery();

        String bestMedicine = "";
        double bestScore = -1;

        String result =
                "MEDICINE RECOMMENDATION\n\n";

        while(rs.next()) {

            String medicineName =
                    rs.getString("medicineName");

            double quantity =
                    rs.getDouble("quantity");

            double cost =
                    rs.getDouble("cost");

            // Quantity = Benefit
            // Cost = Cost

            double score =
                    (quantity * quantityWeight)
                    + ((1.0 / cost) * costWeight);

            result +=
                    medicineName
                    + " -> Score: "
                    + String.format("%.4f", score)
                    + "\n";

            if(score > bestScore) {

                bestScore = score;
                bestMedicine = medicineName;

            }

        }

        result +=
                "\nRecommended Medicine:\n"
                + bestMedicine;

        txtResult.setText(result);

        conn.close();

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});

        btnCalculate.setBounds(
                180,
                290,
                250,
                40);

        // Result Area
        txtResult =
                new JTextArea();

        txtResult.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(txtResult);

        scrollPane.setBounds(
                80,
                360,
                520,
                100);

        // Back Button
        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                280,
                470,
                100,
                30);

        btnBack.addActionListener(e -> {

            new BPPharmacistDashboard()
                    .setVisible(true);

            dispose();

        });

        panel.add(lblTitle);
        panel.add(lblCriteria);

        panel.add(lblQuantityWeight);
        panel.add(txtQuantityWeight);

        panel.add(lblCostWeight);
        panel.add(txtCostWeight);

        panel.add(btnCalculate);

        panel.add(scrollPane);

        panel.add(btnBack);

        add(panel);
    }
}