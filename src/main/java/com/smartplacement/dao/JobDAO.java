package com.smartplacement.dao;

import com.smartplacement.model.Job;
import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Statement;

public class JobDAO {

    public List<Job> findApprovedJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = """
                SELECT
                    job_id,
                    company_id,
                    created_by,
                    title,
                    description,
                    location,
                    employment_type,
                    salary,
                    min_cgpa,
                    max_backlogs,
                    application_deadline,
                    status,
                    created_at
                FROM jobs
                WHERE status = 'APPROVED'
                AND (
                    application_deadline IS NULL
                    OR application_deadline >= CURRENT_TIMESTAMP
                )
                ORDER BY application_deadline ASC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Job job = new Job();

                job.setJobId(
                        resultSet.getInt("job_id")
                );

                job.setCompanyId(
                        resultSet.getInt("company_id")
                );

                job.setCreatedBy(
                        resultSet.getInt("created_by")
                );

                job.setTitle(
                        resultSet.getString("title")
                );

                job.setDescription(
                        resultSet.getString("description")
                );

                job.setLocation(
                        resultSet.getString("location")
                );

                job.setEmploymentType(
                        resultSet.getString("employment_type")
                );

                job.setSalary(
                        resultSet.getBigDecimal("salary")
                );

                job.setMinCgpa(
                        resultSet.getBigDecimal("min_cgpa")
                );

                job.setMaxBacklogs(
                        resultSet.getInt("max_backlogs")
                );

                job.setApplicationDeadline(
                        resultSet.getTimestamp(
                                "application_deadline"
                        )
                );

                job.setStatus(
                        resultSet.getString("status")
                );

                job.setCreatedAt(
                        resultSet.getTimestamp("created_at")
                );

                jobs.add(job);
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("Unable to load available jobs.");
            System.out.println("Database error: " + e.getMessage());
        }

        return jobs;
    }

    public Job findById(int jobId) {

        String sql = """
                SELECT
                    job_id,
                    company_id,
                    created_by,
                    title,
                    description,
                    location,
                    employment_type,
                    salary,
                    min_cgpa,
                    max_backlogs,
                    application_deadline,
                    status,
                    created_at
                FROM jobs
                WHERE job_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, jobId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Job job = new Job();

                    job.setJobId(
                            resultSet.getInt("job_id")
                    );

                    job.setCompanyId(
                            resultSet.getInt("company_id")
                    );

                    job.setCreatedBy(
                            resultSet.getInt("created_by")
                    );

                    job.setTitle(
                            resultSet.getString("title")
                    );

                    job.setDescription(
                            resultSet.getString("description")
                    );

                    job.setLocation(
                            resultSet.getString("location")
                    );

                    job.setEmploymentType(
                            resultSet.getString("employment_type")
                    );

                    job.setSalary(
                            resultSet.getBigDecimal("salary")
                    );

                    job.setMinCgpa(
                            resultSet.getBigDecimal("min_cgpa")
                    );

                    job.setMaxBacklogs(
                            resultSet.getInt("max_backlogs")
                    );

                    job.setApplicationDeadline(
                            resultSet.getTimestamp(
                                    "application_deadline"
                            )
                    );

                    job.setStatus(
                            resultSet.getString("status")
                    );

                    job.setCreatedAt(
                            resultSet.getTimestamp("created_at")
                    );

