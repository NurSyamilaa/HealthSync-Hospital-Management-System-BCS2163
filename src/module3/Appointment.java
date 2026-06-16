package module3;

public class Appointment {

    private int appointmentId;
    private String patientName;
    private String doctorName;
    private String appointmentDate;

    public Appointment(int appointmentId, String patientName,
                       String doctorName, String appointmentDate) {

        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient Name : " + patientName);
        System.out.println("Doctor Name  : " + doctorName);
        System.out.println("Date         : " + appointmentDate);
    }
}