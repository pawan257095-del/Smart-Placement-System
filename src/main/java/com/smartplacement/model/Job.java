package com.smartplacement.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Job {

    private int jobId;
    private int companyId;
    private int createdBy;

    private String title;
    private String description;
    private String location;
    private String employmentType;

    private BigDecimal salary;
    private BigDecimal minCgpa;

    private int maxBacklogs;

    private Timestamp applicationDeadline;
    private String status;
    private Timestamp createdAt;

    public Job() {
    }

    public Job(
            int jobId,
            int companyId,
            int createdBy,
            String title,
            String description,
            String location,
            String employmentType,
            BigDecimal salary,
            BigDecimal minCgpa,
            int maxBacklogs,
            Timestamp applicationDeadline,
            String status,
            Timestamp createdAt
    ) {
        this.jobId = jobId;
        this.companyId = companyId;
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.salary = salary;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs;
        this.applicationDeadline = applicationDeadline;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(BigDecimal minCgpa) {
        this.minCgpa = minCgpa;
    }

    public int getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(int maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public Timestamp getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(Timestamp applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}