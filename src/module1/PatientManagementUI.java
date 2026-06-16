package module1;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Main UI for the Patient Management module.
 * Uses Java Swing with a tabbed pane layout.
 * Demonstrates: event handling, OOP, encapsulation, UI-controller separation.
 */
public class PatientManagementUI extends JFrame {

    // ── Colours & Fonts ───────────────────────────────────────────────────────
    private static final Color NAVY       = new Color(31, 78, 121);
    private static final Color BLUE       = new Color(46, 117, 182);
    private static final Color LIGHT_BLUE = new Color(214, 228, 240);
    private static final Color WHITE      = Color.WHITE;
    private static final Color SUCCESS    = new Color(55, 86, 35);
    private static final Color ERROR_RED  = new Color(192, 0, 0);
    private static final Font  TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font  LABEL_FONT = new Font("Arial", Font.PLAIN, 13);
    private static final Font  BOLD_FONT  = new Font("Arial", Font.BOLD, 13);

    // ── Controller ────────────────────────────────────────────────────────────
    private final PatientController controller;

    // ── Tabs ──────────────────────────────────────────────────────────────────
    private JTabbedPane tabbedPane;

    // Register tab
    private JTextField regName, regIC, regContact, regAddress, regDOB, regEmergency;
    private JComboBox<String> regGender;
    private JLabel regStatusLabel;

    // Search tab
    private JTextField searchField;
    private JTable searchResultTable;
    private DefaultTableModel searchTableModel;
    private JTextArea profileDisplay;

    // Medical history tab
    private JTextField histPatientID;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;

    // Add record tab
    private JTextField recPatientID, recDoctorID, recPrescriptions;
    private JTextArea recDiagnosis, recNotes;
    private JLabel recStatusLabel;

    // ── Constructor ───────────────────────────────────────────────────────────
    public PatientManagementUI() {
        controller = new PatientController();
        initUI();
    }

