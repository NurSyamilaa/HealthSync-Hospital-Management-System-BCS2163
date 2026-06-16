package module4;

import java.util.ArrayList;

public class BPDataManager {

    private ArrayList<BPMedicine> medicineList;

    public BPDataManager() {

        medicineList = new ArrayList<>();

    }

    public void addMedicine(
            BPMedicine medicine) {

        medicineList.add(medicine);

    }

    public ArrayList<BPMedicine> getMedicineList() {

        return medicineList;

    }
}