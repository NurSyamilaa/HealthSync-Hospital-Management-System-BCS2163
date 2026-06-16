package module1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private String patientID;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String icPassportNumber;
    private String contactNumber;
    private String address;
    private String emergencyContact;
    private boolean isActive;
    private List<MedicalRecord> medicalHistory;

    public Patient(String patientID, String fullName, LocalDate dateOfBirth,
                   String gender, String icPassportNumber,
                   String contactNumber, String address, String emergencyContact) {
        this.patientID = patientID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.icPassportNumber = icPassportNumber;
        this.contactNumber = contactNumber;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.isActive = true;
        this.medicalHistory = new ArrayList<>();
    }

    public String getPatientID() { return patientID; }
    public String getFullName() { return fullName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getIcPassportNumber() { return icPassportNumber; }
    public String getContactNumber() { return contactNumber; }
    public String getAddress() { return address; }
    public String getEmergencyContact() { return emergencyContact; }
    public boolean isActive() { return isActive; }

    public List<MedicalRecord> getMedicalHistory() {
        return new ArrayList<>(medicalHistory);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (record == null)
            throw new IllegalArgumentException("Medical record cannot be null.");

        medicalHistory.add(record);
    }

    public String getProfileSummary() {
        return String.format(
                "ID: %s | Name: %s | DOB: %s | IC: %s | Contact: %s",
                patientID, fullName, dateOfBirth,
                icPassportNumber, contactNumber);
    }

    @Override
    public String toString() {
        return getProfileSummary();
    }
}