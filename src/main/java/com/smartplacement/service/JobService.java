package com.smartplacement.service;

import com.smartplacement.dao.JobDAO;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class JobService {

    private final JobDAO jobDAO;

    public JobService() {
        this.jobDAO = new JobDAO();
    }

    public int createJob(
            int companyId,
            int createdBy,
            String title,
            String description,
            String location,
            String employmentType,
            BigDecimal salary,
            BigDecimal minCgpa,
            int maxBacklogs,
            Timestamp applicationDeadline
    ) {

        // -------------------------------
        // Validation
        // -------------------------------

        if (title == null || title.isBlank()) {
            return -2;
        }

        if (description == null || description.isBlank()) {
            return -3;
        }

        if (location == null || location.isBlank()) {
            return -4;
        }

        if (salary != null && salary.compareTo(BigDecimal.ZERO) < 0) {
            return -5;
        }

        if (minCgpa != null) {

            if (minCgpa.compareTo(BigDecimal.ZERO) < 0 ||
                minCgpa.compareTo(new BigDecimal("10.00")) > 0) {

                return -6;
            }
        }

        if (maxBacklogs < 0) {
            return -7;
        }

        if (applicationDeadline != null &&
            applicationDeadline.before(
                    new Timestamp(System.currentTimeMillis()))) {

            return -8;
        }

        // -------------------------------
        // Create job
        // -------------------------------

        return jobDAO.createJob(
                companyId,
                createdBy,
                title.trim(),
                description.trim(),
                location.trim(),
                employmentType,
                salary,
                minCgpa,
                maxBacklogs,
                applicationDeadline
        );
    }
}