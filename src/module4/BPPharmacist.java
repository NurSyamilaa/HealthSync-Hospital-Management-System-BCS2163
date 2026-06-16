package module4;

public class BPPharmacist extends BPUser {

    public BPPharmacist(
            String userID,
            String name) {

        super(userID, name);

    }

    @Override
    public void displayRole() {

        System.out.println(
                "Pharmacist");

    }
}