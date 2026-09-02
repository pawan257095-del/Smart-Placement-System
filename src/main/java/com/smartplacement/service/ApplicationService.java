package com.smartplacement.service;

import com.smartplacement.dao.ApplicationDAO;
import com.smartplacement.model.Application;
import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ApplicationService {

    private final ApplicationDAO applicationDAO;

    public ApplicationService() {
        this.applicationDAO = new ApplicationDAO();
    }

    /**
     * Applies a student for a job.
     */
    public String applyForJob(int studentId, int jobId) {

        System.out.println("DEBUG: Starting application process...");
        System.out.println("DEBUG: Student ID = " + studentId);
        System.out.println("DEBUG: Job ID = " + jobId);

        // --------------------------------------------------
        // 1. Check whether job exists and is approved
        // --------------------------------------------------

        String jobSql = """
                SELECT title,
                       status,
                       application_deadline
                FROM jobs
                WHERE job_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(jobSql)) {

            statement.setInt(1, jobId);

            try (ResultSet rs = statement.executeQuery()) {

                if (!rs.next()) {
                    return "JOB_NOT_FOUND";
                }

                String title = rs.getString("title");
                String status = rs.getString("status");

                System.out.println("DEBUG: Job = " + title);
                System.out.println("DEBUG: Job status = " + status);

                // Job must be approved
                if (!"APPROVED".equalsIgnoreCase(status)) {
                    return "JOB_NOT_AVAILABLE";
                }

                // --------------------------------------------------
                // 2. Check application deadline
                // --------------------------------------------------

                java.sql.Timestamp deadline =
                        rs.getTimestamp("application_deadline");

                if (deadline != null &&
                        deadline.before(
                                new java.sql.Timestamp(
                                        System.currentTimeMillis()))) {

                    return "DEADLINE_PASSED";
                }
            }

        } catch (SQLException e) {

            System.out.println("Error checking job.");
            e.printStackTrace();

            return "DATABASE_ERROR";
        }

        // --------------------------------------------------
        // 3. Check duplicate application
        // --------------------------------------------------

        if (applicationDAO.hasApplied(studentId, jobId)) {

            System.out.println(
                    "DEBUG: Student has already applied for this job.");

            return "ALREADY_APPLIED";
        }

        // --------------------------------------------------
        // 4. Check eligibility
        // --------------------------------------------------

        EligibilityData eligibility =
                checkEligibility(studentId, jobId);

        if (!eligibility.jobExists) {
            return "JOB_NOT_FOUND";
        }

        if (!eligibility.cgpaPassed) {
            return "CGPA_NOT_ELIGIBLE";
        }

        if (!eligibility.backlogPassed) {
            return "BACKLOG_NOT_ELIGIBLE";
        }

        if (!eligibility.skillsPassed) {
            return "SKILLS_NOT_ELIGIBLE";
        }

        // --------------------------------------------------
        // 5. Insert application
        // --------------------------------------------------

        boolean created =
                applicationDAO.createApplication(
                        studentId,
                        jobId);

        if (created) {

            System.out.println(
                    "DEBUG: Application inserted successfully.");

            return "SUCCESS";
        }

        return "DATABASE_ERROR";
    }

    /**
     * Gets all applications of a student.
     */
    public List<Application> getApplicationsByStudent(int studentId) {

        return applicationDAO.getApplicationsByStudent(studentId);
    }

    /**
     * Checks student eligibility using:
     *
     * CGPA
     * Backlogs
     * Skills
     */
    private EligibilityData checkEligibility(
            int studentId,
            int jobId) {

        EligibilityData result = new EligibilityData();

        String studentJobSql = """
                SELECT
                    s.cgpa,
                    s.backlogs,
                    j.min_cgpa,
                    j.max_backlogs
                FROM students s
                CROSS JOIN jobs j
                WHERE s.student_id = ?
                  AND j.job_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(studentJobSql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, jobId);

            try (ResultSet rs = statement.executeQuery()) {

                if (!rs.next()) {
                    return result;
                }

                result.jobExists = true;

                double studentCgpa =
                        rs.getDouble("cgpa");

                double requiredCgpa =
                        rs.getDouble("min_cgpa");

                int studentBacklogs =
                        rs.getInt("backlogs");

                int allowedBacklogs =
                        rs.getInt("max_backlogs");

                result.cgpaPassed =
                        studentCgpa >= requiredCgpa;

                result.backlogPassed =
                        studentBacklogs <= allowedBacklogs;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error checking academic eligibility.");

            e.printStackTrace();

            return result;
        }

        // --------------------------------------------------
        // Skill eligibility
        // --------------------------------------------------

        String skillSql = """
                SELECT
                    COUNT(js.skill_id) AS required_skills,

                    SUM(
                        CASE
                            WHEN
                                CASE ss.proficiency
                                    WHEN 'BEGINNER' THEN 1
                                    WHEN 'INTERMEDIATE' THEN 2
                                    WHEN 'ADVANCED' THEN 3
                                    ELSE 0
                                END
                                >=
                                CASE js.required_level
                                    WHEN 'BEGINNER' THEN 1
                                    WHEN 'INTERMEDIATE' THEN 2
                                    WHEN 'ADVANCED' THEN 3
                                    ELSE 0
                                END
                            THEN 1
                            ELSE 0
                        END
                    ) AS matching_skills

                FROM job_skills js

                LEFT JOIN student_skills ss
                    ON ss.skill_id = js.skill_id
                   AND ss.student_id = ?

                WHERE js.job_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(skillSql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, jobId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    int requiredSkills =
                            rs.getInt("required_skills");

                    int matchingSkills =
                            rs.getInt("matching_skills");

                    // If job has no required skills,
                    // automatically pass.
                    if (requiredSkills == 0) {

                        result.skillsPassed = true;

                    } else {

                        result.skillsPassed =
                                matchingSkills == requiredSkills;
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error checking skill eligibility.");

            e.printStackTrace();

            return result;
        }

        return result;
    }

    /**
     * Internal eligibility result.
     */
    private static class EligibilityData {

        boolean jobExists = false;
        boolean cgpaPassed = false;
        boolean backlogPassed = false;
        boolean skillsPassed = false;
    }
}