package gui;

import java.sql.Connection;

public class TestDatabase {

    public static void main(String[] args) {

        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {

            System.out.println(
                    "Database connected successfully!"
            );

            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println(
                    "Database connection failed!"
            );
        }
    }
}