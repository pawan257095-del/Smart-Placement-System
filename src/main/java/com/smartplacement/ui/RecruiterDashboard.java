package com.smartplacement.ui;

import com.smartplacement.model.User;
import com.smartplacement.model.Job;
import com.smartplacement.dao.JobDAO;

import com.smartplacement.dao.ApplicationDAO;
import com.smartplacement.model.Application;
import com.smartplacement.service.RecruiterService;

import java.util.List;
import com.smartplacement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RecruiterDashboard {

    private final User user;
    private final Scanner scanner;

    public RecruiterDashboard(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
    }

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("              RECRUITER DASHBOARD");
            System.out.println("==================================================");

            System.out.println();
            System.out.println("Welcome, " + user.getName());
            System.out.println("Email: " + user.getEmail());

            System.out.println();
            System.out.println("--------------------------------------------------");
            System.out.println("                     MENU");
            System.out.println("--------------------------------------------------");

            System.out.println("1. Company Profile");
            System.out.println("2. Edit Company Profile");
            System.out.println("3. Create Job");
            System.out.println("4. My Jobs");
            System.out.println("5. View Applicants");
            System.out.println("6. Manage Applications");
            System.out.println("7. Logout");
            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
    companyProfile();
    break;

case "2":
    editCompanyProfile();
    break;

case "3":
    createJob();
    break;

case "4":
    myJobs();
    break;

case "5":
    viewApplicants();
    break;

case "6":
    manageApplications();
    break;

case "7":
    System.out.println();
    System.out.println("Logging out...");
    running = false;
    break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =========================================================
    // COMPANY PROFILE
    // =========================================================

    private void companyProfile() {

    System.out.println();
    System.out.println("==================================================");
    System.out.println("                COMPANY PROFILE");
    System.out.println("==================================================");

    RecruiterService recruiterService =
            new RecruiterService();

    int companyId =
            recruiterService.getCompanyIdByUserId(
                    user.getUserId()
            );

    if (companyId == -1) {

        System.out.println();
        System.out.println(
                "Unable to find your company profile."
        );

        pause();
        return;
    }

    String sql = """
            SELECT
                company_id,
                company_name,
                industry,
                website,
                email,
                phone,
                address,
                description,
                status
            FROM companies
            WHERE company_id = ?
            """;

    try (Connection connection =
                 DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, companyId);

        try (ResultSet rs =
                     statement.executeQuery()) {

            if (!rs.next()) {

                System.out.println();
                System.out.println(
                        "Company profile not found."
                );

                pause();
                return;
            }

            System.out.println();

            System.out.println("Company Information");
            System.out.println("-------------------");

            System.out.println(
                    "Company ID       : "
                            + rs.getInt("company_id")
            );

            System.out.println(
                    "Company Name     : "
                            + displayValue(
                                    rs.getString("company_name")
                            )
            );

            System.out.println(
                    "Industry         : "
                            + displayValue(
                                    rs.getString("industry")
                            )
            );

            System.out.println(
                    "Status           : "
                            + displayValue(
                                    rs.getString("status")
                            )
            );

            System.out.println();

            System.out.println("Contact Information");
            System.out.println("-------------------");

            System.out.println(
                    "Email            : "
                            + displayValue(
                                    rs.getString("email")
                            )
            );

            System.out.println(
                    "Phone            : "
                            + displayValue(
                                    rs.getString("phone")
                            )
            );

            System.out.println(
                    "Website          : "
                            + displayValue(
                                    rs.getString("website")
                            )
            );

            System.out.println(
                    "Address          : "
                            + displayValue(
                                    rs.getString("address")
                            )
            );

            System.out.println();

            System.out.println("Description");
            System.out.println("-----------");

            System.out.println(
                    displayValue(
                            rs.getString("description")
                    )
            );

            System.out.println();

            System.out.println(
                    "=================================================="
            );
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to load company profile."
        );

        System.out.println(
                "Database error: "
                        + e.getMessage()
        );

        e.printStackTrace();
    }

    pause();
}

// =========================================================
// EDIT COMPANY PROFILE
// =========================================================

