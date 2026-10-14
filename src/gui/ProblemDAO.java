
package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class ProblemDAO {

    private static String formatName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        name = name.trim().toLowerCase();

        return Character.toUpperCase(name.charAt(0))
                + name.substring(1);
    }

 
    private static boolean isDuplicateActiveProblem(
            Connection conn, Data.ProblemData p) throws SQLException {

        String sql =
                "SELECT problem_id FROM problems "
                + "WHERE building_id = ? "
                + "AND room_type_id = ? "
                + "AND problem_type_id = ? "
                + "AND LOWER(TRIM(COALESCE(problem, ''))) "
                + "= LOWER(TRIM(COALESCE(?, ''))) "
                + "AND LOWER(TRIM(COALESCE(location_description, ''))) "
                + "= LOWER(TRIM(COALESCE(?, ''))) "
                + "AND LOWER(TRIM(COALESCE(other_problem, ''))) "
                + "= LOWER(TRIM(COALESCE(?, ''))) "
                + "AND status IN ('Pending', 'In Progress') "
                + "AND archived = FALSE "
                + "LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.buildingId);
            stmt.setInt(2, p.roomTypeId);
            stmt.setInt(3, p.problemTypeId);
            stmt.setString(4, p.problem);
            stmt.setString(5, p.locationDescription);
            stmt.setString(6, p.otherProblem == null
                    ? "" : p.otherProblem);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean addProblem(Data.ProblemData p) {

        if (p == null || p.studentId == null
                || p.studentId.trim().isEmpty()) {
            return false;
        }

        if (p.buildingId <= 0 || p.roomTypeId <= 0
                || p.problemTypeId <= 0) {
            System.out.println(
                    "Invalid building, room type, or problem type ID.");
            return false;
        }

        String formattedFirstName = formatName(p.firstName);
        String formattedLastName = formatName(p.lastName);

        String checkStudent =
                "SELECT student_id FROM students "
                + "WHERE TRIM(student_id) = TRIM(?)";

        String insertStudent =
                "INSERT INTO students "
                + "(student_id, first_name, last_name, phone_number) "
                + "VALUES (?, ?, ?, ?)";

        String updateStudent =
                "UPDATE students SET first_name = ?, "
                + "last_name = ?, phone_number = ? "
                + "WHERE TRIM(student_id) = TRIM(?)";

        String insertProblem =
                "INSERT INTO problems "
                + "(student_id, problem, building, room, "
                + "location_description, building_id, room_type_id, "
                + "problem_type_id, other_problem, status, "
                + "date_reported, archived) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "'Pending', CURDATE(), FALSE)";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try {
                // CHECK DUPLICATES BEFORE SAVING
                if (isDuplicateActiveProblem(conn, p)) {
                    conn.rollback();

                    System.out.println(
                            "Duplicate problem detected. Report not saved.");

                    return false;
                }

                // CHECK WHETHER STUDENT EXISTS
                boolean studentExists;

                try (PreparedStatement stmt =
                        conn.prepareStatement(checkStudent)) {

                    stmt.setString(1, p.studentId.trim());

                    try (ResultSet rs = stmt.executeQuery()) {
                        studentExists = rs.next();
                    }
                }

                // UPDATE EXISTING STUDENT
                if (studentExists) {
                    try (PreparedStatement stmt =
                            conn.prepareStatement(updateStudent)) {

                        stmt.setString(1, formattedFirstName);
                        stmt.setString(2, formattedLastName);
                        stmt.setString(3, p.phoneNumber);
                        stmt.setString(4, p.studentId.trim());

                        stmt.executeUpdate();
                    }
                } else {
                    // INSERT NEW STUDENT
                    try (PreparedStatement stmt =
                            conn.prepareStatement(insertStudent)) {

                        stmt.setString(1, p.studentId.trim());
                        stmt.setString(2, formattedFirstName);
                        stmt.setString(3, formattedLastName);
                        stmt.setString(4, p.phoneNumber);

                        stmt.executeUpdate();
                    }
                }

                // INSERT PROBLEM
                try (PreparedStatement stmt =
                        conn.prepareStatement(insertProblem)) {

                    stmt.setString(1, p.studentId.trim());
                    stmt.setString(2, p.problem);
                    stmt.setString(3, p.building);
                    stmt.setString(4, p.room);
                    stmt.setString(5, p.locationDescription);

                    stmt.setInt(6, p.buildingId);
                    stmt.setInt(7, p.roomTypeId);
                    stmt.setInt(8, p.problemTypeId);

                    if (p.otherProblem == null
                            || p.otherProblem.trim().isEmpty()) {
                        stmt.setNull(9, java.sql.Types.VARCHAR);
                    } else {
                        stmt.setString(9, p.otherProblem.trim());
                    }

                    stmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error adding problem.");
            e.printStackTrace();
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // GET ALL ACTIVE PROBLEMS
    public static ArrayList<Data.ProblemData> getAllProblems() {

        ArrayList<Data.ProblemData> problems = new ArrayList<>();

        String sql =
                "SELECT p.problem_id, p.student_id, "
                + "s.first_name, s.last_name, s.phone_number, "
                + "p.problem, p.building, p.room, "
                + "p.location_description, p.building_id, "
                + "p.room_type_id, p.problem_type_id, "
                + "p.other_problem, p.status, p.date_reported, "
                + "p.date_accepted, p.fixed_date, p.date_unfixed, "
                + "p.unfixed_reason "
                + "FROM problems p "
                + "LEFT JOIN students s "
                + "ON TRIM(p.student_id) = TRIM(s.student_id) "
                + "WHERE p.archived = FALSE "
                + "ORDER BY p.problem_id ASC";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return problems;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    problems.add(createProblemFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading active problems.");
            e.printStackTrace();
        }

        return problems;
    }

    public static ArrayList<Data.ProblemData> getProblemsByStudent(
            String studentId) {

        ArrayList<Data.ProblemData> problems = new ArrayList<>();

        if (studentId == null || studentId.trim().isEmpty()) {
            return problems;
        }

        String sql =
                "SELECT p.problem_id, p.student_id, "
                + "s.first_name, s.last_name, s.phone_number, "
                + "p.problem, p.building, p.room, "
                + "p.location_description, p.building_id, "
                + "p.room_type_id, p.problem_type_id, "
                + "p.other_problem, p.status, p.date_reported, "
                + "p.date_accepted, p.fixed_date, p.date_unfixed, "
                + "p.unfixed_reason "
                + "FROM problems p "
                + "LEFT JOIN students s "
                + "ON TRIM(p.student_id) = TRIM(s.student_id) "
                + "WHERE TRIM(p.student_id) = TRIM(?) "
                + "AND p.archived = FALSE "
                + "ORDER BY p.problem_id ASC";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return problems;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, studentId.trim());

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        problems.add(createProblemFromResultSet(rs));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error loading problems for student ID: " + studentId);
            e.printStackTrace();
        }

        return problems;
    }

    public static Data.ProblemData getProblemById(int problemId) {

        String sql =
                "SELECT p.problem_id, p.student_id, "
                + "s.first_name, s.last_name, s.phone_number, "
                + "p.problem, p.building, p.room, "
                + "p.location_description, p.building_id, "
                + "p.room_type_id, p.problem_type_id, "
                + "p.other_problem, p.status, p.date_reported, "
                + "p.date_accepted, p.fixed_date, p.date_unfixed, "
                + "p.unfixed_reason "
                + "FROM problems p "
                + "LEFT JOIN students s "
                + "ON TRIM(p.student_id) = TRIM(s.student_id) "
                + "WHERE p.problem_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, problemId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return createProblemFromResultSet(rs);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading problem ID: " + problemId);
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateProblem(Data.ProblemData p) {

        if (p == null || p.studentId == null
                || p.studentId.trim().isEmpty()) {
            return false;
        }

        if (p.buildingId <= 0 || p.roomTypeId <= 0
                || p.problemTypeId <= 0) {
            System.out.println(
                    "Invalid building, room type, or problem type ID.");
            return false;
        }

        String updateProblem =
                "UPDATE problems SET problem = ?, building = ?, "
                + "room = ?, location_description = ?, "
                + "building_id = ?, room_type_id = ?, "
                + "problem_type_id = ?, other_problem = ? "
                + "WHERE problem_id = ? "
                + "AND TRIM(student_id) = TRIM(?) "
                + "AND status = 'Pending' AND archived = FALSE";

        String updateStudent =
                "UPDATE students SET first_name = ?, last_name = ?, "
                + "phone_number = ? "
                + "WHERE TRIM(student_id) = TRIM(?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try {
                int result;

                try (PreparedStatement stmt =
                        conn.prepareStatement(updateProblem)) {

                    stmt.setString(1, p.problem);
                    stmt.setString(2, p.building);
                    stmt.setString(3, p.room);
                    stmt.setString(4, p.locationDescription);
                    stmt.setInt(5, p.buildingId);
                    stmt.setInt(6, p.roomTypeId);
                    stmt.setInt(7, p.problemTypeId);

                    if (p.otherProblem == null
                            || p.otherProblem.trim().isEmpty()) {
                        stmt.setNull(8, java.sql.Types.VARCHAR);
                    } else {
                        stmt.setString(8, p.otherProblem.trim());
                    }

                    stmt.setInt(9, p.id);
                    stmt.setString(10, p.studentId.trim());

                    result = stmt.executeUpdate();
                }

                if (result == 0) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement stmt =
                        conn.prepareStatement(updateStudent)) {

                    stmt.setString(1, formatName(p.firstName));
                    stmt.setString(2, formatName(p.lastName));
                    stmt.setString(3, p.phoneNumber);
                    stmt.setString(4, p.studentId.trim());

                    stmt.executeUpdate();
                }

                conn.commit();

                System.out.println(
                        "Problem updated successfully. ID: " + p.id);

                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Error updating problem.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStatus(
            int problemId, String status, String unfixedReason) {

        if (!"In Progress".equals(status)
                && !"Done".equals(status)
                && !"Unfixed".equals(status)) {
            return false;
        }

        if ("Unfixed".equals(status)
                && (unfixedReason == null
                || unfixedReason.trim().isEmpty())) {
            return false;
        }

        String sql =
                "UPDATE problems SET "
                + "status = ?, "
                + "unfixed_reason = CASE "
                + "WHEN ? = 'Unfixed' THEN ? "
                + "ELSE unfixed_reason END, "
                + "date_accepted = CASE "
                + "WHEN ? = 'In Progress' "
                + "THEN COALESCE(date_accepted, CURDATE()) "
                + "ELSE date_accepted END, "
                + "fixed_date = CASE "
                + "WHEN ? = 'Done' "
                + "THEN COALESCE(fixed_date, CURDATE()) "
                + "ELSE fixed_date END, "
                + "date_unfixed = CASE "
                + "WHEN ? = 'Unfixed' "
                + "THEN COALESCE(date_unfixed, CURDATE()) "
                + "ELSE date_unfixed END "
                + "WHERE problem_id = ? AND archived = FALSE";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, status);
                stmt.setString(2, status);
                stmt.setString(3, unfixedReason);
                stmt.setString(4, status);
                stmt.setString(5, status);
                stmt.setString(6, status);
                stmt.setInt(7, problemId);

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error updating status.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean archiveProblem(int problemId) {

        String sql =
                "UPDATE problems SET archived = TRUE "
                + "WHERE problem_id = ? "
                + "AND status IN ('Done', 'Unfixed') "
                + "AND archived = FALSE";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, problemId);
                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error archiving problem.");
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Data.ProblemData> getArchivedProblems() {

        ArrayList<Data.ProblemData> problems = new ArrayList<>();

        String sql =
                "SELECT p.problem_id, p.student_id, "
                + "s.first_name, s.last_name, s.phone_number, "
                + "p.problem, p.building, p.room, "
                + "p.location_description, p.building_id, "
                + "p.room_type_id, p.problem_type_id, "
                + "p.other_problem, p.status, p.date_reported, "
                + "p.date_accepted, p.fixed_date, p.date_unfixed, "
                + "p.unfixed_reason "
                + "FROM problems p "
                + "LEFT JOIN students s "
                + "ON TRIM(p.student_id) = TRIM(s.student_id) "
                + "WHERE p.archived = TRUE "
                + "ORDER BY p.problem_id ASC";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return problems;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    problems.add(createProblemFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading archived problems.");
            e.printStackTrace();
        }

        return problems;
    }
    
    public static boolean unarchiveProblem(int problemId) {

        String sql =
                "UPDATE problems SET archived = FALSE "
                + "WHERE problem_id = ? AND archived = TRUE";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, problemId);
                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error unarchiving problem.");
            e.printStackTrace();
            return false;
        }
    }
    
    private static Data.ProblemData createProblemFromResultSet(
            ResultSet rs) throws SQLException {

        Data.ProblemData p = new Data.ProblemData(
                rs.getInt("problem_id"),
                rs.getString("student_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("problem"),
                rs.getString("building"),
                rs.getString("room"),
                rs.getString("location_description")
        );

        p.buildingId = rs.getInt("building_id");
        p.roomTypeId = rs.getInt("room_type_id");
        p.problemTypeId = rs.getInt("problem_type_id");

        p.otherProblem = rs.getString("other_problem");

        if (p.otherProblem == null) {
            p.otherProblem = "";
        }

        String status = rs.getString("status");
        p.status = status == null ? "" : status;

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("MMMM dd, yyyy");

        java.sql.Date reportedDate = rs.getDate("date_reported");
        p.dateReported = reportedDate == null
                ? "" : dateFormat.format(reportedDate);

        java.sql.Date acceptedDate = rs.getDate("date_accepted");
        p.dateAccepted = acceptedDate == null
                ? "" : dateFormat.format(acceptedDate);

        java.sql.Date fixedDate = rs.getDate("fixed_date");
        p.fixedDate = fixedDate == null
                ? "" : dateFormat.format(fixedDate);

        java.sql.Date unfixedDate = rs.getDate("date_unfixed");
        p.dateUnfixed = unfixedDate == null
                ? "" : dateFormat.format(unfixedDate);

        p.unfixedReason = rs.getString("unfixed_reason");

        if (p.unfixedReason == null) {
            p.unfixedReason = "";
        }

        return p;
    }
}
