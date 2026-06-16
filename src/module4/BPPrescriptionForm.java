package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPPrescriptionForm extends JFrame {

    private JLabel lblTitle;
    private JLabel lblPrescriptionID;
    private JLabel lblPatientName;
    private JLabel lblMedicineName;
    private JLabel lblDosage;

    private JTextField txtPrescriptionID;
    private JTextField txtPatientName;
    private JTextField txtMedicineName;
    private JTextField txtDosage;

    private JButton btnAdd;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnBack;

    public BPPrescriptionForm() {

        setTitle("Prescription Form");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        ImageIcon appIcon = new ImageIcon(
                getClass().getResource("/image/HealthSync.png"));

        setIconImage(appIcon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(214,234,248));

        lblTitle = new JLabel("PRESCRIPTION FORM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 30, 400, 40);

        lblPrescriptionID = new JLabel("Prescription ID:");
        lblPrescriptionID.setBounds(100, 100, 120, 25);

        txtPrescriptionID = new JTextField();
        txtPrescriptionID.setBounds(250, 100, 200, 25);

        lblPatientName = new JLabel("Patient Name:");
        lblPatientName.setBounds(100, 150, 120, 25);

        txtPatientName = new JTextField();
        txtPatientName.setBounds(250, 150, 200, 25);

        lblMedicineName = new JLabel("Medicine Name:");
        lblMedicineName.setBounds(100, 200, 120, 25);

        txtMedicineName = new JTextField();
        txtMedicineName.setBounds(250, 200, 200, 25);

        lblDosage = new JLabel("Dosage:");
        lblDosage.setBounds(100, 250, 120, 25);

        txtDosage = new JTextField();
        txtDosage.setBounds(250, 250, 200, 25);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(70, 340, 100, 40);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(190, 340, 100, 40);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(310, 340, 100, 40);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(430, 340, 100, 40);

        btnBack = new JButton("Back");
        btnBack.setBounds(250, 400, 120, 40);

        btnBack.addActionListener(e -> {

            new BPPharmacistDashboard()
                    .setVisible(true);

            dispose();

        });
        
        btnAdd.addActionListener(e -> {

    try {

        BPPrescription prescription =
                new BPPrescription(
                        txtPrescriptionID.getText(),
                        txtPatientName.getText(),
                        txtMedicineName.getText(),
                        txtDosage.getText());

        java.sql.Connection conn =
                BPDatabaseConnection.getConnection();

        String sql =
                "INSERT INTO prescription VALUES(?,?,?,?)";

        java.sql.PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1,
                prescription.getPrescriptionID());

        pst.setString(2,
                prescription.getPatientName());

        pst.setString(3,
                prescription.getMedicineName());

        pst.setString(4,
                prescription.getDosage());

        pst.executeUpdate();

        JOptionPane.showMessageDialog(
                null,
                "Prescription Added Successfully!");

        conn.close();

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});
        
        btnSearch.addActionListener(e -> {

    try {

        java.sql.Connection conn =
                BPDatabaseConnection.getConnection();

        String sql =
                "SELECT * FROM prescription WHERE prescriptionID=?";

        java.sql.PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(
                1,
                txtPrescriptionID.getText());

        java.sql.ResultSet rs =
                pst.executeQuery();

        if(rs.next()) {

            txtPatientName.setText(
                    rs.getString("patientName"));

            txtMedicineName.setText(
                    rs.getString("medicineName"));

            txtDosage.setText(
                    rs.getString("dosage"));

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Prescription Not Found!");

        }

        conn.close();

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});
        
        btnUpdate.addActionListener(e -> {

    try {

        java.sql.Connection conn =
                BPDatabaseConnection.getConnection();

        String sql =
                "UPDATE prescription SET patientName=?, medicineName=?, dosage=? WHERE prescriptionID=?";

        java.sql.PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1, txtPatientName.getText());
        pst.setString(2, txtMedicineName.getText());
        pst.setString(3, txtDosage.getText());
        pst.setString(4, txtPrescriptionID.getText());

        int rows = pst.executeUpdate();

        if(rows > 0){

            JOptionPane.showMessageDialog(
                    null,
                    "Prescription Updated Successfully!");

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Prescription Not Found!");

        }

        conn.close();

    } catch(Exception ex){

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});
        
        btnDelete.addActionListener(e -> {

    try {

        int confirm =
                JOptionPane.showConfirmDialog(
                        null,
                        "Delete this prescription?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){

            java.sql.Connection conn =
                    BPDatabaseConnection.getConnection();

            String sql =
                    "DELETE FROM prescription WHERE prescriptionID=?";

            java.sql.PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setString(
                    1,
                    txtPrescriptionID.getText());

            int rows =
                    pst.executeUpdate();

            if(rows > 0){

                JOptionPane.showMessageDialog(
                        null,
                        "Prescription Deleted Successfully!");

                txtPrescriptionID.setText("");
                txtPatientName.setText("");
                txtMedicineName.setText("");
                txtDosage.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Prescription Not Found!");

            }

            conn.close();
        }

    } catch(Exception ex){

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});

        panel.add(lblTitle);

        panel.add(lblPrescriptionID);
        panel.add(txtPrescriptionID);

        panel.add(lblPatientName);
        panel.add(txtPatientName);

        panel.add(lblMedicineName);
        panel.add(txtMedicineName);

        panel.add(lblDosage);
        panel.add(txtDosage);

        panel.add(btnAdd);
        panel.add(btnSearch);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnBack);

        add(panel);
    }
}