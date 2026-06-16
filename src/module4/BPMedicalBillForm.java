package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPMedicalBillForm extends JFrame {

    private JLabel lblTitle;
    private JLabel lblBillID;
    private JLabel lblPatientName;
    private JLabel lblAmount;

    private JTextField txtBillID;
    private JTextField txtPatientName;
    private JTextField txtAmount;

    private JButton btnAdd;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnBack;

    public BPMedicalBillForm() {

        setTitle("Medical Bill Form");
        setSize(650, 500);
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
                "MEDICAL BILL FORM");

        lblTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22));

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitle.setBounds(
                100,
                30,
                400,
                40);

        // Bill ID
        lblBillID = new JLabel(
                "Bill ID:");

        lblBillID.setBounds(
                100,
                120,
                120,
                25);

        txtBillID = new JTextField();

        txtBillID.setBounds(
                250,
                120,
                200,
                25);

        // Patient Name
        lblPatientName = new JLabel(
                "Patient Name:");

        lblPatientName.setBounds(
                100,
                180,
                120,
                25);

        txtPatientName = new JTextField();

        txtPatientName.setBounds(
                250,
                180,
                200,
                25);

        // Amount
        lblAmount = new JLabel(
                "Amount (RM):");

        lblAmount.setBounds(
                100,
                240,
                120,
                25);

        txtAmount = new JTextField();

        txtAmount.setBounds(
                250,
                240,
                200,
                25);

        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setBounds(
                60,
                330,
                100,
                40);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(
                180,
                330,
                100,
                40);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(
                300,
                330,
                100,
                40);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(
                420,
                330,
                100,
                40);

        btnBack = new JButton("Back");
        btnBack.setBounds(
                250,
                400,
                120,
                40);

        // Back
        btnBack.addActionListener(e -> {

            new BPBillingDashboard()
                    .setVisible(true);

            dispose();

        });

        // ADD
        btnAdd.addActionListener(e -> {

            try {

                BPMedicalBill bill =
                        new BPMedicalBill(
                                txtBillID.getText(),
                                txtPatientName.getText(),
                                Double.parseDouble(
                                        txtAmount.getText()));

                java.sql.Connection conn =
                        BPDatabaseConnection.getConnection();

                String sql =
                        "INSERT INTO medicalbill VALUES(?,?,?)";

                java.sql.PreparedStatement pst =
                        conn.prepareStatement(sql);

                pst.setString(
                        1,
                        bill.getBillID());

                pst.setString(
                        2,
                        bill.getPatientName());

                pst.setDouble(
                        3,
                        bill.getAmount());

                pst.executeUpdate();
                BPReceiptFile.saveReceipt(
                        "Bill ID: " + txtBillID.getText()
                                + "\nPatient Name: " + txtPatientName.getText()
                                + "\nAmount: RM " + txtAmount.getText());
                JOptionPane.showMessageDialog(
                        null,
                        "Medical Bill Added Successfully!");

                conn.close();

            } catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage());

            }

        });

        // SEARCH
        btnSearch.addActionListener(e -> {

            try {

                java.sql.Connection conn =
                        BPDatabaseConnection.getConnection();

                String sql =
                        "SELECT * FROM medicalbill WHERE billID=?";

                java.sql.PreparedStatement pst =
                        conn.prepareStatement(sql);

                pst.setString(
                        1,
                        txtBillID.getText());

                java.sql.ResultSet rs =
                        pst.executeQuery();

                if(rs.next()) {

                    txtPatientName.setText(
                            rs.getString(
                                    "patientName"));

                    txtAmount.setText(
                            String.valueOf(
                                    rs.getDouble(
                                            "amount")));

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Medical Bill Not Found!");

                }

                conn.close();

            } catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage());

            }

        });

        // UPDATE
        btnUpdate.addActionListener(e -> {

            try {

                java.sql.Connection conn =
                        BPDatabaseConnection.getConnection();

                String sql =
                        "UPDATE medicalbill SET patientName=?, amount=? WHERE billID=?";

                java.sql.PreparedStatement pst =
                        conn.prepareStatement(sql);

                pst.setString(
                        1,
                        txtPatientName.getText());

                pst.setDouble(
                        2,
                        Double.parseDouble(
                                txtAmount.getText()));

                pst.setString(
                        3,
                        txtBillID.getText());

                int rows =
                        pst.executeUpdate();

                if(rows > 0){

                    JOptionPane.showMessageDialog(
                            null,
                            "Medical Bill Updated Successfully!");

                }else{

                    JOptionPane.showMessageDialog(
                            null,
                            "Medical Bill Not Found!");

                }

                conn.close();

            } catch(Exception ex){

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage());

            }

        });

        // DELETE
        btnDelete.addActionListener(e -> {

            try {

                int confirm =
                        JOptionPane.showConfirmDialog(
                                null,
                                "Delete this medical bill?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION);

                if(confirm ==
                        JOptionPane.YES_OPTION){

                    java.sql.Connection conn =
                            BPDatabaseConnection.getConnection();

                    String sql =
                            "DELETE FROM medicalbill WHERE billID=?";

                    java.sql.PreparedStatement pst =
                            conn.prepareStatement(sql);

                    pst.setString(
                            1,
                            txtBillID.getText());

                    int rows =
                            pst.executeUpdate();

                    if(rows > 0){

                        JOptionPane.showMessageDialog(
                                null,
                                "Medical Bill Deleted Successfully!");

                        txtBillID.setText("");
                        txtPatientName.setText("");
                        txtAmount.setText("");

                    }else{

                        JOptionPane.showMessageDialog(
                                null,
                                "Medical Bill Not Found!");

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

        panel.add(lblBillID);
        panel.add(txtBillID);

        panel.add(lblPatientName);
        panel.add(txtPatientName);

        panel.add(lblAmount);
        panel.add(txtAmount);

        panel.add(btnAdd);
        panel.add(btnSearch);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnBack);

        add(panel);
    }
}