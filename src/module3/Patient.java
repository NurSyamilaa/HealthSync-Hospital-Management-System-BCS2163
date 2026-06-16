package module3;

public class Patient extends User {

    public Patient(String name) {
        super(name);
    }

    @Override
    public void displayRole() {
        System.out.println("Patient: " + name);
    }
}