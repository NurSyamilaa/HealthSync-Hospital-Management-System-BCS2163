package module2;

// Inheritance: Doctor inherits all properties (ID, name, email) from Staff
public class Doctor extends Staff {
    private String specialisation;
    private String licenseNumber;

    public Doctor(String staffID, String name, String email, String specialisation, String licenseNumber) {
        super(staffID, name, email); // Passes shared data up to the parent Staff constructor
        this.specialisation = specialisation;
        this.licenseNumber = licenseNumber;
    }

    public String getSpecialisation() { return specialisation; }
    public void setSpecialisation(String specialisation) { this.specialisation = specialisation; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    // Polymorphism: Dynamic Method Overriding
    @Override
    public void displayDutyDetails() {
        System.out.println("[DOCTOR PROFILE] ID: " + getStaffID() 
                + " | Name: " + getName() 
                + " | Specialisation: " + specialisation 
                + " | Status: Active on clinical rounds.");
    }
}