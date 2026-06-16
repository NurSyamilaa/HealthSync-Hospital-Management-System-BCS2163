package module4;

public class BPPrescription {

    private String prescriptionID;
    private String patientName;
    private String medicineName;
    private String dosage;

    // Association
    private BPMedicine medicine;

    public BPPrescription() {
    }

    public BPPrescription(
            String prescriptionID,
            String patientName,
            String medicineName,
            String dosage) {

        this.prescriptionID = prescriptionID;
        this.patientName = patientName;
        this.medicineName = medicineName;
        this.dosage = dosage;
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public BPMedicine getMedicine() {
        return medicine;
    }

    public void setMedicine(BPMedicine medicine) {
        this.medicine = medicine;
    }
}