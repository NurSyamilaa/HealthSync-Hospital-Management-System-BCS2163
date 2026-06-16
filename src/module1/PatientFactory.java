package module1;

import java.time.LocalDate;


public class PatientFactory {

    /**
     * Creates and returns a new Patient instance.
     */
    public Patient createPatient(String id, String fullName, LocalDate dob, String gender,
                                 String ic, String contact, String address, String emergencyContact) {
        return new Patient(id, fullName, dob, gender, ic, contact, address, emergencyContact);
    }

    /**
     * Creates and returns a new MedicalRecord instance.
     */
    public MedicalRecord createMedicalRecord(String recordID, String patientID, String doctorID,
                                             LocalDate date, String diagnosis, String treatmentNotes,
                                             String prescriptions) {
        return new MedicalRecord(recordID, patientID, doctorID, date, diagnosis, treatmentNotes, prescriptions);
    }
}