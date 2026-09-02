package com.smartplacement.dao;

import com.smartplacement.model.Student;
import com.smartplacement.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    public Student findByUserId(int userId) {

        String sql = """
                SELECT
                    student_id,
                    user_id,
                    enrollment_no,
                    phone,
                    course,
                    branch,
                    semester,
                    cgpa,
                    backlogs,
                    graduation_year
                FROM students
                WHERE user_id = ?
                """;

        System.out.println(
                "DEBUG: StudentDAO searching for user_id = " + userId
        );

        try (Connection connection = DBConnection.getConnection()) {

            if (connection == null) {
                System.out.println("DEBUG: Connection is NULL.");
                return null;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, userId);

                System.out.println(
                        "DEBUG: Executing student query..."
                );

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        System.out.println(
                                "DEBUG: Student record FOUND."
                        );

                        Student student = new Student();

                        student.setStudentId(
                                resultSet.getInt("student_id")
                        );

                        student.setUserId(
                                resultSet.getInt("user_id")
                        );

                        student.setEnrollmentNo(
                                resultSet.getString("enrollment_no")
                        );

                        student.setPhone(
                                resultSet.getString("phone")
                        );

                        student.setCourse(
                                resultSet.getString("course")
                        );

                        student.setBranch(
                                resultSet.getString("branch")
                        );

                        student.setSemester(
                                resultSet.getInt("semester")
                        );

                        student.setCgpa(
                                resultSet.getDouble("cgpa")
                        );

                        student.setBacklogs(
                                resultSet.getInt("backlogs")
                        );

                        student.setGraduationYear(
                                resultSet.getInt("graduation_year")
                        );

                        return student;
                    }

                    System.out.println(
                            "DEBUG: No student record found."
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "DEBUG: SQL error while loading profile."
            );

            e.printStackTrace();
        }

        return null;
    }

    public boolean createStudent(
        int userId,
        String enrollmentNo,
        String phone,
        String course,
        String branch,
        int semester,
        double cgpa,
        int backlogs,
        int graduationYear) {

    String sql = """
            INSERT INTO students
            (
                user_id,
                enrollment_no,
                phone,
                course,
                branch,
                semester,
                cgpa,
                backlogs,
                graduation_year
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);
        statement.setString(2, enrollmentNo);
        statement.setString(3, phone);
        statement.setString(4, course);
        statement.setString(5, branch);
        statement.setInt(6, semester);
        statement.setDouble(7, cgpa);
        statement.setInt(8, backlogs);
        statement.setInt(9, graduationYear);

        return statement.executeUpdate() == 1;

    } catch (SQLException e) {

        System.out.println();
        System.out.println("Unable to create student profile.");
        System.out.println(
                "Database error: " + e.getMessage()
        );

        return false;
    }
}
public boolean updateStudentProfile(
        int userId,
        String phone,
        String course,
        String branch,
        int semester,
        double cgpa,
        int backlogs,
        int graduationYear) {

    String sql = """
            UPDATE students
            SET
                phone = ?,
                course = ?,
                branch = ?,
                semester = ?,
                cgpa = ?,
                backlogs = ?,
                graduation_year = ?
            WHERE user_id = ?
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, phone);
        statement.setString(2, course);
        statement.setString(3, branch);
        statement.setInt(4, semester);
        statement.setDouble(5, cgpa);
        statement.setInt(6, backlogs);
        statement.setInt(7, graduationYear);
        statement.setInt(8, userId);

        int rows = statement.executeUpdate();

        return rows == 1;

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to update student profile."
        );

        System.out.println(
                "Database error: " + e.getMessage()
        );

        return false;
    }
}
}