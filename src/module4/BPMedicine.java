package module4;

public class BPMedicine {

    private String medicineID;
    private String medicineName;
    private int quantity;
    private double cost;

    public BPMedicine() {
    }

    public BPMedicine(
            String medicineID,
            String medicineName,
            int quantity,
            double cost) {

        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}