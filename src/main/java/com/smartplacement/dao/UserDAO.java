package com.smartplacement.dao;

import com.smartplacement.model.User;
import com.smartplacement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id, name, email, role, status
                FROM users
                ORDER BY user_id
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(
                        resultSet.getInt("user_id")
                );

                user.setName(
                        resultSet.getString("name")
                );

                user.setEmail(
                        resultSet.getString("email")
                );

                user.setRole(
                        resultSet.getString("role")
                );

                user.setStatus(
                        resultSet.getString("status")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            System.out.println("Failed to retrieve users.");
            e.printStackTrace();
        }

        return users;
    }

    public User findByEmail(String email) {

    String sql = """
            SELECT user_id, name, email, password_hash, role, status
            FROM users
            WHERE email = ?
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, email);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                User user = new User();

                user.setUserId(
                        resultSet.getInt("user_id")
                );

                user.setName(
                        resultSet.getString("name")
                );

                user.setEmail(
                        resultSet.getString("email")
                );

                user.setRole(
                        resultSet.getString("role")
                );

                user.setStatus(
                        resultSet.getString("status")
                );

                // We need the password hash for authentication.
                user.setPasswordHash(
                        resultSet.getString("password_hash")
                );

                return user;
            }
        }

    } catch (SQLException e) {

        System.out.println("Error while finding user by email.");
        e.printStackTrace();
    }

    return null;
}

public int createUser(
        String name,
        String email,
        String passwordHash,
        String role,
        String status) {

    String sql = """
            INSERT INTO users
            (name, email, password_hash, role, status)
            VALUES (?, ?, ?, ?, ?)
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(
                         sql,
                         java.sql.Statement.RETURN_GENERATED_KEYS)) {

        statement.setString(1, name);
        statement.setString(2, email);
        statement.setString(3, passwordHash);
        statement.setString(4, role);
        statement.setString(5, status);

        int rows = statement.executeUpdate();

        if (rows == 1) {

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println("Unable to create user.");
        System.out.println(
                "Database error: " + e.getMessage()
        );
    }

    return -1;
}

public boolean updatePassword(
        int userId,
        String passwordHash) {

    String sql = """
            UPDATE users
            SET password_hash = ?
            WHERE user_id = ?
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, passwordHash);
        statement.setInt(2, userId);

        int rows = statement.executeUpdate();

        return rows == 1;

    } catch (SQLException e) {

        System.out.println();
        System.out.println("Unable to update password.");
        System.out.println(
                "Database error: " + e.getMessage()
        );
    }

    return false;
}
}