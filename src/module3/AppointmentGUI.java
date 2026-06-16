package module3;

import javax.swing.*;
import java.awt.*;

public class AppointmentGUI extends JFrame {

    private JTextField txtPatient;
    private JTextField txtDoctor;
    private JTextField txtDate;
    private JButton btnBook;

    public AppointmentGUI() {

        setTitle("HealthSync Appointment System");
        setSize(400,250);
        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("Patient Name:"));
        txtPatient = new JTextField();
        add(txtPatient);

        add(new JLabel("Doctor Name:"));
        txtDoctor = new JTextField();
        add(txtDoctor);

        add(new JLabel("Appointment Date:"));
        txtDate = new JTextField();
        add(txtDate);

        btnBook = new JButton("Book Appointment");
        add(btnBook);

        btnBook.addActionListener(e -> {

            String patient = txtPatient.getText();
            String doctor = txtDoctor.getText();
            String date = txtDate.getText();

            JOptionPane.showMessageDialog(
                    null,
                    "Appointment Booked!\n\n" +
                    "Patient: " + patient +
                    "\nDoctor: " + doctor +
                    "\nDate: " + date
            );
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AppointmentGUI();
    }
}