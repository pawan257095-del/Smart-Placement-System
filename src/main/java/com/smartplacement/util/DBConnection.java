package com.smartplacement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // MySQL database URL
    private static final String URL =
            "jdbc:mysql://localhost:3306/smart_placement_db";

    // MySQL username
    private static final String USER =
            "root";

    // MySQL password
    private static final String PASSWORD =
            "Pawan@123";

    public static Connection getConnection() {

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database connection successful! ✅");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database connection failed! ❌");
            e.printStackTrace();

            return null;
        }
    }
}