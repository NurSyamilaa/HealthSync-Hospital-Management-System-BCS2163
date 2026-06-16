package module2;

import java.util.ArrayList;
import java.util.List;

// Composition (Aggregation)
public class Department {
    private String deptID;
    private String deptName;
    private List<Staff> assignedStaff; // Collection reference to Staff class objects

    public Department(String deptID, String deptName) {
        this.deptID = deptID;
        this.deptName = deptName;
        this.assignedStaff = new ArrayList<>();
    }

    public String getDeptID() { return deptID; }
    public void setDeptID(String deptID) { this.deptID = deptID; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public boolean linkStaffToDept(Staff staff) {
        if (staff != null) {
            assignedStaff.add(staff);
            System.out.println("System Confirmation: " + staff.getName() 
                    + " has been successfully linked to the " + deptName + ".");
            return true; // returns true for 'mappingSuccessful' sequence path
        }
        return false;
    }
}