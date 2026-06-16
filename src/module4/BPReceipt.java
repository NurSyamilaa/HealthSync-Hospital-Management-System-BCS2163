package module4;

public class BPReceipt
        implements BPPrintable {

    private String receiptID;
    private BPMedicalBill bill;

    public BPReceipt(
        String receiptID,
        BPMedicalBill bill) {

    this.receiptID = receiptID;
    this.bill = bill;
    }

    @Override
    public void printReceipt() {

        System.out.println(
                "Receipt ID: "
                + receiptID);

    }
}