private void editCompanyProfile() {

    System.out.println();
    System.out.println("==============================================================");
    System.out.println("                   EDIT COMPANY PROFILE");
    System.out.println("==============================================================");

    RecruiterService recruiterService =
            new RecruiterService();

    int companyId =
            recruiterService.getCompanyIdByUserId(
                    user.getUserId()
            );

    if (companyId == -1) {

        System.out.println();
        System.out.println(
                "Unable to find your company profile."
        );

        pause();
        return;
    }

    String selectSql = """
            SELECT
                company_name,
                industry,
                website,
                email,
                phone,
                address,
                description
            FROM companies
            WHERE company_id = ?
            """;

    String companyName;
    String industry;
    String website;
    String email;
    String phone;
    String address;
    String description;

    try (Connection connection =
                 DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(selectSql)) {

        statement.setInt(1, companyId);

        try (ResultSet rs =
                     statement.executeQuery()) {

            if (!rs.next()) {

                System.out.println();
                System.out.println(
                        "Company profile not found."
                );

                pause();
                return;
            }

            String currentCompanyName =
                    rs.getString("company_name");

            String currentIndustry =
                    rs.getString("industry");

            String currentWebsite =
                    rs.getString("website");

            String currentEmail =
                    rs.getString("email");

            String currentPhone =
                    rs.getString("phone");

            String currentAddress =
                    rs.getString("address");

            String currentDescription =
                    rs.getString("description");

            System.out.println();
            System.out.println(
                    "Press ENTER to keep the current value."
            );

            System.out.println();

            System.out.print(
                    "Company Name [" +
                            displayValue(currentCompanyName) +
                            "]: "
            );

            String input =
                    scanner.nextLine().trim();

            companyName =
                    input.isBlank()
                            ? currentCompanyName
                            : input;

            System.out.print(
                    "Industry [" +
                            displayValue(currentIndustry) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            industry =
                    input.isBlank()
                            ? currentIndustry
                            : input;

            System.out.print(
                    "Website [" +
                            displayValue(currentWebsite) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            website =
                    input.isBlank()
                            ? currentWebsite
                            : input;

            System.out.print(
                    "Company Email [" +
                            displayValue(currentEmail) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            email =
                    input.isBlank()
                            ? currentEmail
                            : input;

            System.out.print(
                    "Phone [" +
                            displayValue(currentPhone) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            phone =
                    input.isBlank()
                            ? currentPhone
                            : input;

            System.out.print(
                    "Address [" +
                            displayValue(currentAddress) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            address =
                    input.isBlank()
                            ? currentAddress
                            : input;

            System.out.print(
                    "Description [" +
                            displayValue(currentDescription) +
                            "]: "
            );

            input =
                    scanner.nextLine().trim();

            description =
                    input.isBlank()
                            ? currentDescription
                            : input;
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to load company profile."
        );

        System.out.println(
                "Database error: "
                        + e.getMessage()
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // Preview
    // ---------------------------------------------------------

    System.out.println();

    System.out.println(
            "=============================================================="
    );

    System.out.println(
            "                    UPDATE PREVIEW"
    );

    System.out.println(
            "=============================================================="
    );

    System.out.println();

    System.out.println(
            "Company ID    : " + companyId
    );

    System.out.println(
            "Company Name  : " + displayValue(companyName)
    );

    System.out.println(
            "Industry      : " + displayValue(industry)
    );

    System.out.println(
            "Website       : " + displayValue(website)
    );

    System.out.println(
            "Email         : " + displayValue(email)
    );

    System.out.println(
            "Phone         : " + displayValue(phone)
    );

    System.out.println(
            "Address       : " + displayValue(address)
    );

    System.out.println(
            "Description   : " + displayValue(description)
    );

    System.out.println();

    System.out.print(
            "Save these changes? (Y/N): "
    );

    String confirmation =
            scanner.nextLine().trim();

    if (!confirmation.equalsIgnoreCase("Y")) {

        System.out.println();
        System.out.println(
                "Company profile update cancelled."
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // Update database
    // ---------------------------------------------------------

    boolean updated =
            recruiterService.updateCompanyProfile(
                    companyId,
                    companyName,
                    industry,
                    website,
                    email,
                    phone,
                    address,
                    description
            );

    System.out.println();

    if (updated) {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "       COMPANY PROFILE UPDATED SUCCESSFULLY"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println();

        System.out.println(
                "Company ID   : " + companyId
        );

        System.out.println(
                "Company Name : "
                        + displayValue(companyName)
        );

    } else {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "          COMPANY PROFILE UPDATE FAILED"
        );

        System.out.println(
                "=================================================="
        );
    }

    pause();
}
    // =========================================================
    // CREATE JOB
    // =========================================================

    private void createJob() {

    System.out.println();
    System.out.println("==================================================");
    System.out.println("                  CREATE JOB");
    System.out.println("==================================================");

    System.out.println();
    System.out.println("Enter Job Details");
    System.out.println("----------------");

    // ---------------------------------------------------------
    // 1. Job Title
    // ---------------------------------------------------------

    String title;

    while (true) {

        System.out.print("Job Title: ");
        title = scanner.nextLine().trim();

        if (!title.isBlank()) {
            break;
        }

        System.out.println("Job title cannot be empty.");
    }

    // ---------------------------------------------------------
    // 2. Description
    // ---------------------------------------------------------

    String description;

    while (true) {

        System.out.print("Job Description: ");
        description = scanner.nextLine().trim();

        if (!description.isBlank()) {
            break;
        }

        System.out.println("Job description cannot be empty.");
    }

    // ---------------------------------------------------------
    // 3. Location
    // ---------------------------------------------------------

    String location;

    while (true) {

        System.out.print("Location: ");
        location = scanner.nextLine().trim();

        if (!location.isBlank()) {
            break;
        }

        System.out.println("Location cannot be empty.");
    }

    // ---------------------------------------------------------
    // 4. Employment Type
    // ---------------------------------------------------------

    String employmentType = null;

    while (employmentType == null) {

        System.out.println();
        System.out.println("Employment Type");
        System.out.println("1. FULL_TIME");
        System.out.println("2. PART_TIME");
        System.out.println("3. INTERNSHIP");

        System.out.print("Select employment type: ");

        String typeChoice = scanner.nextLine().trim();

        switch (typeChoice) {

            case "1":
                employmentType = "FULL_TIME";
                break;

            case "2":
                employmentType = "PART_TIME";
                break;

            case "3":
                employmentType = "INTERNSHIP";
                break;

            default:
                System.out.println(
                        "Invalid choice. Please select 1, 2 or 3."
                );
        }
    }

    // ---------------------------------------------------------
    // 5. Salary
    // ---------------------------------------------------------

    java.math.BigDecimal salary = null;

    while (salary == null) {

        System.out.print("Salary (e.g. 800000): ");

        String salaryInput = scanner.nextLine().trim();

        try {

            salary = new java.math.BigDecimal(salaryInput);

            if (salary.compareTo(java.math.BigDecimal.ZERO) < 0) {

                System.out.println(
                        "Salary cannot be negative."
                );

                salary = null;
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid salary. Please enter a valid number."
            );
        }
    }

    // ---------------------------------------------------------
    // 6. Minimum CGPA
    // ---------------------------------------------------------

    java.math.BigDecimal minCgpa = null;

    while (minCgpa == null) {

        System.out.print("Minimum CGPA (0 - 10): ");

        String cgpaInput = scanner.nextLine().trim();

        try {

            minCgpa = new java.math.BigDecimal(cgpaInput);

            if (minCgpa.compareTo(java.math.BigDecimal.ZERO) < 0
                    || minCgpa.compareTo(new java.math.BigDecimal("10.00")) > 0) {

                System.out.println(
                        "CGPA must be between 0 and 10."
                );

                minCgpa = null;
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid CGPA. Please enter a number between 0 and 10."
            );
        }
    }

    // ---------------------------------------------------------
    // 7. Maximum Backlogs
    // ---------------------------------------------------------

    int maxBacklogs = -1;

    while (maxBacklogs < 0) {

        System.out.print("Maximum Backlogs: ");

        String backlogInput = scanner.nextLine().trim();

        try {

            maxBacklogs = Integer.parseInt(backlogInput);

            if (maxBacklogs < 0) {

                System.out.println(
                        "Backlogs cannot be negative."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid number. Please enter a whole number."
            );
        }
    }

    // ---------------------------------------------------------
    // 8. Application Deadline
    // ---------------------------------------------------------

    java.sql.Timestamp applicationDeadline = null;

    while (applicationDeadline == null) {

        System.out.println();
        System.out.println(
                "Application Deadline Format: yyyy-MM-dd HH:mm"
        );

        System.out.print(
                "Application Deadline: "
        );

        String deadlineInput = scanner.nextLine().trim();

        try {

            java.time.LocalDateTime dateTime =
                    java.time.LocalDateTime.parse(
                            deadlineInput,
                            java.time.format.DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd HH:mm"
                            )
                    );

            applicationDeadline =
                    java.sql.Timestamp.valueOf(dateTime);

            if (applicationDeadline.before(
                    new java.sql.Timestamp(
                            System.currentTimeMillis()
                    ))) {

                System.out.println(
                        "Deadline must be in the future."
                );

                applicationDeadline = null;
            }

        } catch (Exception e) {

            System.out.println(
                    "Invalid date/time format."
            );

            System.out.println(
                    "Use: yyyy-MM-dd HH:mm"
            );
        }
    }

    // ---------------------------------------------------------
    // 9. Show Job Preview
    // ---------------------------------------------------------

    System.out.println();
    System.out.println("==================================================");
    System.out.println("                  JOB PREVIEW");
    System.out.println("==================================================");

    System.out.println();
    System.out.println("Title              : " + title);
    System.out.println("Description        : " + description);
    System.out.println("Location           : " + location);
    System.out.println("Employment Type    : " + employmentType);
    System.out.println("Salary             : Rs." + salary);
    System.out.println("Minimum CGPA       : " + minCgpa);
    System.out.println("Maximum Backlogs   : " + maxBacklogs);
    System.out.println("Application Dead.  : " + applicationDeadline);
    System.out.println("Status             : PENDING");

    System.out.println();
    System.out.println("--------------------------------------------------");

    System.out.print(
            "Do you want to create this job? (Y/N): "
    );

    String confirmation = scanner.nextLine().trim();

    if (!confirmation.equalsIgnoreCase("Y")) {

        System.out.println();
        System.out.println(
                "Job creation cancelled."
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
// 10. Find Recruiter ID and Company ID
// ---------------------------------------------------------

int recruiterId = -1;
int companyId = -1;

String recruiterSql = """
        SELECT recruiter_id, company_id
        FROM recruiters
        WHERE user_id = ?
        """;

try (Connection connection = DBConnection.getConnection();
     PreparedStatement statement =
             connection.prepareStatement(recruiterSql)) {

    // IMPORTANT:
    // users.user_id is used to find the corresponding
    // recruiters.recruiter_id.
    statement.setInt(1, user.getUserId());

    try (ResultSet rs = statement.executeQuery()) {

        if (rs.next()) {

            recruiterId = rs.getInt("recruiter_id");
            companyId = rs.getInt("company_id");

            System.out.println(
                    "DEBUG: User ID     = " + user.getUserId()
            );

            System.out.println(
                    "DEBUG: Recruiter ID = " + recruiterId
            );

            System.out.println(
                    "DEBUG: Company ID   = " + companyId
            );

        } else {

            System.out.println();
            System.out.println(
                    "Unable to find recruiter record."
            );

            System.out.println(
                    "No recruiter record exists for user ID: "
                            + user.getUserId()
            );

            pause();
            return;
        }
    }

} catch (SQLException e) {

    System.out.println();
    System.out.println(
            "Unable to identify recruiter."
    );

    System.out.println(
            "Database error: " + e.getMessage()
    );

    e.printStackTrace();

    pause();
    return;
}
String insertSql = """
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

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(
                         insertSql,
                         java.sql.Statement.RETURN_GENERATED_KEYS
                 )) {

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

            int jobId = -1;

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {
                    jobId = keys.getInt(1);
                }
            }

            System.out.println();
            System.out.println("==================================================");
            System.out.println("              JOB CREATED SUCCESSFULLY");
            System.out.println("==================================================");

            System.out.println();
            System.out.println("Job ID             : " + jobId);
            System.out.println("Job Title          : " + title);
            System.out.println("Employment Type    : " + employmentType);
            System.out.println("Location           : " + location);
            System.out.println("Status             : PENDING");

            System.out.println();
            System.out.println(
                    "Your job has been submitted for approval."
            );

            System.out.println(
                    "Students can apply after the job is APPROVED."
            );

            System.out.println();
            System.out.println("==================================================");

        } else {

            System.out.println();
            System.out.println(
                    "Unable to create job."
            );
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to create job."
        );

        System.out.println(
                "Database error: " + e.getMessage()
        );

        e.printStackTrace();
    }

    pause();
}
    // =========================================================
    // MY JOBS
    // =========================================================

private void myJobs() {

    System.out.println();
    System.out.println("==============================================================");
    System.out.println("                         MY JOBS");
    System.out.println("==============================================================");

    // ---------------------------------------------------------
    // Find recruiter_id from logged-in user
    // ---------------------------------------------------------

    int recruiterId = -1;

    String recruiterSql = """
            SELECT recruiter_id
            FROM recruiters
            WHERE user_id = ?
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(recruiterSql)) {

        statement.setInt(1, user.getUserId());

        try (ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {

                recruiterId = rs.getInt("recruiter_id");

                System.out.println(
                        "DEBUG: User ID      = " + user.getUserId()
                );

                System.out.println(
                        "DEBUG: Recruiter ID = " + recruiterId
                );

            } else {

                System.out.println();
                System.out.println(
                        "Recruiter profile not found."
                );

                pause();
                return;
            }
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to identify recruiter."
        );

        System.out.println(
                "Database error: " + e.getMessage()
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // Get jobs created by this recruiter
    // ---------------------------------------------------------

    JobDAO jobDAO = new JobDAO();

    List<Job> jobs =
            jobDAO.findJobsByRecruiterId(recruiterId);

    if (jobs == null || jobs.isEmpty()) {

        System.out.println();
        System.out.println("You have not created any jobs yet.");
        System.out.println();
        System.out.println(
                "Create a job from option 2."
        );

        System.out.println();
        System.out.println("==============================================================");

        pause();
        return;
    }

    // ---------------------------------------------------------
    // Display jobs
    // ---------------------------------------------------------

    System.out.println();

    System.out.printf(
            "%-6s %-28s %-18s %-13s %-15s%n",
            "ID",
            "POSITION",
            "LOCATION",
            "TYPE",
            "SALARY"
    );

    System.out.println(
            "-------------------------------------------------------------------------------"
    );

    for (Job job : jobs) {

        String salary =
                job.getSalary() == null
                        ? "-"
                        : "Rs." + job.getSalary();

        System.out.printf(
                "%-6d %-28s %-18s %-13s %-15s%n",
                job.getJobId(),
                truncate(job.getTitle(), 28),
                truncate(job.getLocation(), 18),
                truncate(job.getEmploymentType(), 13),
                salary
        );
    }

    System.out.println(
            "-------------------------------------------------------------------------------"
    );

    // ---------------------------------------------------------
    // Display complete details
    // ---------------------------------------------------------

    System.out.println();

    for (Job job : jobs) {

        System.out.println(
                "--------------------------------------------------------------"
        );

        System.out.println(
                "Job ID               : " + job.getJobId()
        );

        System.out.println(
                "Position             : " + displayValue(job.getTitle())
        );

        System.out.println(
                "Description          : "
                        + displayValue(job.getDescription())
        );

        System.out.println(
                "Location             : "
                        + displayValue(job.getLocation())
        );

        System.out.println(
                "Employment Type      : "
                        + displayValue(job.getEmploymentType())
        );

        System.out.println(
                "Salary               : "
                        + (job.getSalary() == null
                        ? "-"
                        : "Rs." + job.getSalary())
        );

        System.out.println(
                "Minimum CGPA         : "
                        + (job.getMinCgpa() == null
                        ? "-"
                        : job.getMinCgpa())
        );

        System.out.println(
                "Maximum Backlogs     : "
                        + job.getMaxBacklogs()
        );

        System.out.println(
                "Application Deadline : "
                        + (job.getApplicationDeadline() == null
                        ? "-"
                        : job.getApplicationDeadline())
        );

        System.out.println(
                "Status               : "
                        + displayValue(job.getStatus())
        );

        System.out.println(
                "Created At           : "
                        + (job.getCreatedAt() == null
                        ? "-"
                        : job.getCreatedAt())
        );
    }

    System.out.println(
            "--------------------------------------------------------------"
    );

    System.out.println();
    System.out.println(
            "Total Jobs : " + jobs.size()
    );

    System.out.println();
    System.out.println(
            "=============================================================="
    );

    pause();
}

    // =========================================================
// VIEW APPLICANTS
// =========================================================

private void viewApplicants() {

    System.out.println();
    System.out.println("==============================================================");
    System.out.println("                       VIEW APPLICANTS");
    System.out.println("==============================================================");

    // ---------------------------------------------------------
    // 1. Find recruiter ID
    // ---------------------------------------------------------

    int recruiterId = -1;

    String recruiterSql = """
            SELECT recruiter_id
            FROM recruiters
            WHERE user_id = ?
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(recruiterSql)) {

        statement.setInt(1, user.getUserId());

        try (ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {

                recruiterId = rs.getInt("recruiter_id");

                System.out.println(
                        "DEBUG: User ID      = " + user.getUserId()
                );

                System.out.println(
                        "DEBUG: Recruiter ID = " + recruiterId
                );

            } else {

                System.out.println();
                System.out.println(
                        "Recruiter profile not found."
                );

                pause();
                return;
            }
        }

    } catch (SQLException e) {

        System.out.println();
        System.out.println(
                "Unable to identify recruiter."
        );

        System.out.println(
                "Database error: " + e.getMessage()
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // 2. Get recruiter's jobs
    // ---------------------------------------------------------

    JobDAO jobDAO = new JobDAO();

    List<Job> jobs =
            jobDAO.findJobsByRecruiterId(recruiterId);

    if (jobs == null || jobs.isEmpty()) {

        System.out.println();
        System.out.println(
                "You have not created any jobs yet."
        );

        System.out.println();
        System.out.println(
                "Create a job from option 2."
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // 3. Display jobs
    // ---------------------------------------------------------

    System.out.println();
    System.out.println("Your Jobs");
    System.out.println("---------");

    System.out.printf(
            "%-8s %-30s %-20s%n",
            "JOB ID",
            "POSITION",
            "LOCATION"
    );

    System.out.println(
            "--------------------------------------------------------------"
    );

    for (Job job : jobs) {

        System.out.printf(
                "%-8d %-30s %-20s%n",
                job.getJobId(),
                truncate(job.getTitle(), 30),
                truncate(job.getLocation(), 20)
        );
    }

    System.out.println(
            "--------------------------------------------------------------"
    );

    // ---------------------------------------------------------
    // 4. Select Job
    // ---------------------------------------------------------

    int selectedJobId;

    while (true) {

        System.out.print(
                "\nEnter Job ID to view applicants (0 to return): "
        );

        String input = scanner.nextLine().trim();

        try {

            selectedJobId = Integer.parseInt(input);

            if (selectedJobId == 0) {
                return;
            }

            boolean jobExists = false;

            for (Job job : jobs) {

                if (job.getJobId() == selectedJobId) {
                    jobExists = true;
                    break;
                }
            }

            if (jobExists) {
                break;
            }

            System.out.println(
                    "You can only select a job created by you."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid Job ID. Please enter a number."
            );
        }
    }

    // ---------------------------------------------------------
    // 5. Get applicants
    // ---------------------------------------------------------

    System.out.println();
    System.out.println(
            "Loading applicants..."
    );

    ApplicationDAO applicationDAO =
            new ApplicationDAO();

    List<Application> applicants =
            applicationDAO.getApplicantsByRecruiter(
                    recruiterId
            );

    // ---------------------------------------------------------
    // 6. Filter applicants for selected job
    // ---------------------------------------------------------

    List<Application> jobApplicants =
            new java.util.ArrayList<>();

    for (Application application : applicants) {

        if (application.getJobId() == selectedJobId) {

            jobApplicants.add(application);
        }
    }

    // ---------------------------------------------------------
    // 7. No applicants
    // ---------------------------------------------------------

    if (jobApplicants.isEmpty()) {

        System.out.println();
        System.out.println(
                "=================================================="
        );

        System.out.println(
                "              NO APPLICANTS FOUND"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println();
        System.out.println(
                "No students have applied for this job yet."
        );

        pause();
        return;
    }

    // ---------------------------------------------------------
    // 8. Display applicants
    // ---------------------------------------------------------

    System.out.println();
    System.out.println(
            "=============================================================="
    );

    System.out.println(
            "                         APPLICANTS"
    );

    System.out.println(
            "=============================================================="
    );

    for (Application application : jobApplicants) {

        System.out.println();

        System.out.println(
                "--------------------------------------------------------------"
        );

        System.out.println(
                "Application ID      : "
                        + application.getApplicationId()
        );

        System.out.println(
                "Student Name        : "
                        + displayValue(
                                application.getStudentName()
                        )
        );

        System.out.println(
                "Email               : "
                        + displayValue(
                                application.getStudentEmail()
                        )
        );

        System.out.println(
                "Phone               : "
                        + displayValue(
                                application.getPhone()
                        )
        );

        System.out.println(
                "Enrollment No.      : "
                        + displayValue(
                                application.getEnrollmentNo()
                        )
        );

        System.out.println();

        System.out.println(
                "Course              : "
                        + displayValue(
                                application.getCourse()
                        )
        );

        System.out.println(
                "Branch              : "
                        + displayValue(
                                application.getBranch()
                        )
        );

        System.out.println(
                "Semester            : "
                        + application.getSemester()
        );

        System.out.println(
                "CGPA                : "
                        + String.format(
                                "%.2f",
                                application.getCgpa()
                        )
        );

        System.out.println(
                "Backlogs            : "
                        + application.getBacklogs()
        );

        System.out.println(
                "Graduation Year     : "
                        + application.getGraduationYear()
        );

        System.out.println();

        System.out.println(
                "Application Status  : "
                        + displayValue(
                                application.getStatus()
                        )
        );

        System.out.println(
                "Applied At          : "
                        + application.getAppliedAt()
        );

        System.out.println(
                "Remarks             : "
                        + displayValue(
                                application.getRemarks()
                        )
        );
    }

    System.out.println();

    System.out.println(
            "--------------------------------------------------------------"
    );

    System.out.println();
    System.out.println(
            "Total Applicants : "
                    + jobApplicants.size()
    );

    System.out.println(
            "=============================================================="
    );

    pause();
}
    // =========================================================
    // MANAGE APPLICATIONS
    // =========================================================

      

    private void manageApplications() {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                    MANAGE APPLICATIONS");
        System.out.println("==============================================================");

        // ---------------------------------------------------------
        // 1. Find recruiter ID
        // ---------------------------------------------------------

        int recruiterId = -1;

        String recruiterSql = """
                SELECT recruiter_id
                FROM recruiters
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(recruiterSql)) {

            statement.setInt(1, user.getUserId());

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    recruiterId = rs.getInt("recruiter_id");

                } else {

                    System.out.println();
                    System.out.println(
                            "Recruiter profile not found."
                    );

                    pause();
                    return;
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to identify recruiter."
            );

            System.out.println(
                    "Database error: " + e.getMessage()
            );

            pause();
            return;
        }

        // ---------------------------------------------------------
        // 2. Get recruiter's jobs
        // ---------------------------------------------------------

        JobDAO jobDAO = new JobDAO();

        List<Job> jobs =
                jobDAO.findJobsByRecruiterId(recruiterId);

        if (jobs == null || jobs.isEmpty()) {

            System.out.println();
            System.out.println(
                    "You have not created any jobs yet."
            );

            pause();
            return;
        }

        // ---------------------------------------------------------
        // 3. Display jobs
        // ---------------------------------------------------------

        System.out.println();
        System.out.println("Your Jobs");
        System.out.println("---------");

        System.out.printf(
                "%-8s %-30s %-20s%n",
                "JOB ID",
                "POSITION",
                "LOCATION"
        );

        System.out.println(
                "--------------------------------------------------------------"
        );

        for (Job job : jobs) {

            System.out.printf(
                    "%-8d %-30s %-20s%n",
                    job.getJobId(),
                    truncate(job.getTitle(), 30),
                    truncate(job.getLocation(), 20)
            );
        }

        System.out.println(
                "--------------------------------------------------------------"
        );

        // ---------------------------------------------------------
        // 4. Select Job
        // ---------------------------------------------------------

        int selectedJobId;

        while (true) {

            System.out.print(
                    "\nEnter Job ID to manage applications (0 to return): "
            );

            String input = scanner.nextLine().trim();

            try {

                selectedJobId = Integer.parseInt(input);

                if (selectedJobId == 0) {
                    return;
                }

                boolean jobExists = false;

                for (Job job : jobs) {

                    if (job.getJobId() == selectedJobId) {
                        jobExists = true;
                        break;
                    }
                }

                if (jobExists) {
                    break;
                }

                System.out.println(
                        "You can only select a job created by you."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Job ID. Please enter a number."
                );
            }
        }

        // ---------------------------------------------------------
        // 5. Get applicants
        // ---------------------------------------------------------

        System.out.println();
        System.out.println(
                "Loading applications..."
        );

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        List<Application> applicants =
                applicationDAO.getApplicantsByRecruiter(
                        recruiterId
                );

        // ---------------------------------------------------------
        // 6. Filter applicants for selected job
        // ---------------------------------------------------------

        List<Application> jobApplicants =
                new java.util.ArrayList<>();

        for (Application application : applicants) {

            if (application.getJobId() == selectedJobId) {

                jobApplicants.add(application);
            }
        }

        // ---------------------------------------------------------
        // 7. No applicants
        // ---------------------------------------------------------

        if (jobApplicants.isEmpty()) {

            System.out.println();
            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "              NO APPLICATIONS FOUND"
            );

            System.out.println(
                    "=================================================="
            );

            System.out.println();
            System.out.println(
                    "No students have applied for this job yet."
            );

            pause();
            return;
        }

        // ---------------------------------------------------------
        // 8. Display applications
        // ---------------------------------------------------------

        System.out.println();
        System.out.println(
                "=============================================================="
        );

        System.out.println(
                "                       APPLICATIONS"
        );

        System.out.println(
                "=============================================================="
        );

        System.out.printf(
                "%-8s %-25s %-18s%n",
                "ID",
                "STUDENT",
                "STATUS"
        );

        System.out.println(
                "--------------------------------------------------------------"
        );

        for (Application application : jobApplicants) {

            System.out.printf(
                    "%-8d %-25s %-18s%n",
                    application.getApplicationId(),
                    truncate(
                            application.getStudentName(),
                            25
                    ),
                    application.getStatus()
            );
        }

        System.out.println(
                "--------------------------------------------------------------"
        );

        // ---------------------------------------------------------
        // 9. Select Application
        // ---------------------------------------------------------

        int selectedApplicationId;

        while (true) {

            System.out.print(
                    "\nEnter Application ID to manage (0 to return): "
            );

            String input = scanner.nextLine().trim();

            try {

                selectedApplicationId =
                        Integer.parseInt(input);

                if (selectedApplicationId == 0) {
                    return;
                }

                boolean applicationExists = false;

                for (Application application : jobApplicants) {

                    if (application.getApplicationId()
                            == selectedApplicationId) {

                        applicationExists = true;
                        break;
                    }
                }

                if (applicationExists) {
                    break;
                }

                System.out.println(
                        "Invalid Application ID."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Application ID. Please enter a number."
                );
            }
        }

        // ---------------------------------------------------------
        // 10. Find selected application
        // ---------------------------------------------------------

        Application selectedApplication = null;

        for (Application application : jobApplicants) {

            if (application.getApplicationId()
                    == selectedApplicationId) {

                selectedApplication = application;
                break;
            }
        }

        if (selectedApplication == null) {

            System.out.println(
                    "Application not found."
            );

            pause();
            return;
        }

        // ---------------------------------------------------------
        // 11. Display applicant details
        // ---------------------------------------------------------

        System.out.println();
        System.out.println(
                "=============================================================="
        );

        System.out.println(
                "                    APPLICATION DETAILS"
        );

        System.out.println(
                "=============================================================="
        );

        System.out.println();

        System.out.println(
                "Application ID      : "
                        + selectedApplication.getApplicationId()
        );

        System.out.println(
                "Student Name        : "
                        + displayValue(
                                selectedApplication.getStudentName()
                        )
        );

        System.out.println(
                "Email               : "
                        + displayValue(
                                selectedApplication.getStudentEmail()
                        )
        );

        System.out.println(
                "Phone               : "
                        + displayValue(
                                selectedApplication.getPhone()
                        )
        );

        System.out.println(
                "Enrollment No.      : "
                        + displayValue(
                                selectedApplication.getEnrollmentNo()
                        )
        );

        System.out.println();

        System.out.println(
                "Course              : "
                        + displayValue(
                                selectedApplication.getCourse()
                        )
        );

        System.out.println(
                "Branch              : "
                        + displayValue(
                                selectedApplication.getBranch()
                        )
        );

        System.out.println(
                "Semester            : "
                        + selectedApplication.getSemester()
        );

        System.out.println(
                "CGPA                : "
                        + String.format(
                                "%.2f",
                                selectedApplication.getCgpa()
                        )
        );

        System.out.println(
                "Backlogs            : "
                        + selectedApplication.getBacklogs()
        );

        System.out.println(
                "Graduation Year     : "
                        + selectedApplication.getGraduationYear()
        );

        System.out.println();

        System.out.println(
                "Current Status      : "
                        + displayValue(
                                selectedApplication.getStatus()
                        )
        );

        System.out.println(
                "Current Remarks     : "
                        + displayValue(
                                selectedApplication.getRemarks()
                        )
        );

        System.out.println(
                "Applied At          : "
                        + selectedApplication.getAppliedAt()
        );

        // ---------------------------------------------------------
        // 12. Select new status
        // ---------------------------------------------------------

        String newStatus = null;

        while (newStatus == null) {

            System.out.println();
            System.out.println(
                    "--------------------------------------------------------------"
            );

            System.out.println(
                    "                    UPDATE STATUS"
            );

            System.out.println(
                    "--------------------------------------------------------------"
            );

            System.out.println("1. UNDER_REVIEW");
            System.out.println("2. SHORTLISTED");
            System.out.println("3. INTERVIEW");
            System.out.println("4. SELECTED");
            System.out.println("5. REJECTED");
            System.out.println("6. WITHDRAWN");
            System.out.println("7. Keep Current Status");
            System.out.println("0. Cancel");

            System.out.print(
                    "\nSelect action: "
            );

            String choice =
                    scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    newStatus = "UNDER_REVIEW";
                    break;

                case "2":
                    newStatus = "SHORTLISTED";
                    break;

                case "3":
                    newStatus = "INTERVIEW";
                    break;

                case "4":
                    newStatus = "SELECTED";
                    break;

                case "5":
                    newStatus = "REJECTED";
                    break;

                case "6":
                    newStatus = "WITHDRAWN";
                    break;

                case "7":
                    newStatus =
                            selectedApplication.getStatus();
                    break;

                case "0":
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please select 0-7."
                    );
            }
        }

        // ---------------------------------------------------------
        // 13. Enter remarks
        // ---------------------------------------------------------

        System.out.println();

        System.out.print(
                "Enter remarks (press ENTER to keep current remarks): "
        );

        String remarks =
                scanner.nextLine().trim();

        if (remarks.isBlank()) {

            remarks =
                    selectedApplication.getRemarks();

        }

        // ---------------------------------------------------------
        // 14. Confirm update
        // ---------------------------------------------------------

        System.out.println();
        System.out.println(
                "=============================================================="
        );

        System.out.println(
                "                     UPDATE PREVIEW"
        );

        System.out.println(
                "=============================================================="
        );

        System.out.println();

        System.out.println(
                "Application ID : "
                        + selectedApplication.getApplicationId()
        );

        System.out.println(
                "Student        : "
                        + selectedApplication.getStudentName()
        );

        System.out.println(
                "Old Status     : "
                        + selectedApplication.getStatus()
        );

        System.out.println(
                "New Status     : "
                        + newStatus
        );

        System.out.println(
                "Remarks        : "
                        + displayValue(remarks)
        );

        System.out.println();

        System.out.print(
                "Confirm update? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println();
            System.out.println(
                    "Application update cancelled."
            );

            pause();
            return;
        }

        // ---------------------------------------------------------
        // 15. Update database
        // ---------------------------------------------------------

        boolean updated =
                applicationDAO.updateApplicationStatus(
                        selectedApplicationId,
                        recruiterId,
                        newStatus,
                        remarks
                );

        System.out.println();

        if (updated) {

            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "          APPLICATION UPDATED SUCCESSFULLY"
            );

            System.out.println(
                    "=================================================="
            );

            System.out.println();

            System.out.println(
                    "Application ID : "
                            + selectedApplicationId
            );

            System.out.println(
                    "New Status     : "
                            + newStatus
            );

            System.out.println(
                    "Remarks        : "
                            + displayValue(remarks)
            );

        } else {

            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "             UPDATE FAILED"
            );

            System.out.println(
                    "=================================================="
            );
        }

        pause();
    }

    // =========================================================
    // UTILITY METHODS
    // =========================================================

    private String truncate(String value, int maxLength) {

    if (value == null || value.isBlank()) {
        return "-";
    }

    if (value.length() <= maxLength) {
        return value;
    }

    return value.substring(0, maxLength - 3) + "...";
}

    private String displayValue(String value) {

        if (value == null || value.isBlank()) {
            return "-";
        }

        return value;
    }

    private void pause() {

        System.out.println();
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }
}