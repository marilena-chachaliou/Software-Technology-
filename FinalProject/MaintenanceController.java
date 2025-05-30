package inncontrol;

public class MaintenanceRequest {
    private String roomNumber;
    private String issue;
    private String dateReported;
    private String status;

    public MaintenanceRequest(String roomNumber, String issue, String dateReported, String status) {
        this.roomNumber = roomNumber;
        this.issue = issue;
        this.dateReported = dateReported;
        this.status = status;
    }

    public String getRoomNumber() { return roomNumber; }
    public String getIssue() { return issue; }
    public String getDateReported() { return dateReported; }
    public String getStatus() { return status; }
}