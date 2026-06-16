package module4;

public class BPOOPTest {

    public static void main(String[] args) {

        BPUser user1 =
                new BPBillingOfficer(
                        "BO001",
                        "Ali");

        BPUser user2 =
                new BPPharmacist(
                        "PH001",
                        "Siti");

        user1.displayRole();
        user2.displayRole();

        BPMedicalBill bill =
        new BPMedicalBill(
                "B001",
                "John Doe",
                100.00);

        BPPrintable receipt =
                new BPReceipt(
                        "R001",
                        bill);

        receipt.printReceipt();

    }
}