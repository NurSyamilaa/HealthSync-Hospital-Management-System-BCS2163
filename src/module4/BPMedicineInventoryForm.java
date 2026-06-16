package module4;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class BPMedicineInventoryForm extends JFrame {

    private JLabel lblTitle;
    private JLabel lblMedicineID;
    private JLabel lblMedicineName;
    private JLabel lblQuantity;
    private JLabel lblCost;

    private JTextField txtMedicineID;
    private JTextField txtMedicineName;
    private JTextField txtQuantity;
    private JTextField txtCost;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnBack;
    private BPDataManager dataManager =
        new BPDataManager();

    public BPMedicineInventoryForm() {

        setTitle("Medicine Inventory");
        setSize(600, 500);
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
        lblTitle = new JLabel("MEDICINE INVENTORY");

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

        // Medicine ID
        lblMedicineID = new JLabel("Medicine ID:");
        lblMedicineID.setBounds(100, 100, 120, 25);

        txtMedicineID = new JTextField();
        txtMedicineID.setBounds(220, 100, 180, 25);

        // Medicine Name
        lblMedicineName = new JLabel("Medicine Name:");
        lblMedicineName.setBounds(100, 150, 120, 25);

        txtMedicineName = new JTextField();
        txtMedicineName.setBounds(220, 150, 180, 25);

        // Quantity
        lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(100, 200, 120, 25);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(220, 200, 180, 25);
        
        //Cost
        lblCost = new JLabel("Cost (RM):");
        lblCost.setBounds(100, 250, 120, 25);

        txtCost = new JTextField();
        txtCost.setBounds(220, 250, 180, 25);

        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setBounds(80, 350, 100, 40);
        btnAdd.addActionListener(e -> {
            try {
                String medicineID =
                        txtMedicineID.getText();
                String medicineName =
                        txtMedicineName.getText();
                int quantity =
                        Integer.parseInt(
                                txtQuantity.getText());
                
                double cost =
                        Double.parseDouble(
                                txtCost.getText());
                
                BPMedicine medicine =
                        new BPMedicine(
                                medicineID,
                                medicineName,
                                quantity,
                                cost);
                
                dataManager.addMedicine(
                        medicine);
                
                java.sql.Connection conn =
                        BPDatabaseConnection.getConnection();
                
                String sql =
                        "INSERT INTO medicine VALUES(?,?,?,?)";
                java.sql.PreparedStatement pst =
                        conn.prepareStatement(sql);
                
                pst.setString(1,medicine.getMedicineID());
                pst.setString(2,medicine.getMedicineName());
                pst.setInt(3,medicine.getQuantity());
                pst.setDouble(4,medicine.getCost());
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(
                        null,"Medicine Added Successfully!");
                conn.close();
            } catch (Exception ex) {
                
                JOptionPane.showMessageDialog(
                        null,ex.getMessage());
            }
        });

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 350, 100, 40);
        btnUpdate.addActionListener(e -> {

    try {

        String medicineID =
                txtMedicineID.getText();

        String medicineName =
                txtMedicineName.getText();

        int quantity =
                Integer.parseInt(
                        txtQuantity.getText());

        java.sql.Connection conn =
                BPDatabaseConnection.getConnection();
        
        double cost =
                Double.parseDouble(
                        txtCost.getText());

        String sql =
                "UPDATE medicine SET medicineName=?, quantity=?, cost=? WHERE medicineID=?";

        java.sql.PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1, medicineName);
        pst.setInt(2, quantity);
        pst.setDouble(3, cost);
        pst.setString(4, medicineID);

        int rows =
                pst.executeUpdate();

        if(rows > 0){

            JOptionPane.showMessageDialog(
                    null,
                    "Medicine Updated Successfully!");

        }else{

            JOptionPane.showMessageDialog(
                    null,
                    "Medicine Not Found!");

        }

        conn.close();

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 350, 100, 40);
        btnDelete.addActionListener(e -> {

    try {

        String medicineID =
                txtMedicineID.getText();

        int confirm =
                JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this medicine?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){

            java.sql.Connection conn =
                    BPDatabaseConnection.getConnection();

            String sql =
                    "DELETE FROM medicine WHERE medicineID=?";

            java.sql.PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setString(1, medicineID);

            int rows =
                    pst.executeUpdate();

            if(rows > 0){

                JOptionPane.showMessageDialog(
                        null,
                        "Medicine Deleted Successfully!");

                txtMedicineID.setText("");
                txtMedicineName.setText("");
                txtQuantity.setText("");
                txtCost.setText("");

            }else{

                JOptionPane.showMessageDialog(
                        null,
                        "Medicine Not Found!");

            }

            conn.close();
        }

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});

        btnSearch = new JButton("Search");
        btnSearch.setBounds(410, 350, 100, 40);
        btnSearch.addActionListener(e -> {

    try {

        String medicineID =
                txtMedicineID.getText();

        java.sql.Connection conn =
                BPDatabaseConnection.getConnection();

        String sql =
                "SELECT * FROM medicine WHERE medicineID=?";

        java.sql.PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1, medicineID);

        java.sql.ResultSet rs =
                pst.executeQuery();

        if(rs.next()) {

            txtMedicineName.setText(
                    rs.getString("medicineName"));

            txtQuantity.setText(
                    String.valueOf(
                            rs.getInt("quantity")));
            txtCost.setText(
                    String.valueOf(
                            rs.getDouble("cost")));

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Medicine Not Found!");

        }

        conn.close();

    } catch(Exception ex) {

        JOptionPane.showMessageDialog(
                null,
                ex.getMessage());

    }

});

        btnBack = new JButton("Back");
        btnBack.setBounds(240, 420, 100, 40);

        btnBack.addActionListener(e -> {

            new BPPharmacistDashboard()
                    .setVisible(true);

            dispose();

        });

        panel.add(lblTitle);

        panel.add(lblMedicineID);
        panel.add(txtMedicineID);

        panel.add(lblMedicineName);
        panel.add(txtMedicineName);

        panel.add(lblQuantity);
        panel.add(txtQuantity);
        
        panel.add(lblCost);
        panel.add(txtCost);

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSearch);
        panel.add(btnBack);

        add(panel);
    }
}