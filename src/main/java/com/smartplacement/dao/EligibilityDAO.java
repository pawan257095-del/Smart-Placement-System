package com.smartplacement.dao;

import com.smartplacement.model.EligibilityResult;
import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EligibilityDAO {

    public EligibilityResult checkEligibility(int userId, int jobId) {

        String sql = """
                SELECT
                    j.job_id,
                    j.title,
                    s.cgpa AS student_cgpa,
                    j.min_cgpa,
                    s.backlogs AS student_backlogs,
                    j.max_backlogs,

                    COUNT(DISTINCT js.skill_id) AS required_skills,

                    COUNT(
                        DISTINCT CASE
                            WHEN ss.skill_id IS NOT NULL
                            THEN js.skill_id
                        END
                    ) AS matching_skills

                FROM jobs j

                JOIN students s
                    ON s.user_id = ?

                LEFT JOIN job_skills js
                    ON js.job_id = j.job_id

                LEFT JOIN student_skills ss
                    ON ss.student_id = s.student_id
                    AND ss.skill_id = js.skill_id

                WHERE j.job_id = ?
                  AND j.status = 'APPROVED'
                  AND (
                        j.application_deadline IS NULL
                        OR j.application_deadline >= CURRENT_TIMESTAMP
                      )

                GROUP BY
                    j.job_id,
                    j.title,
                    s.cgpa,
                    j.min_cgpa,
                    s.backlogs,
                    j.max_backlogs
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, jobId);

            System.out.println("DEBUG: Checking eligibility...");
            System.out.println("DEBUG: User ID = " + userId);
            System.out.println("DEBUG: Job ID = " + jobId);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    EligibilityResult result = new EligibilityResult();

                    result.setJobId(rs.getInt("job_id"));
                    result.setJobTitle(rs.getString("title"));

                    result.setStudentCgpa(
                            rs.getDouble("student_cgpa")
                    );

                    result.setRequiredCgpa(
                            rs.getDouble("min_cgpa")
                    );

                    result.setStudentBacklogs(
                            rs.getInt("student_backlogs")
                    );

                    result.setAllowedBacklogs(
                            rs.getInt("max_backlogs")
                    );

                    result.setRequiredSkills(
                            rs.getInt("required_skills")
                    );

                    result.setMatchingSkills(
                            rs.getInt("matching_skills")
                    );

                    boolean cgpaEligible =
                            result.getStudentCgpa()
                                    >= result.getRequiredCgpa();

                    boolean backlogEligible =
                            result.getStudentBacklogs()
                                    <= result.getAllowedBacklogs();

                    boolean skillsEligible =
                            result.getMatchingSkills()
                                    >= result.getRequiredSkills();

                    boolean eligible =
                            cgpaEligible
                            && backlogEligible
                            && skillsEligible;

                    result.setCgpaEligible(cgpaEligible);
                    result.setBacklogEligible(backlogEligible);
                    result.setSkillsEligible(skillsEligible);
                    result.setEligible(eligible);

                    System.out.println(
                            "DEBUG: Eligibility calculated successfully."
                    );

                    return result;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "ERROR: Could not check eligibility."
            );

            e.printStackTrace();
        }

        return null;
    }
}