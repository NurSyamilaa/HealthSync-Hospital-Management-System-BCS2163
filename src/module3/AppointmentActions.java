package module3;

public interface AppointmentActions {
    void addAppointment(Appointment appointment);
    void viewAppointments();
    void cancelAppointment(int appointmentId);
}