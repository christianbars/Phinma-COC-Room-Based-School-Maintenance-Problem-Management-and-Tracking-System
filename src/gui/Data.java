
package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Data {

    public static ArrayList<ProblemData> problems = new ArrayList<>();

    public static int nextId = 1;

    public static class ProblemData {

        public int id;
        public String studentId;
        public String firstName;
        public String lastName;
        public String phoneNumber;

        public String problem;
        public String building;
        public String room;
        public String locationDescription;

        public int buildingId;
        public int roomTypeId;
        public int problemTypeId;
        public String otherProblem;

        public String status;
        public String dateReported;
        public String dateAccepted;
        public String fixedDate;
        public String dateUnfixed;
        public String unfixedReason;

        public ProblemData(
                int id,
                String studentId,
                String firstName,
                String lastName,
                String phoneNumber,
                String problem,
                String building,
                String room,
                String locationDescription) {

            this.id = id;
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;

            this.problem = problem;
            this.building = building;
            this.room = room;
            this.locationDescription = locationDescription;

            this.buildingId = 0;
            this.roomTypeId = 0;
            this.problemTypeId = 0;
            this.otherProblem = "";

            this.status = "Pending";

            this.dateReported =
                    new SimpleDateFormat("MMMM dd, yyyy")
                            .format(new Date());

            this.dateAccepted = "";
            this.fixedDate = "";
            this.dateUnfixed = "";
            this.unfixedReason = "";
        }
    }
}
