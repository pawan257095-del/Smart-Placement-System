package com.smartplacement.model;

import java.sql.Timestamp;

public class Application {

    // =========================================================
    // APPLICATION INFORMATION
    // =========================================================

    private int applicationId;
    private int studentId;
    private int jobId;
    private Timestamp appliedAt;
    private String status;
    private String remarks;

    // =========================================================
    // JOB INFORMATION
    // =========================================================

    private String jobTitle;
    private String jobDescription;
    private String location;
    private String employmentType;
    private double salary;
    private double minCgpa;
    private int maxBacklogs;
    private Timestamp applicationDeadline;

    // =========================================================
    // STUDENT INFORMATION
    // =========================================================

    private String studentName;
    private String studentEmail;
    private String enrollmentNo;
    private String phone;
    private String course;
    private String branch;
    private int semester;
    private double cgpa;
    private int backlogs;
    private int graduationYear;

    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public Application() {
    }

    public Application(
            int applicationId,
            int studentId,
            int jobId,
            Timestamp appliedAt,
            String status,
            String remarks) {

        this.applicationId = applicationId;
        this.studentId = studentId;
        this.jobId = jobId;
        this.appliedAt = appliedAt;
        this.status = status;
        this.remarks = remarks;
    }

    // =========================================================
    // APPLICATION GETTERS / SETTERS
    // =========================================================

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Timestamp getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Timestamp appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // =========================================================
    // JOB GETTERS / SETTERS
    // =========================================================

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(double minCgpa) {
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

    public void setApplicationDeadline(
            Timestamp applicationDeadline) {

        this.applicationDeadline = applicationDeadline;
    }

    // =========================================================
    // STUDENT GETTERS / SETTERS
    // =========================================================

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(int backlogs) {
        this.backlogs = backlogs;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }
}