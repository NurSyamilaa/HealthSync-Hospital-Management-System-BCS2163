package module2;

public abstract class Staff {
    private String staffID;
    private String name;
    private String email;

    public Staff(String staffID, String name, String email) {
        this.staffID = staffID;
        this.name = name;
        this.email = email;
    }

    // Public Getters and Setters allow controlled access from other modules
    public String getStaffID() { return staffID; }
    public void setStaffID(String staffID) { this.staffID = staffID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Abstract method
    public abstract void displayDutyDetails();
}