    // ── UI Initialisation ─────────────────────────────────────────────────────
    private void initUI() {
        setTitle("HealthSync — Patient Management");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel headerTitle = new JLabel("HealthSync Management System  |  Patient Management");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setForeground(WHITE);
        header.add(headerTitle, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(BOLD_FONT);
        tabbedPane.addTab("Register Patient",    buildRegisterTab());
        tabbedPane.addTab("Search Patient",      buildSearchTab());
        tabbedPane.addTab("Medical History",     buildHistoryTab());
        tabbedPane.addTab("Add Medical Record",  buildAddRecordTab());
        add(tabbedPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(LIGHT_BLUE);
        statusBar.add(new JLabel("  Module 1: Patient Management  |  Lab 02B  |  HealthSync"));
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ── TAB 1: Register Patient ───────────────────────────────────────────────
    private JPanel buildRegisterTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(WHITE);

        JLabel title = new JLabel("Register New Patient");
        title.setFont(TITLE_FONT);
        title.setForeground(NAVY);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBackground(WHITE);

        regName      = addFormField(form, "Full Name *");
        regIC        = addFormField(form, "IC / Passport No. *");
        regDOB       = addFormField(form, "Date of Birth (YYYY-MM-DD) *");
        // Gender dropdown
        form.add(makeLabel("Gender *"));
        regGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        regGender.setFont(LABEL_FONT);
        form.add(regGender);
        regContact   = addFormField(form, "Contact Number *");
        regAddress   = addFormField(form, "Address *");
        regEmergency = addFormField(form, "Emergency Contact");

        panel.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(WHITE);
        JButton submitBtn = makeButton("Submit Registration", BLUE);
        regStatusLabel = new JLabel("");
        regStatusLabel.setFont(LABEL_FONT);
        bottom.add(submitBtn);
        bottom.add(regStatusLabel);
        panel.add(bottom, BorderLayout.SOUTH);

        submitBtn.addActionListener(e -> handleRegister());
        return panel;
    }

    private void handleRegister() {
        try {
            LocalDate dob = LocalDate.parse(regDOB.getText().trim(),
                    DateTimeFormatter.ISO_LOCAL_DATE);
            Patient p = controller.registerPatient(
                    regName.getText().trim(),
                    dob,
                    (String) regGender.getSelectedItem(),
                    regIC.getText().trim(),
                    regContact.getText().trim(),
                    regAddress.getText().trim(),
                    regEmergency.getText().trim());
            showStatus(regStatusLabel, "✓ Patient registered. ID: " + p.getPatientID(), true);
            clearRegisterForm();
        } catch (DateTimeParseException ex) {
            showStatus(regStatusLabel, "✗ Invalid date format. Use YYYY-MM-DD.", false);
        } catch (Exception ex) {
            showStatus(regStatusLabel, "✗ " + ex.getMessage(), false);
        }
    }

    private void clearRegisterForm() {
        regName.setText(""); regIC.setText(""); regDOB.setText("");
        regContact.setText(""); regAddress.setText(""); regEmergency.setText("");
    }

    // ── TAB 2: Search Patient ─────────────────────────────────────────────────
    private JPanel buildSearchTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(WHITE);
        top.add(makeLabel("Search (name / Patient ID / IC): "));
        searchField = new JTextField(25);
        searchField.setFont(LABEL_FONT);
        JButton searchBtn = makeButton("Search", BLUE);
        top.add(searchField);
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);

        // Results table
        searchTableModel = new DefaultTableModel(
                new String[]{"Patient ID", "Full Name", "IC/Passport", "Contact", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        searchResultTable = new JTable(searchTableModel);
        styleTable(searchResultTable);
        JScrollPane tableScroll = new JScrollPane(searchResultTable);
        tableScroll.setPreferredSize(new Dimension(800, 200));

        // Profile display
        profileDisplay = new JTextArea(8, 60);
        profileDisplay.setFont(LABEL_FONT);
        profileDisplay.setEditable(false);
        profileDisplay.setBorder(BorderFactory.createTitledBorder("Patient Profile"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                tableScroll, new JScrollPane(profileDisplay));
        split.setDividerLocation(220);
        panel.add(split, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> handleSearch());
        searchResultTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showSelectedProfile();
        });
        return panel;
    }

    private void handleSearch() {
        searchTableModel.setRowCount(0);
        try {
            List<Patient> results = controller.search(searchField.getText().trim());
            if (results.isEmpty()) {
                profileDisplay.setText("No patient record found.");
            } else {
                for (Patient p : results) {
                    searchTableModel.addRow(new Object[]{
                            p.getPatientID(), p.getFullName(),
                            p.getIcPassportNumber(), p.getContactNumber(),
                            p.isActive() ? "Active" : "Archived"
                    });
                }
            }
        } catch (Exception ex) {
            profileDisplay.setText("Error: " + ex.getMessage());
        }
    }

    private void showSelectedProfile() {
        int row = searchResultTable.getSelectedRow();
        if (row < 0) return;
        String id = (String) searchTableModel.getValueAt(row, 0);
        List<Patient> results = controller.search(id);
        if (!results.isEmpty()) {
            Patient p = results.get(0);
            profileDisplay.setText(
                    "Patient ID:        " + p.getPatientID() + "\n" +
                    "Full Name:         " + p.getFullName() + "\n" +
                    "Date of Birth:     " + p.getDateOfBirth() + "\n" +
                    "Gender:            " + p.getGender() + "\n" +
                    "IC/Passport:       " + p.getIcPassportNumber() + "\n" +
                    "Contact:           " + p.getContactNumber() + "\n" +
                    "Address:           " + p.getAddress() + "\n" +
                    "Emergency Contact: " + p.getEmergencyContact() + "\n" +
                    "Status:            " + (p.isActive() ? "Active" : "Archived") + "\n" +
                    "Medical Records:   " + p.getMedicalHistory().size() + " record(s)"
            );
        }
    }

    // ── TAB 3: Medical History ────────────────────────────────────────────────
    private JPanel buildHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(WHITE);
        top.add(makeLabel("Patient ID: "));
        histPatientID = new JTextField(15);
        histPatientID.setFont(LABEL_FONT);
        JButton loadBtn = makeButton("Load History", BLUE);
        top.add(histPatientID);
        top.add(loadBtn);
        panel.add(top, BorderLayout.NORTH);

        historyTableModel = new DefaultTableModel(
                new String[]{"Record ID", "Date", "Doctor ID", "Diagnosis", "Prescriptions", "Amended"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        historyTable = new JTable(historyTableModel);
        styleTable(historyTable);
        panel.add(new JScrollPane(historyTable), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> {
            historyTableModel.setRowCount(0);
            try {
                List<MedicalRecord> history = controller.getMedicalHistory(histPatientID.getText().trim());
                if (history.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No medical records found for this patient.");
                } else {
                    for (MedicalRecord r : history) {
                        historyTableModel.addRow(new Object[]{
                                r.getRecordID(), r.getConsultationDate(),
                                r.getDoctorID(), r.getDiagnosis(),
                                r.getPrescriptions(), r.isAmended() ? "Yes" : "No"
                        });
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // ── TAB 4: Add Medical Record ─────────────────────────────────────────────
    private JPanel buildAddRecordTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(WHITE);

        JLabel title = new JLabel("Add Medical Record");
        title.setFont(TITLE_FONT);
        title.setForeground(NAVY);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBackground(WHITE);
        recPatientID   = addFormField(form, "Patient ID *");
        recDoctorID    = addFormField(form, "Doctor ID *");
        recPrescriptions = addFormField(form, "Prescriptions");

        form.add(makeLabel("Diagnosis *"));
        recDiagnosis = new JTextArea(3, 20);
        recDiagnosis.setFont(LABEL_FONT);
        form.add(new JScrollPane(recDiagnosis));

        form.add(makeLabel("Treatment Notes"));
        recNotes = new JTextArea(3, 20);
        recNotes.setFont(LABEL_FONT);
        form.add(new JScrollPane(recNotes));

        panel.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(WHITE);
        JButton saveBtn = makeButton("Save Record", BLUE);
        recStatusLabel = new JLabel("");
        recStatusLabel.setFont(LABEL_FONT);
        bottom.add(saveBtn);
        bottom.add(recStatusLabel);
        panel.add(bottom, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            try {
                MedicalRecord rec = controller.addMedicalRecord(
                        recPatientID.getText().trim(),
                        recDoctorID.getText().trim(),
                        recDiagnosis.getText().trim(),
                        recNotes.getText().trim(),
                        recPrescriptions.getText().trim());
                showStatus(recStatusLabel, "✓ Record saved. ID: " + rec.getRecordID(), true);
                recDiagnosis.setText(""); recNotes.setText("");
                recPrescriptions.setText("");
            } catch (Exception ex) {
                showStatus(recStatusLabel, "✗ " + ex.getMessage(), false);
            }
        });
        return panel;
    }

    // ── UI Helpers ────────────────────────────────────────────────────────────
    private JTextField addFormField(JPanel panel, String label) {
        panel.add(makeLabel(label));
        JTextField field = new JTextField();
        field.setFont(LABEL_FONT);
        panel.add(field);
        return field;
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(LABEL_FONT);
        return lbl;
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(BOLD_FONT);
        btn.setBackground(bg);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        return btn;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(24);
        table.setFont(LABEL_FONT);
        table.getTableHeader().setFont(BOLD_FONT);
        table.getTableHeader().setBackground(NAVY);
        table.getTableHeader().setForeground(WHITE);
        table.setSelectionBackground(LIGHT_BLUE);
        table.setGridColor(Color.LIGHT_GRAY);
    }

    private void showStatus(JLabel label, String msg, boolean success) {
        label.setText(msg);
        label.setForeground(success ? SUCCESS : ERROR_RED);
    }

    // ── Entry Point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientManagementUI());
    }
}