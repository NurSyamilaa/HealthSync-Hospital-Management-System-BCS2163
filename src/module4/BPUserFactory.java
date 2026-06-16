package module4;

public class BPUserFactory {

    public static BPUser createUser(
            String role) {

        if(role.equals(
                "Billing Officer")) {

            return new BPBillingOfficer(
                    "B001",
                    "Billing Staff");

        }

        return new BPPharmacist(
                "P001",
                "Pharmacy Staff");

    }
}