                    return job;
                }
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("Unable to load job details.");
            System.out.println("Database error: " + e.getMessage());
        }

        return null;
    }

    


    // =========================================================
    // GET RECRUITER DETAILS FROM USER ID
    // =========================================================

    public int[] getRecruiterDetails(int userId) {

        String sql = """
                SELECT recruiter_id, company_id
                FROM recruiters
                WHERE user_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    int recruiterId =
                            rs.getInt("recruiter_id");

                    int companyId =
                            rs.getInt("company_id");

                    System.out.println(
                            "DEBUG: User ID = " + userId
                    );

                    System.out.println(
                            "DEBUG: Recruiter ID = " + recruiterId
                    );

                    System.out.println(
                            "DEBUG: Company ID = " + companyId
                    );

                    return new int[]{
                            recruiterId,
                            companyId
                    };
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to find recruiter details."
            );

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return null;
    }


    // =========================================================
    // CREATE JOB
    // =========================================================

    public int createJob(
            int companyId,
            int recruiterId,
            String title,
            String description,
            String location,
            String employmentType,
            java.math.BigDecimal salary,
            java.math.BigDecimal minCgpa,
            int maxBacklogs,
            java.sql.Timestamp applicationDeadline
    ) {

        String sql = """
                INSERT INTO jobs
                (
                    company_id,
                    created_by,
                    title,
                    description,
                    location,
                    employment_type,
                    salary,
                    min_cgpa,
                    max_backlogs,
                    application_deadline,
                    status
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'PENDING'
                )
                """;

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(1, companyId);
            statement.setInt(2, recruiterId);
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.setString(6, employmentType);
            statement.setBigDecimal(7, salary);
            statement.setBigDecimal(8, minCgpa);
            statement.setInt(9, maxBacklogs);
            statement.setTimestamp(10, applicationDeadline);

            int rows = statement.executeUpdate();

            if (rows == 1) {

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (keys.next()) {

                        int jobId = keys.getInt(1);

                        System.out.println(
                                "DEBUG: Job created successfully."
                        );

                        System.out.println(
                                "DEBUG: Job ID = " + jobId
                        );

                        System.out.println(
                                "DEBUG: Recruiter ID = "
                                        + recruiterId
                        );

                        System.out.println(
                                "DEBUG: Company ID = "
                                        + companyId
                        );

                        return jobId;
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Unable to create job.");
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return -1;
    }

        // =========================================================
    // FIND JOBS CREATED BY RECRUITER
    // =========================================================

    public List<Job> findJobsByRecruiterId(int recruiterId) {

        List<Job> jobs = new ArrayList<>();

        String sql = """
                SELECT
                    job_id,
                    company_id,
                    created_by,
                    title,
                    description,
                    location,
                    employment_type,
                    salary,
                    min_cgpa,
                    max_backlogs,
                    application_deadline,
                    status,
                    created_at
                FROM jobs
                WHERE created_by = ?
                ORDER BY created_at DESC
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, recruiterId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Job job = new Job();

                    job.setJobId(
                            resultSet.getInt("job_id")
                    );

                    job.setCompanyId(
                            resultSet.getInt("company_id")
                    );

                    job.setCreatedBy(
                            resultSet.getInt("created_by")
                    );

                    job.setTitle(
                            resultSet.getString("title")
                    );

                    job.setDescription(
                            resultSet.getString("description")
                    );

                    job.setLocation(
                            resultSet.getString("location")
                    );

                    job.setEmploymentType(
                            resultSet.getString("employment_type")
                    );

                    job.setSalary(
                            resultSet.getBigDecimal("salary")
                    );

                    job.setMinCgpa(
                            resultSet.getBigDecimal("min_cgpa")
                    );

                    job.setMaxBacklogs(
                            resultSet.getInt("max_backlogs")
                    );

                    job.setApplicationDeadline(
                            resultSet.getTimestamp(
                                    "application_deadline"
                            )
                    );

                    job.setStatus(
                            resultSet.getString("status")
                    );

                    job.setCreatedAt(
                            resultSet.getTimestamp("created_at")
                    );

                    jobs.add(job);
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Unable to load recruiter jobs.");
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return jobs;
    }

    // =========================================================
// FIND ALL PENDING JOBS
// =========================================================

public List<Job> findPendingJobs() {

    List<Job> jobs = new ArrayList<>();

    String sql = """
            SELECT
                job_id,
                company_id,
                created_by,
                title,
                description,
                location,
                employment_type,
                salary,
                min_cgpa,
                max_backlogs,
                application_deadline,
                status,
                created_at
            FROM jobs
            WHERE status = 'PENDING'
            ORDER BY created_at ASC
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            ResultSet resultSet =
                    statement.executeQuery()
    ) {

        while (resultSet.next()) {

            Job job = new Job();

            job.setJobId(
                    resultSet.getInt("job_id")
            );

            job.setCompanyId(
                    resultSet.getInt("company_id")
            );

            job.setCreatedBy(
                    resultSet.getInt("created_by")
            );

            job.setTitle(
                    resultSet.getString("title")
            );

            job.setDescription(
                    resultSet.getString("description")
            );

            job.setLocation(
                    resultSet.getString("location")
            );

            job.setEmploymentType(
                    resultSet.getString("employment_type")
            );

            job.setSalary(
                    resultSet.getBigDecimal("salary")
            );

            job.setMinCgpa(
                    resultSet.getBigDecimal("min_cgpa")
            );

            job.setMaxBacklogs(
                    resultSet.getInt("max_backlogs")
            );

            job.setApplicationDeadline(
                    resultSet.getTimestamp(
                            "application_deadline"
                    )
            );

            job.setStatus(
                    resultSet.getString("status")
            );

            job.setCreatedAt(
                    resultSet.getTimestamp("created_at")
            );

            jobs.add(job);
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println("Unable to load pending jobs.");
        System.out.println(
                "Database error: " + e.getMessage()
        );
    }

    return jobs;
}


// =========================================================
// UPDATE JOB STATUS
// =========================================================

public boolean updateJobStatus(
        int jobId,
        String newStatus) {

    String sql = """
            UPDATE jobs
            SET status = ?
            WHERE job_id = ?
              AND status = 'PENDING'
            """;

    try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setString(1, newStatus);
        statement.setInt(2, jobId);

        int rows = statement.executeUpdate();

        return rows == 1;

    } catch (SQLException e) {

        System.out.println();
        System.out.println("Unable to update job status.");
        System.out.println(
                "Database error: " + e.getMessage()
        );
    }

    return false;
}
}