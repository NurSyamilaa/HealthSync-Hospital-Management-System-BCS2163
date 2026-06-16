package module4;

public class BPMedicalBill {

    private String billID;
    private String patientName;
    private double amount;

    public BPMedicalBill() {
    }

    public BPMedicalBill(
            String billID,
            String patientName,
            double amount) {

        this.billID = billID;
        this.patientName = patientName;
        this.amount = amount;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}