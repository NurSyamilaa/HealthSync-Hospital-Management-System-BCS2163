package module4;

public abstract class BPUser {

    protected String userID;
    protected String name;

    public BPUser(String userID, String name) {

        this.userID = userID;
        this.name = name;

    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public abstract void displayRole();
}