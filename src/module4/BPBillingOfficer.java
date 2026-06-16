package module4;

public class BPBillingOfficer extends BPUser {

    public BPBillingOfficer(
            String userID,
            String name) {

        super(userID, name);

    }

    @Override
    public void displayRole() {

        System.out.println(
                "Billing Officer");

    }
}