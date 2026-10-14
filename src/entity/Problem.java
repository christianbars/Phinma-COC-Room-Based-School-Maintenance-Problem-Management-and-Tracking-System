
package entity;

public class Problem {
    private int problemId;
    private String studentId;
    private String problem;
    private int buildingId;
    private int roomTypeId;
    private int problemTypeId;
    private String otherProblem;
    private String room;
    private String locationDescription;
    private String status;
    private String dateReported;
    private String fixedDate;
    private String unfixedReason;

    public Problem() {
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getProblemTypeId() {
        return problemTypeId;
    }

    public void setProblemTypeId(int problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    public String getOtherProblem() {
        return otherProblem;
    }

    public void setOtherProblem(String otherProblem) {
        this.otherProblem = otherProblem;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(String fixedDate) {
        this.fixedDate = fixedDate;
    }

    public String getUnfixedReason() {
        return unfixedReason;
    }

    public void setUnfixedReason(String unfixedReason) {
        this.unfixedReason = unfixedReason;
    }
}
