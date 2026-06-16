package module1;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PatientDatabase — now uses MySQL via JDBC instead of HashMap.
 * All methods perform real SQL queries.
 */
public class PatientDatabase {

    // ── Singleton ─────────────────────────────────────────────────────────────
    private static PatientDatabase instance;
    private int idCounter = 1000;

    private PatientDatabase() {
        // Load the max existing ID on startup so we never duplicate
        try (Connection conn = DatabaseConnection.getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(
                     "SELECT MAX(CAST(SUBSTRING(patient_id,3) AS UNSIGNED)) FROM patients")) {
            if (rs.next() && rs.getObject(1) != null) {
                idCounter = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Could not read max patient ID: " + e.getMessage());
        }
    }

    public static PatientDatabase getInstance() {
        if (instance == null) instance = new PatientDatabase();
        return instance;
    }

    // ── ID Generation ─────────────────────────────────────────────────────────
    public String generatePatientID() {
        return "PT" + (++idCounter);
    }

    // ── SAVE (INSERT) ─────────────────────────────────────────────────────────
    public void save(Patient patient) {
        String sql = "INSERT INTO patients " +
                     "(patient_id, full_name, date_of_birth, gender, ic_passport, " +
                     " contact_number, address, emergency_contact, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getPatientID());
            ps.setString(2, patient.getFullName());
            ps.setDate(3,   Date.valueOf(patient.getDateOfBirth()));
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getIcPassportNumber());
            ps.setString(6, patient.getContactNumber());
            ps.setString(7, patient.getAddress());
            ps.setString(8, patient.getEmergencyContact());
            ps.setBoolean(9, patient.isActive());
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalStateException(
                    "Duplicate IC/passport: " + patient.getIcPassportNumber());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save patient: " + e.getMessage(), e);
        }
    }

    // ── FIND BY ID ────────────────────────────────────────────────────────────
    public Patient findByID(String patientID) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patientID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find patient: " + e.getMessage(), e);
        }
        return null;
    }

    // ── SEARCH BY NAME ────────────────────────────────────────────────────────
    public List<Patient> searchByName(String name) {
        String sql = "SELECT * FROM patients WHERE is_active = TRUE " +
                     "AND full_name LIKE ?";
        List<Patient> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) results.add(mapRow(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Search failed: " + e.getMessage(), e);
        }
        return results;
    }

    // ── FIND BY IC ────────────────────────────────────────────────────────────
    public Patient findByIC(String ic) {
        String sql = "SELECT * FROM patients WHERE ic_passport = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ic);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            throw new RuntimeException("IC lookup failed: " + e.getMessage(), e);
        }
        return null;
    }

    // ── CHECK DUPLICATE IC ────────────────────────────────────────────────────
    public boolean isDuplicateIC(String ic) {
        String sql = "SELECT COUNT(*) FROM patients WHERE ic_passport = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ic);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Duplicate check failed: " + e.getMessage(), e);
        }
        return false;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public void update(Patient patient) {
        String sql = "UPDATE patients SET full_name=?, contact_number=?, " +
                     "address=?, emergency_contact=?, is_active=? " +
                     "WHERE patient_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getFullName());
            ps.setString(2, patient.getContactNumber());
            ps.setString(3, patient.getAddress());
            ps.setString(4, patient.getEmergencyContact());
            ps.setBoolean(5, patient.isActive());
            ps.setString(6, patient.getPatientID());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + e.getMessage(), e);
        }
    }

    // ── ARCHIVE (SOFT DELETE) ─────────────────────────────────────────────────
    public boolean archive(String patientID) {
        String sql = "UPDATE patients SET is_active = FALSE WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patientID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Archive failed: " + e.getMessage(), e);
        }
    }

    // ── SAVE MEDICAL RECORD ───────────────────────────────────────────────────
    public void saveMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO medical_records " +
                     "(record_id, patient_id, doctor_id, consultation_date, " +
                     " diagnosis, treatment_notes, prescriptions, is_amended) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, record.getRecordID());
            ps.setString(2, record.getPatientID());
            ps.setString(3, record.getDoctorID());
            ps.setDate(4,   Date.valueOf(record.getConsultationDate()));
            ps.setString(5, record.getDiagnosis());
            ps.setString(6, record.getTreatmentNotes());
            ps.setString(7, record.getPrescriptions());
            ps.setBoolean(8, record.isAmended());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save medical record: " + e.getMessage(), e);
        }
    }

    // ── GET MEDICAL HISTORY ───────────────────────────────────────────────────
    public List<MedicalRecord> getMedicalHistory(String patientID) {
        String sql = "SELECT * FROM medical_records WHERE patient_id = ? " +
                     "ORDER BY consultation_date DESC";
        List<MedicalRecord> records = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalRecord rec = new MedicalRecord(
                        rs.getString("record_id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getDate("consultation_date").toLocalDate(),
                        rs.getString("diagnosis"),
                        rs.getString("treatment_notes"),
                        rs.getString("prescriptions"));
                if (rs.getBoolean("is_amended")) rec.markAsAmended();
                records.add(rec);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load medical history: " + e.getMessage(), e);
        }
        return records;
    }

    // ── ROW MAPPER ────────────────────────────────────────────────────────────
    private Patient mapRow(ResultSet rs) throws SQLException {
        Patient p = new Patient(
                rs.getString("patient_id"),
                rs.getString("full_name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("gender"),
                rs.getString("ic_passport"),
                rs.getString("contact_number"),
                rs.getString("address"),
                rs.getString("emergency_contact"));
        if (!rs.getBoolean("is_active")) p.setActive(false);
        return p;
    }
}