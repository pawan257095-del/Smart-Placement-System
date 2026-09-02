package com.smartplacement.dao;

import com.smartplacement.model.Application;
import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    /**
     * Checks whether a student has already applied for a job.
     */
    public boolean hasApplied(int studentId, int jobId) {

        String sql = """
                SELECT application_id
                FROM applications
                WHERE student_id = ?
                  AND job_id = ?
                LIMIT 1
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, jobId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {

            System.out.println("Error checking existing application.");
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Creates a new application.
     */
    public boolean createApplication(int studentId, int jobId) {

        String sql = """
                INSERT INTO applications
                (student_id, job_id, status)
                VALUES (?, ?, 'APPLIED')
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, studentId);
            statement.setInt(2, jobId);

            int rows = statement.executeUpdate();

            if (rows == 1) {

                try (ResultSet keys = statement.getGeneratedKeys()) {

                    if (keys.next()) {

                        System.out.println(
                                "DEBUG: Application ID = "
                                        + keys.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {

            System.out.println("Error creating application.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets all applications of a student,
     * along with complete job information.
     */
    public List<Application> getApplicationsByStudent(int studentId) {

        List<Application> applications = new ArrayList<>();

        String sql = """
                SELECT
                    a.application_id,
                    a.student_id,
                    a.job_id,
                    a.applied_at,
                    a.status,
                    a.remarks,

                    j.title,
                    j.description,
                    j.location,
                    j.employment_type,
                    j.salary,
                    j.min_cgpa,
                    j.max_backlogs,
                    j.application_deadline

                FROM applications a

                INNER JOIN jobs j
                    ON a.job_id = j.job_id

                WHERE a.student_id = ?

                ORDER BY a.applied_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    Application application = new Application();

                    // Application information
                    application.setApplicationId(
                            rs.getInt("application_id"));

                    application.setStudentId(
                            rs.getInt("student_id"));

                    application.setJobId(
                            rs.getInt("job_id"));

                    application.setAppliedAt(
                            rs.getTimestamp("applied_at"));

                    application.setStatus(
                            rs.getString("status"));

                    application.setRemarks(
                            rs.getString("remarks"));

                    // Job information
                    application.setJobTitle(
                            rs.getString("title"));

                    application.setJobDescription(
                            rs.getString("description"));

                    application.setLocation(
                            rs.getString("location"));

                    application.setEmploymentType(
                            rs.getString("employment_type"));

                    application.setSalary(
                            rs.getDouble("salary"));

                    application.setMinCgpa(
                            rs.getDouble("min_cgpa"));

                    application.setMaxBacklogs(
                            rs.getInt("max_backlogs"));

                    application.setApplicationDeadline(
                            rs.getTimestamp("application_deadline"));

                    applications.add(application);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error loading applications.");
            e.printStackTrace();
        }

        return applications;
    }

        /**
     * Gets all applicants for jobs created by a recruiter.
     */
    public List<Application> getApplicantsByRecruiter(int recruiterId) {

        List<Application> applications = new ArrayList<>();

        String sql = """
                SELECT
                    a.application_id,
                    a.student_id,
                    a.job_id,
                    a.applied_at,
                    a.status,
                    a.remarks,

                    j.title,
                    j.location,
                    j.employment_type,
                    j.salary,

                    s.enrollment_no,
                    s.phone,
                    s.course,
                    s.branch,
                    s.semester,
                    s.cgpa,
                    s.backlogs,
                    s.graduation_year,

                    u.name,
                    u.email

                FROM applications a

                INNER JOIN jobs j
                    ON a.job_id = j.job_id

                INNER JOIN students s
                    ON a.student_id = s.student_id

                INNER JOIN users u
                    ON s.user_id = u.user_id

                WHERE j.created_by = ?

                ORDER BY a.applied_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, recruiterId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    Application application = new Application();

                    application.setApplicationId(
                            rs.getInt("application_id"));

                    application.setStudentId(
                            rs.getInt("student_id"));

                    application.setJobId(
                            rs.getInt("job_id"));

                    application.setAppliedAt(
                            rs.getTimestamp("applied_at"));

                    application.setStatus(
                            rs.getString("status"));

                    application.setRemarks(
                            rs.getString("remarks"));

                    application.setJobTitle(
                            rs.getString("title"));

                    application.setLocation(
                            rs.getString("location"));

                    application.setEmploymentType(
                            rs.getString("employment_type"));

                    application.setSalary(
                            rs.getDouble("salary"));

                    application.setEnrollmentNo(
                            rs.getString("enrollment_no"));

                    application.setPhone(
                            rs.getString("phone"));

                    application.setCourse(
                            rs.getString("course"));

                    application.setBranch(
                            rs.getString("branch"));

                    application.setSemester(
                            rs.getInt("semester"));

                    application.setCgpa(
                            rs.getDouble("cgpa"));

                    application.setBacklogs(
                            rs.getInt("backlogs"));

                    application.setGraduationYear(
                            rs.getInt("graduation_year"));

                    application.setStudentName(
                            rs.getString("name"));

                    application.setStudentEmail(
                            rs.getString("email"));

                    applications.add(application);
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Error loading recruiter applicants.");
            System.out.println("Database error: " + e.getMessage());

            e.printStackTrace();
        }

        return applications;
    }
    /**
     * Updates an application's status and remarks.
     *
     * Only allows a recruiter to update an application
     * belonging to one of their own jobs.
     */
    public boolean updateApplicationStatus(
            int applicationId,
            int recruiterId,
            String status,
            String remarks) {

        String sql = """
                UPDATE applications a
                INNER JOIN jobs j
                    ON a.job_id = j.job_id
                SET
                    a.status = ?,
                    a.remarks = ?
                WHERE a.application_id = ?
                  AND j.created_by = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setString(2, remarks);
            statement.setInt(3, applicationId);
            statement.setInt(4, recruiterId);

            int rows = statement.executeUpdate();

            if (rows == 1) {

                System.out.println();
                System.out.println(
                        "DEBUG: Application updated successfully."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Error updating application."
            );

            System.out.println(
                    "Database error: " + e.getMessage()
            );

            e.printStackTrace();
        }

        return false;
    }
   

}