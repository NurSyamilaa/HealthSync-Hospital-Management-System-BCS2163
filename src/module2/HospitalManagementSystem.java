package module2;

public class HospitalManagementSystem {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  HOSPITAL MANAGEMENT SYSTEM: STAFF MODULE RUNNING");
        System.out.println("==================================================\n");

        // 1. Test Use Case 1: Manage Doctor Profile & Polymorphism
        System.out.println("[EXECUTION]: Running Use Case - Manage Doctor Profile...");
        Staff doctor1 = new Doctor("D201", "Dr. Alan Turing", "alan.t@hospital.com", "Cardiology", "LIC-77621");
        Staff doctor2 = new Doctor("D202", "Dr. Ada Lovelace", "ada.l@hospital.com", "Pediatrics", "LIC-88941");
        
        // Dynamic Polymorphic dispatch (Calls overridden method in Doctor class)
        doctor1.displayDutyDetails();
        doctor2.displayDutyDetails();
        System.out.println("Result: Profile transaction completed successfully.\n");

        // 2. Test Use Case 2: Create Shift Schedule & Overtime Opt Frame
        System.out.println("[EXECUTION]: Running Use Case - Create Shift Schedule...");
        Schedule shift1 = new Schedule("SCH-001", doctor1.getStaffID(), "2026-06-12", "Night Shift");
        shift1.saveSchedule();
        
        // Simulating the 'opt' condition threshold check from your Sequence Diagram
        System.out.println("Checking conditional rules: Hours worked > 40...");
        shift1.flagOvertimeParameters(); 
        System.out.println("Result: Schedule entries saved safely.\n");

        // 3. Test Use Case 3: Assign Department (Composition Mapping)
        System.out.println("[EXECUTION]: Running Use Case - Assign Department...");
        Department emergencyDept = new Department("DEP-EMER", "Emergency Ward");
        
        // Map the polymorphically created Doctor objects directly into the unit container
        emergencyDept.linkStaffToDept(doctor1);
        emergencyDept.linkStaffToDept(doctor2);
        
        System.out.println("\n==================================================");
        System.out.println("     DEMONSTRATION RUN FINISHED WITHOUT ERRORS    ");
        System.out.println("==================================================");
    }
}