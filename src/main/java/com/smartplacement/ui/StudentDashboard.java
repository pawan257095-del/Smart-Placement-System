package com.smartplacement.ui;

import com.smartplacement.dao.ApplicationDAO;
import com.smartplacement.model.Application;
import com.smartplacement.model.Student;
import com.smartplacement.model.User;
import com.smartplacement.service.ApplicationService;
import com.smartplacement.service.StudentService;
import java.util.List;
import java.util.Scanner;

public class StudentDashboard {

    private final User user;
    private final Scanner scanner;

    private final StudentService studentService;
    private final ApplicationService applicationService;
    private final ApplicationDAO applicationDAO;

    public StudentDashboard(User user, Scanner scanner) {

        this.user = user;
        this.scanner = scanner;

        this.studentService = new StudentService();
        this.applicationService = new ApplicationService();
        this.applicationDAO = new ApplicationDAO();
    }

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("              STUDENT DASHBOARD");
            System.out.println("==================================================");

            System.out.println();
            System.out.println("Welcome, " + user.getName());
            System.out.println("Email: " + user.getEmail());

            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println("                     MENU");
            System.out.println("--------------------------------------------------");

            System.out.println("1. My Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View Available Jobs");
            System.out.println("4. Check Eligibility");
            System.out.println("5. Apply for Job");
            System.out.println("6. My Applications");
            System.out.println("7. Logout");

            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

             case "1":
                showProfile();
                pause();
                break;

             case "2":
                editProfile();
                break;

             case "3":
                showAvailableJobs();
                break;

             case "4":
                checkEligibility();
                break;

             case "5":
                applyForJob();
                break;

             case "6":
                showMyApplications();
                break;

             case "7":

                System.out.println();
                System.out.println("Logging out...");
                System.out.println("Goodbye, " + user.getName() + "!");

                running = false;
                break;

                default:

                    System.out.println();
                    System.out.println(
                            "Invalid choice. Please enter 1-7.");

                    pause();
            }
        }
    }

    // =========================================================
    // MY PROFILE
    // =========================================================

    private void showProfile() {

        Student student =
                studentService.getStudentProfile(
                        user.getUserId());

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                  MY PROFILE");
        System.out.println("==================================================");

        if (student == null) {

            System.out.println();
            System.out.println("Student profile not found.");
            System.out.println("==================================================");

            return;
        }

        System.out.println();
        System.out.println("Personal Information");
        System.out.println("--------------------");

        System.out.println(
                "Name             : " + user.getName());

        System.out.println(
                "Email            : " + user.getEmail());

        System.out.println(
                "Phone            : " + student.getPhone());

        System.out.println();

        System.out.println("Academic Information");
        System.out.println("--------------------");

        System.out.println(
                "Enrollment No.   : "
                        + student.getEnrollmentNo());

        System.out.println(
                "Course           : "
                        + student.getCourse());

        System.out.println(
                "Branch           : "
                        + student.getBranch());

        System.out.println(
                "Semester         : "
                        + student.getSemester());

        System.out.println(
                "CGPA             : "
                        + student.getCgpa());

        System.out.println(
                "Backlogs         : "
                        + student.getBacklogs());

        System.out.println(
                "Graduation Year  : "
                        + student.getGraduationYear());

        System.out.println();
        System.out.println("==================================================");
    }

    // =========================================================
// EDIT PROFILE
// =========================================================

