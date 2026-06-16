package module1;

import java.time.LocalDate;

/**
 * Represents a single medical record entry for a patient.
 * Immutable once created (records cannot be deleted, only amended).
 */
public class MedicalRecord {

    // ── Attributes ────────────────────────────────────────────────────────────
    private final String recordID;
    private final String patientID;
    private final String doctorID;
    private final LocalDate consultationDate;
    private String diagnosis;
    private String treatmentNotes;
    private String prescriptions;
    private String attachmentPath;  // optional lab result file path
    private boolean isAmended;

    // ── Constructor ───────────────────────────────────────────────────────────
    public MedicalRecord(String recordID, String patientID, String doctorID,
                         LocalDate consultationDate, String diagnosis,
                         String treatmentNotes, String prescriptions) {
        this.recordID         = recordID;
        this.patientID        = patientID;
        this.doctorID         = doctorID;
        this.consultationDate = consultationDate;
        this.diagnosis        = diagnosis;
        this.treatmentNotes   = treatmentNotes;
        this.prescriptions    = prescriptions;
        this.attachmentPath   = null;
        this.isAmended        = false;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getRecordID()           { return recordID; }
    public String getPatientID()          { return patientID; }
    public String getDoctorID()           { return doctorID; }
    public LocalDate getConsultationDate(){ return consultationDate; }
    public String getDiagnosis()          { return diagnosis; }
    public String getTreatmentNotes()     { return treatmentNotes; }
    public String getPrescriptions()      { return prescriptions; }
    public String getAttachmentPath()     { return attachmentPath; }
    public boolean isAmended()            { return isAmended; }

    // ── Business Methods ──────────────────────────────────────────────────────
    /**
     * Attaches a supporting document (lab result, scan, etc.) to this record.
     */
    public void attachDocument(String filePath) {
        if (filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("File path cannot be empty.");
        this.attachmentPath = filePath;
    }

    /**
     * Marks this record as amended (corrections require a new amendment record).
     */
    public void markAsAmended() {
        this.isAmended = true;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Doctor: %s | Diagnosis: %s",
                recordID, consultationDate, doctorID, diagnosis);
    }
}