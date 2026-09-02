package com.smartplacement.model;

public class EligibilityResult {

    private int jobId;
    private String jobTitle;

    private double studentCgpa;
    private double requiredCgpa;

    private int studentBacklogs;
    private int allowedBacklogs;

    private int requiredSkills;
    private int matchingSkills;

    private boolean cgpaEligible;
    private boolean backlogEligible;
    private boolean skillsEligible;
    private boolean eligible;

    public EligibilityResult() {
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getStudentCgpa() {
        return studentCgpa;
    }

    public void setStudentCgpa(double studentCgpa) {
        this.studentCgpa = studentCgpa;
    }

    public double getRequiredCgpa() {
        return requiredCgpa;
    }

    public void setRequiredCgpa(double requiredCgpa) {
        this.requiredCgpa = requiredCgpa;
    }

    public int getStudentBacklogs() {
        return studentBacklogs;
    }

    public void setStudentBacklogs(int studentBacklogs) {
        this.studentBacklogs = studentBacklogs;
    }

    public int getAllowedBacklogs() {
        return allowedBacklogs;
    }

    public void setAllowedBacklogs(int allowedBacklogs) {
        this.allowedBacklogs = allowedBacklogs;
    }

    public int getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(int requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public int getMatchingSkills() {
        return matchingSkills;
    }

    public void setMatchingSkills(int matchingSkills) {
        this.matchingSkills = matchingSkills;
    }

    public boolean isCgpaEligible() {
        return cgpaEligible;
    }

    public void setCgpaEligible(boolean cgpaEligible) {
        this.cgpaEligible = cgpaEligible;
    }

    public boolean isBacklogEligible() {
        return backlogEligible;
    }

    public void setBacklogEligible(boolean backlogEligible) {
        this.backlogEligible = backlogEligible;
    }

    public boolean isSkillsEligible() {
        return skillsEligible;
    }

    public void setSkillsEligible(boolean skillsEligible) {
        this.skillsEligible = skillsEligible;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }
}