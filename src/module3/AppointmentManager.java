package module3;

import java.util.ArrayList;

public class AppointmentManager implements AppointmentActions {

    private ArrayList<Appointment> appointments;

    public AppointmentManager() {
        appointments = new ArrayList<>();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Appointment Added Successfully!");
    }

    @Override
    public void viewAppointments() {

        if (appointments.isEmpty()) {
            System.out.println("No Appointments Found.");
            return;
        }

        for (Appointment appointment : appointments) {
            appointment.displayAppointment();
            System.out.println("----------------------");
        }
    }

    @Override
    public void cancelAppointment(int appointmentId) {

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentId() == appointmentId) {
                appointments.remove(appointment);
                System.out.println("Appointment Cancelled!");
                return;
            }
        }

        System.out.println("Appointment Not Found.");
    }
}