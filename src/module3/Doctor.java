package module3;

public class Doctor extends User {

    public Doctor(String name) {
        super(name);
    }

    @Override
    public void displayRole() {
        System.out.println("Doctor: " + name);
    }
}