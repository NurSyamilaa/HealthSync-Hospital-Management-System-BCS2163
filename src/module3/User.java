package module3;

public class User {

    protected String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void displayRole() {
        System.out.println("System User");
    }
}