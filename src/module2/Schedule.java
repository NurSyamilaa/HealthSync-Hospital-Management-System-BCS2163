package module2;

// for second sequence diagram (Create Shift Schedule)
public class Schedule {
    private String scheduleID;
    private String staffID;
    private String date;
    private String shiftType;
    private boolean isOvertime;

    public Schedule(String scheduleID, String staffID, String date, String shiftType) {
        this.scheduleID = scheduleID;
        this.staffID = staffID;
        this.date = date;
        this.shiftType = shiftType;
        this.isOvertime = false;
    }

    // connects to 'saveSchedule()' message flow path
    public boolean saveSchedule() {
        System.out.println("System database commit: Schedule [" + scheduleID 
                + "] updated for Staff ID [" + staffID + "] on date: " + date);
        return true; 
    }

    // connects to 'flagOvertimeParameters()' opt box interaction flow
    public void flagOvertimeParameters() {
        this.isOvertime = true;
        System.out.println("System Flag Warning: Weekly thresholds exceeded. Overtime premium applied.");
    }
}