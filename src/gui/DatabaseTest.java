package gui;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("DATABASE CONNECTED SUCCESSFULLY!");

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("DATABASE CONNECTION FAILED!");
        }
    }
}