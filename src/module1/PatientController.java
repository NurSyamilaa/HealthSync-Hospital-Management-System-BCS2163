package module1;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Patient Management — updated to use SQL-backed PatientDatabase.
 * No changes to method signatures, so PatientManagementUI works without changes.
 */
public class PatientController {

    private final PatientDatabase db;

    public PatientController() {
        this.db = PatientDatabase.getInstance();
    }

    // UC1: Register New Patient
    public Patient registerPatient(String fullName, LocalDate dob, String gender,
                                   String ic, String contact,
                                   String address, String emergencyContact) {
        validateNotBlank(fullName, "Full name");
        validateNotBlank(ic,       "IC/Passport number");
        validateNotBlank(contact,  "Contact number");
        validateNotBlank(address,  "Address");
        validateNotNull(dob,       "Date of birth");

        if (dob.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of birth cannot be a future date.");
        if (db.isDuplicateIC(ic))
            throw new IllegalStateException("A patient with IC/Passport '" + ic + "' already exists.");

        String id = db.generatePatientID();
        Patient patient = new Patient(id, fullName, dob, gender, ic, contact, address, emergencyContact);
        db.save(patient);    // INSERT INTO patients
        return patient;
    }

    // UC2: Update Patient Profile
    public Patient updateProfile(String patientID, String newContact,
                                 String newAddress, String newEmergencyContact) {
        Patient p = getPatientOrThrow(patientID);
        if (newContact != null && !newContact.isBlank())       p.setContactNumber(newContact);
        if (newAddress != null && !newAddress.isBlank())       p.setAddress(newAddress);
        if (newEmergencyContact != null)                       p.setEmergencyContact(newEmergencyContact);
        db.update(p);        // UPDATE patients SET ...
        return p;
    }

    // UC3/UC5: Search
    public List<Patient> search(String term) {
        validateNotBlank(term, "Search term");
        if (term.length() < 3)
            throw new IllegalArgumentException("Search term must be at least 3 characters.");
        if (term.toUpperCase().startsWith("PT")) {
            Patient p = db.findByID(term.toUpperCase());  // SELECT by ID
            return p != null ? List.of(p) : List.of();
        }
        return db.searchByName(term);  // SELECT WHERE name LIKE %term%
    }

    public Patient findByIC(String ic) {
        validateNotBlank(ic, "IC/Passport number");
        return db.findByIC(ic);  // SELECT WHERE ic_passport = ?
    }

    // UC3: View Medical History
    public List<MedicalRecord> getMedicalHistory(String patientID) {
        getPatientOrThrow(patientID);
        return db.getMedicalHistory(patientID);  // SELECT FROM medical_records
    }

    // UC4: Add Medical Record
    public MedicalRecord addMedicalRecord(String patientID, String doctorID,
                                          String diagnosis, String treatmentNotes,
                                          String prescriptions) {
        getPatientOrThrow(patientID);
        validateNotBlank(diagnosis, "Diagnosis");
        validateNotBlank(doctorID,  "Doctor ID");

        String recordID = "REC" + System.currentTimeMillis();
        MedicalRecord record = new MedicalRecord(
                recordID, patientID, doctorID,
                LocalDate.now(), diagnosis, treatmentNotes, prescriptions);
        db.saveMedicalRecord(record);  // INSERT INTO medical_records
        return record;
    }

    // UC6: Archive Patient
    public boolean archivePatient(String patientID) {
        getPatientOrThrow(patientID);
        return db.archive(patientID);  // UPDATE patients SET is_active = FALSE
    }

    // Helpers
    private Patient getPatientOrThrow(String patientID) {
        Patient p = db.findByID(patientID);
        if (p == null || !p.isActive())
            throw new IllegalArgumentException("Patient not found: " + patientID);
        return p;
    }
    private void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(fieldName + " is required.");
    }
    private void validateNotNull(Object value, String fieldName) {
        if (value == null)
            throw new IllegalArgumentException(fieldName + " is required.");
    }
}