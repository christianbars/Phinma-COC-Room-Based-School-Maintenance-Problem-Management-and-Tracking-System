
package gui;

import entity.Building;
import entity.RoomType;
import entity.ProblemType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityDAO {

    public List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>();

        String sql = "SELECT building_id, building_name "
                   + "FROM buildings ORDER BY building_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                buildings.add(new Building(
                    rs.getInt("building_id"),
                    rs.getString("building_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();

        String sql = "SELECT room_type_id, room_type_name "
                   + "FROM room_types ORDER BY room_type_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                roomTypes.add(new RoomType(
                    rs.getInt("room_type_id"),
                    rs.getString("room_type_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomTypes;
    }

    public List<ProblemType> getAllProblemTypes() {
        List<ProblemType> problemTypes = new ArrayList<>();

        String sql = "SELECT problem_type_id, problem_type_name "
                   + "FROM problem_types ORDER BY problem_type_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                problemTypes.add(new ProblemType(
                    rs.getInt("problem_type_id"),
                    rs.getString("problem_type_name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return problemTypes;
    }
}
