package module1;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TopsisUI extends JPanel {

    private static final Color NAVY    = new Color(31, 78, 121);
    private static final Color BLUE    = new Color(46, 117, 182);
    private static final Color LBLUE   = new Color(214, 228, 240);
    private static final Color WHITE   = Color.WHITE;
    private static final Color LGRAY   = new Color(245, 245, 245);
    private static final Color SUCCESS = new Color(55, 86, 35);
    private static final Color GOLD    = new Color(180, 120, 0);
    private static final Color ERR     = new Color(192, 0, 0);
    private static final Font  TITLE   = new Font("Arial", Font.BOLD, 15);
    private static final Font  BOLD    = new Font("Arial", Font.BOLD, 13);
    private static final Font  PLAIN   = new Font("Arial", Font.PLAIN, 13);
    private static final Font  MONO    = new Font("Courier New", Font.PLAIN, 12);

    // Input fields for adding a patient to TOPSIS
    private JTextField inPatientID, inName, inAge, inRecords, inDays;
    private JLabel     addStatus;

    // Table showing patients added to the ranking
    private DefaultTableModel inputModel;
    private JTable             inputTable;

    // Results table
    private DefaultTableModel resultModel;
    private JTable             resultTable;

    // Internal data
    private final List<PatientTopsis.PatientData> patientList = new ArrayList<>();

    public TopsisUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(WHITE);
        setBorder(new EmptyBorder(18, 28, 18, 28));

        add(buildInfoPanel(),    BorderLayout.NORTH);
        add(buildCentrePanel(),  BorderLayout.CENTER);
        add(buildResultPanel(),  BorderLayout.SOUTH);
    }

    // ── Info banner ───────────────────────────────────────────────────────────
    private JPanel buildInfoPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(LBLUE);
        p.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel title = new JLabel("Patient Priority Ranking — TOPSIS Method");
        title.setFont(TITLE);
        title.setForeground(NAVY);

        JLabel info = new JLabel(
            "<html>Ranks patients by treatment priority using 3 criteria: " +
            "<b>Age (40%)</b> · <b>No. of Medical Records (35%)</b> · " +
            "<b>Days Since Last Visit (25%)</b>. " +
            "Higher TOPSIS score = higher priority.</html>");
        info.setFont(PLAIN);

        p.add(title, BorderLayout.NORTH);
        p.add(info,  BorderLayout.CENTER);
        return p;
    }

    // ── Input + patient list ──────────────────────────────────────────────────
    private JPanel buildCentrePanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(WHITE);

        // ── Input form
        JPanel form = new JPanel(new GridLayout(2, 6, 8, 8));
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Add Patient to Ranking"));

        form.add(lbl("Patient ID"));
        form.add(lbl("Name"));
        form.add(lbl("Age"));
        form.add(lbl("No. of Records"));
        form.add(lbl("Days Since Visit"));
        form.add(new JLabel()); // spacer

        inPatientID = tf(); form.add(inPatientID);
        inName      = tf(); form.add(inName);
        inAge       = tf(); form.add(inAge);
        inRecords   = tf(); form.add(inRecords);
        inDays      = tf(); form.add(inDays);

        JButton addBtn = btn("Add Patient", BLUE);
        addStatus = new JLabel(); addStatus.setFont(PLAIN);
        JPanel addRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addRow.setBackground(WHITE);
        addRow.add(addBtn); addRow.add(addStatus);

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.setBackground(WHITE);
        top.add(form,   BorderLayout.CENTER);
        top.add(addRow, BorderLayout.SOUTH);

        // ── Patient input table
        inputModel = new DefaultTableModel(
            new String[]{"Patient ID","Name","Age","No. Records","Days Since Visit"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        inputTable = new JTable(inputModel);
        styleTable(inputTable);
        JScrollPane scroll = new JScrollPane(inputTable);
        scroll.setBorder(BorderFactory.createTitledBorder("Patients Added for Ranking"));
        scroll.setPreferredSize(new Dimension(800, 160));

        // ── Run + Clear buttons
        JButton runBtn   = btn("▶  Run TOPSIS Ranking", NAVY);
        JButton clearBtn = btn("Clear All", ERR);
        JButton loadBtn  = btn("Load Sample Patients", GOLD);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRow.setBackground(WHITE);
        btnRow.add(runBtn); btnRow.add(clearBtn); btnRow.add(loadBtn);

        p.add(top,    BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(btnRow, BorderLayout.SOUTH);

        // ── Events
        addBtn.addActionListener(e -> handleAdd());
        clearBtn.addActionListener(e -> {
            patientList.clear();
            inputModel.setRowCount(0);
            resultModel.setRowCount(0);
            status(addStatus, "Cleared.", true);
        });
        loadBtn.addActionListener(e -> loadSamples());
        runBtn.addActionListener(e -> handleRun());

        return p;
    }

    // ── Results panel ─────────────────────────────────────────────────────────
    private JPanel buildResultPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(WHITE);

        resultModel = new DefaultTableModel(
            new String[]{"Rank","Patient ID","Name","Age","Records","Days","TOPSIS Score","Priority"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        resultTable = new JTable(resultModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                // Highlight top 3 rows
                if (!isRowSelected(row)) {
                    String rank = (String) getModel().getValueAt(row, 0);
                    if ("🥇 1".equals(rank)) c.setBackground(new Color(255, 243, 205));
                    else if ("🥈 2".equals(rank)) c.setBackground(new Color(235, 235, 235));
                    else if ("🥉 3".equals(rank)) c.setBackground(new Color(255, 235, 220));
                    else c.setBackground(WHITE);
                }
                return c;
            }
        };
        styleTable(resultTable);
        resultTable.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(resultTable);
        scroll.setBorder(BorderFactory.createTitledBorder("TOPSIS Ranking Results"));
        scroll.setPreferredSize(new Dimension(800, 180));

        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // ── Event Handlers ────────────────────────────────────────────────────────
    private void handleAdd() {
        try {
            String id      = inPatientID.getText().trim();
            String name    = inName.getText().trim();
            double age     = Double.parseDouble(inAge.getText().trim());
            double records = Double.parseDouble(inRecords.getText().trim());
            double days    = Double.parseDouble(inDays.getText().trim());

            if (id.isEmpty() || name.isEmpty())
                throw new IllegalArgumentException("Patient ID and Name are required.");
            if (age <= 0 || records < 0 || days < 0)
                throw new IllegalArgumentException("Age must be > 0. Records and Days must be ≥ 0.");

            PatientTopsis.PatientData pd =
                new PatientTopsis.PatientData(id, name, new double[]{age, records, days});
            patientList.add(pd);

            inputModel.addRow(new Object[]{id, name,
                (int) age, (int) records, (int) days});

            status(addStatus, "✓ " + name + " added.", true);
            clearInputFields();

        } catch (NumberFormatException ex) {
            status(addStatus, "✗ Age, Records and Days must be numbers.", false);
        } catch (Exception ex) {
            status(addStatus, "✗ " + ex.getMessage(), false);
        }
    }

    private void handleRun() {
        resultModel.setRowCount(0);

        if (patientList.size() < 2) {
            JOptionPane.showMessageDialog(this,
                "Please add at least 2 patients before running TOPSIS.",
                "Not Enough Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<PatientTopsis.RankResult> results =
                PatientTopsis.rankPatients(patientList);

            for (PatientTopsis.RankResult r : results) {
                // Find original criteria
                PatientTopsis.PatientData pd = patientList.stream()
                    .filter(p -> p.patientID.equals(r.patientID))
                    .findFirst().orElse(null);

                String rankStr = r.rank == 1 ? "🥇 1"
                               : r.rank == 2 ? "🥈 2"
                               : r.rank == 3 ? "🥉 3"
                               : String.valueOf(r.rank);

                String priority = r.score >= 0.7 ? "HIGH"
                                : r.score >= 0.4 ? "MEDIUM"
                                : "LOW";

                resultModel.addRow(new Object[]{
                    rankStr,
                    r.patientID,
                    r.name,
                    pd != null ? (int) pd.criteria[0] : "-",
                    pd != null ? (int) pd.criteria[1] : "-",
                    pd != null ? (int) pd.criteria[2] : "-",
                    String.format("%.4f", r.score),
                    priority
                });
            }

            // Print to console (matches lecturer's template output)
            PatientTopsis.printRanking(results);

            JOptionPane.showMessageDialog(this,
                "TOPSIS ranking complete!\n\nHighest Priority Patient: "
                + results.get(0).name
                + "\nTOPSIS Score: " + String.format("%.4f", results.get(0).score),
                "Ranking Complete", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error running TOPSIS: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSamples() {
        patientList.clear();
        inputModel.setRowCount(0);
        resultModel.setRowCount(0);

        Object[][] samples = {
            {"PT1001", "Ahmad Bin Ali",        65, 8,  30},
            {"PT1002", "Siti Binti Hassan",    45, 3,  10},
            {"PT1003", "Raj Kumar",            72, 12, 60},
            {"PT1004", "Lim Mei Ling",         30, 1,  5},
            {"PT1005", "Nurul Ain Binti Zain", 58, 6,  45},
        };

        for (Object[] s : samples) {
            patientList.add(new PatientTopsis.PatientData(
                (String) s[0], (String) s[1],
                new double[]{(int) s[2], (int) s[3], (int) s[4]}));
            inputModel.addRow(new Object[]{s[0], s[1], s[2], s[3], s[4]});
        }
        status(addStatus, "✓ 5 sample patients loaded. Click Run TOPSIS.", true);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void clearInputFields() {
        inPatientID.setText(""); inName.setText("");
        inAge.setText(""); inRecords.setText(""); inDays.setText("");
    }

    private JTextField tf() {
        JTextField f = new JTextField(); f.setFont(PLAIN); return f;
    }
    private JLabel lbl(String t) { JLabel l = new JLabel(t); l.setFont(BOLD); return l; }
    private JButton btn(String t, Color bg) {
        JButton b = new JButton(t);
        b.setFont(BOLD); b.setBackground(bg); b.setForeground(WHITE);
        b.setFocusPainted(false); b.setBorder(new EmptyBorder(7, 14, 7, 14));
        return b;
    }
    private void styleTable(JTable t) {
        t.setRowHeight(24); t.setFont(PLAIN);
        t.getTableHeader().setFont(BOLD);
        t.getTableHeader().setBackground(NAVY);
        t.getTableHeader().setForeground(WHITE);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setSelectionBackground(LBLUE);
    }
    private void status(JLabel l, String msg, boolean ok) {
        l.setText(msg); l.setForeground(ok ? SUCCESS : ERR);
    }
}