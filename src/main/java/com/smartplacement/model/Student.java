package com.smartplacement.model;

public class Student {

    private int studentId;
    private int userId;
    private String enrollmentNo;
    private String phone;
    private String course;
    private String branch;
    private int semester;
    private double cgpa;
    private int backlogs;
    private int graduationYear;

    public Student() {
    }

    public Student(
            int studentId,
            int userId,
            String enrollmentNo,
            String phone,
            String course,
            String branch,
            int semester,
            double cgpa,
            int backlogs,
            int graduationYear) {

        this.studentId = studentId;
        this.userId = userId;
        this.enrollmentNo = enrollmentNo;
        this.phone = phone;
        this.course = course;
        this.branch = branch;
        this.semester = semester;
        this.cgpa = cgpa;
        this.backlogs = backlogs;
        this.graduationYear = graduationYear;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public String getPhone() {
        return phone;
    }

    public String getCourse() {
        return course;
    }

    public String getBranch() {
        return branch;
    }

    public int getSemester() {
        return semester;
    }

    public double getCgpa() {
        return cgpa;
    }

    public int getBacklogs() {
        return backlogs;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public void setBacklogs(int backlogs) {
        this.backlogs = backlogs;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }
}