private void editProfile() {

    Student student =
            studentService.getStudentProfile(
                    user.getUserId()
            );

    System.out.println();
    System.out.println(
            "=================================================="
    );

    System.out.println(
            "                 EDIT PROFILE"
    );

    System.out.println(
            "=================================================="
    );

    if (student == null) {

        System.out.println();
        System.out.println(
                "Student profile not found."
        );

        System.out.println(
                "=================================================="
        );

        pause();
        return;
    }

    System.out.println();
    System.out.println(
            "Press ENTER to keep the current value."
    );

    System.out.println();

    // ---------------------------------------------------------
    // Phone
    // ---------------------------------------------------------

    System.out.println(
            "Current Phone       : "
                    + student.getPhone()
    );

    System.out.print(
            "New Phone           : "
    );

    String phoneInput =
            scanner.nextLine().trim();

    String phone =
            phoneInput.isBlank()
                    ? student.getPhone()
                    : phoneInput;

    // ---------------------------------------------------------
    // Course
    // ---------------------------------------------------------

    System.out.println();
    System.out.println(
            "Current Course      : "
                    + student.getCourse()
    );

    System.out.print(
            "New Course          : "
    );

    String courseInput =
            scanner.nextLine().trim();

    String course =
            courseInput.isBlank()
                    ? student.getCourse()
                    : courseInput;

    // ---------------------------------------------------------
    // Branch
    // ---------------------------------------------------------

    System.out.println();
    System.out.println(
            "Current Branch      : "
                    + student.getBranch()
    );

    System.out.print(
            "New Branch          : "
    );

    String branchInput =
            scanner.nextLine().trim();

    String branch =
            branchInput.isBlank()
                    ? student.getBranch()
                    : branchInput;

    // ---------------------------------------------------------
    // Semester
    // ---------------------------------------------------------

    int semester =
            student.getSemester();

    while (true) {

        System.out.println();
        System.out.println(
                "Current Semester    : "
                        + student.getSemester()
        );

        System.out.print(
                "New Semester        : "
        );

        String semesterInput =
                scanner.nextLine().trim();

        if (semesterInput.isBlank()) {
            break;
        }

        try {

            int newSemester =
                    Integer.parseInt(
                            semesterInput
                    );

            if (newSemester < 1 ||
                    newSemester > 8) {

                System.out.println(
                        "Semester must be between 1 and 8."
                );

                continue;
            }

            semester = newSemester;
            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid semester. Please enter a number."
            );
        }
    }

    // ---------------------------------------------------------
    // CGPA
    // ---------------------------------------------------------

    double cgpa =
            student.getCgpa();

    while (true) {

        System.out.println();
        System.out.printf(
                "Current CGPA        : %.2f%n",
                student.getCgpa()
        );

        System.out.print(
                "New CGPA            : "
        );

        String cgpaInput =
                scanner.nextLine().trim();

        if (cgpaInput.isBlank()) {
            break;
        }

        try {

            double newCgpa =
                    Double.parseDouble(
                            cgpaInput
                    );

            if (newCgpa < 0 ||
                    newCgpa > 10) {

                System.out.println(
                        "CGPA must be between 0 and 10."
                );

                continue;
            }

            cgpa = newCgpa;
            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid CGPA. Please enter a number."
            );
        }
    }

    // ---------------------------------------------------------
    // Backlogs
    // ---------------------------------------------------------

    int backlogs =
            student.getBacklogs();

    while (true) {

        System.out.println();
        System.out.println(
                "Current Backlogs    : "
                        + student.getBacklogs()
        );

        System.out.print(
                "New Backlogs        : "
        );

        String backlogInput =
                scanner.nextLine().trim();

        if (backlogInput.isBlank()) {
            break;
        }

        try {

            int newBacklogs =
                    Integer.parseInt(
                            backlogInput
                    );

            if (newBacklogs < 0) {

                System.out.println(
                        "Backlogs cannot be negative."
                );

                continue;
            }

            backlogs = newBacklogs;
            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid number of backlogs."
            );
        }
    }

    // ---------------------------------------------------------
    // Graduation Year
    // ---------------------------------------------------------

    int graduationYear =
            student.getGraduationYear();

    while (true) {

        System.out.println();
        System.out.println(
                "Current Graduation Year : "
                        + student.getGraduationYear()
        );

        System.out.print(
                "New Graduation Year    : "
        );

        String yearInput =
                scanner.nextLine().trim();

        if (yearInput.isBlank()) {
            break;
        }

        try {

            int newYear =
                    Integer.parseInt(
                            yearInput
                    );

            if (newYear < 2000 ||
                    newYear > 2100) {

                System.out.println(
                        "Please enter a valid graduation year."
                );

                continue;
            }

            graduationYear = newYear;
            break;

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid graduation year."
            );
        }
    }

    // ---------------------------------------------------------
    // Preview
    // ---------------------------------------------------------

    System.out.println();
    System.out.println(
            "=================================================="
    );

    System.out.println(
            "                 UPDATE PREVIEW"
    );

    System.out.println(
            "=================================================="
    );

    System.out.println();

    System.out.println(
            "Phone             : " + phone
    );

    System.out.println(
            "Course            : " + course
    );

    System.out.println(
            "Branch            : " + branch
    );

    System.out.println(
            "Semester          : " + semester
    );

    System.out.printf(
            "CGPA              : %.2f%n",
            cgpa
    );

    System.out.println(
            "Backlogs          : " + backlogs
    );

    System.out.println(
            "Graduation Year   : " + graduationYear
    );

    System.out.println();

    System.out.print(
            "Save these changes? (Y/N): "
    );

    String confirmation =
            scanner.nextLine().trim();

    if (!"Y".equalsIgnoreCase(
            confirmation
    )) {

        System.out.println();
        System.out.println(
                "Profile update cancelled."
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // Update database
    // ---------------------------------------------------------

    boolean updated =
            studentService.updateStudentProfile(
                    user.getUserId(),
                    phone,
                    course,
                    branch,
                    semester,
                    cgpa,
                    backlogs,
                    graduationYear
            );

    System.out.println();

    if (updated) {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "          PROFILE UPDATED SUCCESSFULLY"
        );

        System.out.println(
                "=================================================="
        );

    } else {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "           PROFILE UPDATE FAILED"
        );

        System.out.println(
                "=================================================="
        );
    }

    pause();
}

    // =========================================================
    // AVAILABLE JOBS
    // =========================================================

    private void showAvailableJobs() {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                    AVAILABLE JOBS");
        System.out.println("==============================================================");

        String sql = """
                SELECT job_id,
                       title,
                       location,
                       salary
                FROM jobs
                WHERE status = 'APPROVED'
                  AND (
                        application_deadline IS NULL
                        OR application_deadline >= NOW()
                      )
                ORDER BY job_id
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql);
             java.sql.ResultSet rs =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.printf(
                    "%-6s %-35s %-18s %-15s%n",
                    "ID",
                    "POSITION",
                    "LOCATION",
                    "SALARY");

            System.out.println(
                    "--------------------------------------------------------------------------");

            while (rs.next()) {

                found = true;

                int jobId =
                        rs.getInt("job_id");

                String title =
                        rs.getString("title");

                String location =
                        rs.getString("location");

                java.math.BigDecimal salary =
                        rs.getBigDecimal("salary");

                System.out.printf(
                        "%-6d %-35s %-18s Rs. %-11s%n",
                        jobId,
                        title,
                        location,
                        salary == null ? "N/A" : salary);
            }

            if (!found) {

                System.out.println();
                System.out.println(
                        "No jobs are currently available.");
                pause();
                return;
            }

        } catch (java.sql.SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to load available jobs.");

            e.printStackTrace();

            pause();
            return;
        }

        System.out.println();
        System.out.print(
                "Enter a Job ID to view complete details "
                        + "(0 to return): ");

        String input = scanner.nextLine().trim();

        if ("0".equals(input)) {
            return;
        }

        try {

            int jobId = Integer.parseInt(input);

            showJobDetails(jobId);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Job ID.");

            pause();
        }
    }

    // =========================================================
    // JOB DETAILS
    // =========================================================

    private void showJobDetails(int jobId) {

        String sql = """
                SELECT job_id,
                       title,
                       description,
                       location,
                       employment_type,
                       salary,
                       min_cgpa,
                       max_backlogs,
                       application_deadline,
                       status
                FROM jobs
                WHERE job_id = ?
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);

            try (java.sql.ResultSet rs =
                         statement.executeQuery()) {

                if (!rs.next()) {

                    System.out.println();
                    System.out.println(
                            "Job not found.");

                    pause();
                    return;
                }

                System.out.println();
                System.out.println("==============================================================");
                System.out.println("                       JOB DETAILS");
                System.out.println("==============================================================");

                System.out.println();
                System.out.println(
                        "Job ID               : "
                                + rs.getInt("job_id"));

                System.out.println(
                        "Position             : "
                                + rs.getString("title"));

                System.out.println(
                        "Location             : "
                                + rs.getString("location"));

                System.out.println(
                        "Employment Type      : "
                                + rs.getString("employment_type"));

                System.out.println(
                        "Salary               : Rs."
                                + rs.getBigDecimal("salary"));

                System.out.println(
                        "Minimum CGPA         : "
                                + rs.getBigDecimal("min_cgpa"));

                System.out.println(
                        "Maximum Backlogs     : "
                                + rs.getInt("max_backlogs"));

                System.out.println(
                        "Application Deadline : "
                                + rs.getTimestamp(
                                        "application_deadline"));

                System.out.println(
                        "Status               : "
                                + rs.getString("status"));

                System.out.println();
                System.out.println(
                        "--------------------------------------------------------------");

                System.out.println("DESCRIPTION");

                System.out.println(
                        "--------------------------------------------------------------");

                String description =
                        rs.getString("description");

                System.out.println(
                        description == null
                                ? "No description available."
                                : description);

                System.out.println();
                System.out.println(
                        "==============================================================");
            }

        } catch (java.sql.SQLException e) {

            System.out.println(
                    "Unable to load job details.");

            e.printStackTrace();
        }

        pause();
    }

    // =========================================================
    // CHECK ELIGIBILITY
    // =========================================================

    private void checkEligibility() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                CHECK ELIGIBILITY");
        System.out.println("==================================================");

        System.out.println();
        System.out.println("Available Jobs:");

        String sql = """
                SELECT job_id,
                       title,
                       location
                FROM jobs
                WHERE status = 'APPROVED'
                  AND (
                        application_deadline IS NULL
                        OR application_deadline >= NOW()
                      )
                ORDER BY job_id
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql);
             java.sql.ResultSet rs =
                     statement.executeQuery()) {

            System.out.printf(
                    "%-8s %-35s %-20s%n",
                    "JOB ID",
                    "POSITION",
                    "LOCATION");

            System.out.println(
                    "--------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-8d %-35s %-20s%n",
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("location"));
            }

        } catch (java.sql.SQLException e) {

            System.out.println(
                    "Unable to load jobs.");

            e.printStackTrace();

            pause();
            return;
        }

        System.out.println();
        System.out.print("Enter Job ID: ");

        String input = scanner.nextLine().trim();

        int jobId;

        try {

            jobId = Integer.parseInt(input);

        } catch (NumberFormatException e) {

            System.out.println("Invalid Job ID.");
            pause();
            return;
        }

        performEligibilityCheck(jobId);
    }

    private void performEligibilityCheck(int jobId) {

        System.out.println();
        System.out.println("Checking eligibility...");

        String sql = """
                SELECT
                    s.cgpa AS student_cgpa,
                    s.backlogs AS student_backlogs,
                    j.title,
                    j.min_cgpa,
                    j.max_backlogs
                FROM students s
                JOIN jobs j
                WHERE s.user_id = ?
                  AND j.job_id = ?
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, user.getUserId());
            statement.setInt(2, jobId);

            try (java.sql.ResultSet rs =
                         statement.executeQuery()) {

                if (!rs.next()) {

                    System.out.println();
                    System.out.println(
                            "Student or job not found.");

                    pause();
                    return;
                }

                double studentCgpa =
                        rs.getDouble("student_cgpa");

                double requiredCgpa =
                        rs.getDouble("min_cgpa");

                int studentBacklogs =
                        rs.getInt("student_backlogs");

                int allowedBacklogs =
                        rs.getInt("max_backlogs");

                String title =
                        rs.getString("title");

                boolean cgpaPassed =
                        studentCgpa >= requiredCgpa;

                boolean backlogPassed =
                        studentBacklogs <= allowedBacklogs;

                int requiredSkills =
                        getRequiredSkillCount(jobId);

                int matchingSkills =
                        getMatchingSkillCount(
                                getStudentId(),
                                jobId);

                boolean skillsPassed =
                        requiredSkills == 0
                                || matchingSkills == requiredSkills;

                boolean eligible =
                        cgpaPassed
                                && backlogPassed
                                && skillsPassed;

                System.out.println();
                System.out.println("==================================================");
                System.out.println("              ELIGIBILITY RESULT");
                System.out.println("==================================================");

                System.out.println();
                System.out.println(
                        "Job ID             : " + jobId);

                System.out.println(
                        "Job                : " + title);

                System.out.println();
                System.out.println("Academic Criteria");
                System.out.println("-----------------");

                System.out.printf(
                        "Student CGPA       : %.2f%n",
                        studentCgpa);

                System.out.printf(
                        "Required CGPA      : %.2f%n",
                        requiredCgpa);

                System.out.println(
                        "CGPA Requirement   : "
                                + (cgpaPassed
                                ? "PASSED"
                                : "FAILED"));

                System.out.println();

                System.out.println(
                        "Student Backlogs   : "
                                + studentBacklogs);

                System.out.println(
                        "Allowed Backlogs   : "
                                + allowedBacklogs);

                System.out.println(
                        "Backlog Requirement: "
                                + (backlogPassed
                                ? "PASSED"
                                : "FAILED"));

                System.out.println();
                System.out.println("Skills Criteria");
                System.out.println("---------------");

                System.out.println(
                        "Required Skills    : "
                                + requiredSkills);

                System.out.println(
                        "Matching Skills    : "
                                + matchingSkills);

                System.out.println(
                        "Skills Requirement : "
                                + (skillsPassed
                                ? "PASSED"
                                : "FAILED"));

                System.out.println();
                System.out.println(
                        "--------------------------------------------------");

                if (eligible) {

                    System.out.println(
                            "ELIGIBILITY STATUS : ELIGIBLE");

                    System.out.println();
                    System.out.println(
                            "You are eligible to apply for this job.");

                } else {

                    System.out.println(
                            "ELIGIBILITY STATUS : NOT ELIGIBLE");

                    System.out.println();
                    System.out.println(
                            "You are not eligible to apply for this job.");
                }

                System.out.println();
                System.out.println(
                        "==================================================");
            }

        } catch (java.sql.SQLException e) {

            System.out.println(
                    "Error checking eligibility.");

            e.printStackTrace();
        }

        pause();
    }

    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    private void applyForJob() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                 APPLY FOR JOB");
        System.out.println("==================================================");

        System.out.println();
        System.out.println("Available Jobs:");

        String sql = """
                SELECT job_id,
                       title,
                       location
                FROM jobs
                WHERE status = 'APPROVED'
                  AND (
                        application_deadline IS NULL
                        OR application_deadline >= NOW()
                      )
                ORDER BY job_id
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql);
             java.sql.ResultSet rs =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.printf(
                    "%-8s %-35s %-20s%n",
                    "JOB ID",
                    "POSITION",
                    "LOCATION");

            System.out.println(
                    "--------------------------------------------------------------");

            while (rs.next()) {

                found = true;

                System.out.printf(
                        "%-8d %-35s %-20s%n",
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("location"));
            }

            if (!found) {

                System.out.println();
                System.out.println(
                        "No jobs are currently available.");

                pause();
                return;
            }

        } catch (java.sql.SQLException e) {

            System.out.println(
                    "Unable to load available jobs.");

            e.printStackTrace();

            pause();
            return;
        }

        System.out.println();
        System.out.print("Enter Job ID to apply: ");

        String input = scanner.nextLine().trim();

        int jobId;

        try {

            jobId = Integer.parseInt(input);

        } catch (NumberFormatException e) {

            System.out.println();
            System.out.println(
                    "Invalid Job ID.");

            pause();
            return;
        }

        System.out.println();
        System.out.println("Checking application requirements...");

        int studentId = getStudentId();

        String result =
                applicationService.applyForJob(
                        studentId,
                        jobId);

        System.out.println();

        System.out.println("==================================================");
        System.out.println("              APPLICATION RESULT");
        System.out.println("==================================================");

        switch (result) {

            case "SUCCESS":

                System.out.println();
                System.out.println(
                        "APPLICATION SUBMITTED SUCCESSFULLY!");

                System.out.println();
                System.out.println(
                        "Your application has been recorded.");

                System.out.println(
                        "Application Status : APPLIED");

                break;

            case "ALREADY_APPLIED":

                System.out.println();
                System.out.println(
                        "APPLICATION ALREADY EXISTS!");

                System.out.println();
                System.out.println(
                        "You have already applied for this job.");

                System.out.println(
                        "Duplicate applications are not allowed.");

                break;

            case "CGPA_NOT_ELIGIBLE":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "You do not meet the required CGPA.");

                break;

            case "BACKLOG_NOT_ELIGIBLE":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "You do not meet the backlog requirement.");

                break;

            case "SKILLS_NOT_ELIGIBLE":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "You do not have the required skills.");

                break;

            case "DEADLINE_PASSED":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "The application deadline has passed.");

                break;

            case "JOB_NOT_AVAILABLE":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "This job is currently not available.");

                break;

            case "JOB_NOT_FOUND":

                System.out.println();
                System.out.println(
                        "APPLICATION NOT SUBMITTED.");

                System.out.println(
                        "Job not found.");

                break;

            default:

                System.out.println();
                System.out.println(
                        "APPLICATION FAILED.");

                System.out.println(
                        "A database error occurred.");
        }

        System.out.println();
        System.out.println(
                "==================================================");

        pause();
    }

    // =========================================================
    // MY APPLICATIONS
    // =========================================================

    private void showMyApplications() {

    System.out.println();
    System.out.println("==============================================================");
    System.out.println("                    MY APPLICATIONS");
    System.out.println("==============================================================");

    List<Application> applications =
            applicationService.getApplicationsByStudent(
                    user.getUserId());

    if (applications == null || applications.isEmpty()) {

        System.out.println();
        System.out.println("You have not applied for any jobs yet.");
        System.out.println();
        System.out.println("==============================================================");

        pause();

        return;
    }

    System.out.println();

    for (Application application : applications) {

        System.out.println("--------------------------------------------------------------");

        System.out.println(
                "Application ID      : "
                        + application.getApplicationId());

        System.out.println(
                "Job ID              : "
                        + application.getJobId());

        System.out.println(
                "Position            : "
                        + application.getJobTitle());

        System.out.println(
                "Location            : "
                        + application.getLocation());

        System.out.println(
                "Employment Type     : "
                        + application.getEmploymentType());

        System.out.printf(
                "Salary              : Rs. %.2f%n",
                application.getSalary());

        System.out.println(
                "Applied At          : "
                        + application.getAppliedAt());

        System.out.println(
                "Application Status  : "
                        + application.getStatus());

        String remarks = application.getRemarks();

        if (remarks == null || remarks.isBlank()) {
            remarks = "-";
        }

        System.out.println(
                "Remarks             : "
                        + remarks);

        System.out.println(
                "Application Deadline: "
                        + application.getApplicationDeadline());

        System.out.println("--------------------------------------------------------------");
    }

    System.out.println();
    System.out.println("Total Applications : " + applications.size());

    System.out.println();
    System.out.println("==============================================================");

    pause();
}
    // =========================================================
    // HELPER METHODS
    // =========================================================

    private int getStudentId() {

        String sql = """
                SELECT student_id
                FROM students
                WHERE user_id = ?
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, user.getUserId());

            try (java.sql.ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("student_id");
                }
            }

        } catch (java.sql.SQLException e) {

            System.out.println(
                    "Unable to find student ID.");

            e.printStackTrace();
        }

        return -1;
    }

    private int getRequiredSkillCount(int jobId)
            throws java.sql.SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM job_skills
                WHERE job_id = ?
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, jobId);

            try (java.sql.ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private int getMatchingSkillCount(
            int studentId,
            int jobId) throws java.sql.SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM job_skills js
                JOIN student_skills ss
                  ON ss.skill_id = js.skill_id
                 AND ss.student_id = ?
                WHERE js.job_id = ?
                  AND
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
                """;

        try (java.sql.Connection connection =
                     com.smartplacement.util.DBConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, jobId);

            try (java.sql.ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private void pause() {

        System.out.println();
        System.out.print("Press ENTER to continue...");

        scanner.nextLine();
    }
}