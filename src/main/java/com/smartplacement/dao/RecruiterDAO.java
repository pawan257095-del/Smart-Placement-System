package com.smartplacement.dao;

import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecruiterDAO {

    /**
     * Checks whether a company with the given name already exists.
     */
    public int findCompanyByName(String companyName) {

        String sql = """
                SELECT company_id
                FROM companies
                WHERE company_name = ?
                LIMIT 1
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, companyName);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("company_id");
                }
            }

        } catch (SQLException e) {

            System.out.println("Error checking company.");
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Creates a new company and returns company_id.
     */
    public int createCompany(
            String companyName,
            String industry,
            String website,
            String email,
            String phone,
            String address,
            String description) {

        String sql = """
                INSERT INTO companies
                (
                    company_name,
                    industry,
                    website,
                    email,
                    phone,
                    address,
                    description,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, companyName);
            statement.setString(2, industry);
            statement.setString(3, website);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, address);
            statement.setString(7, description);

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

            System.out.println("Unable to create company.");
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return -1;
    }

    /**
     * Creates recruiter record linked to user and company.
     */
    public int createRecruiter(
            int userId,
            int companyId) {

        String sql = """
                INSERT INTO recruiters
                (
                    user_id,
                    company_id
                )
                VALUES (?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, userId);
            statement.setInt(2, companyId);

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

            System.out.println("Unable to create recruiter.");
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return -1;
    }
    /**
 * Updates company profile details.
 */
public boolean updateCompany(
        int companyId,
        String companyName,
        String industry,
        String website,
        String email,
        String phone,
        String address,
        String description) {

    String sql = """
            UPDATE companies
            SET
                company_name = ?,
                industry = ?,
                website = ?,
                email = ?,
                phone = ?,
                address = ?,
                description = ?
            WHERE company_id = ?
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setString(1, companyName);
        statement.setString(2, industry);
        statement.setString(3, website);
        statement.setString(4, email);
        statement.setString(5, phone);
        statement.setString(6, address);
        statement.setString(7, description);
        statement.setInt(8, companyId);

        return statement.executeUpdate() == 1;

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to update company profile."
        );

        System.out.println(
                "Database error: " + e.getMessage()
        );

        return false;
    }
}

/**
 * Finds the company ID belonging to a recruiter user.
 */
public int findCompanyIdByUserId(int userId) {

    String sql = """
            SELECT company_id
            FROM recruiters
            WHERE user_id = ?
            LIMIT 1
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        try (ResultSet rs =
                     statement.executeQuery()) {

            if (rs.next()) {

                return rs.getInt("company_id");
            }
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to find recruiter company."
        );

        System.out.println(
                "Database error: "
                        + e.getMessage()
        );
    }

    return -1;
}
}
