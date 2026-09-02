package com.smartplacement.ui;

import com.smartplacement.dao.JobDAO;
import com.smartplacement.model.Job;
import com.smartplacement.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminDashboard {

    private final User user;
    private final Scanner scanner;
    private final JobDAO jobDAO;

    public AdminDashboard(
            User user,
            Scanner scanner) {

        this.user = user;
        this.scanner = scanner;
        this.jobDAO = new JobDAO();
    }

    // =========================================================
    // SHOW DASHBOARD
    // =========================================================

    public void show() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "                 ADMIN DASHBOARD"
            );

            System.out.println(
                    "=================================================="
            );

            System.out.println();

            System.out.println(
                    "Welcome, " + user.getName()
            );

            System.out.println(
                    "Email: " + user.getEmail()
            );

            System.out.println();

            System.out.println(
                    "--------------------------------------------------"
            );

            System.out.println(
                    "                     MENU"
            );

            System.out.println(
                    "--------------------------------------------------"
            );

            System.out.println(
                    "1. Manage Pending Jobs"
            );

            System.out.println(
                    "2. Logout"
            );

            System.out.print(
                    "\nEnter choice: "
            );

            String choice =
                    scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    managePendingJobs();
                    break;

                case "2":

                    System.out.println();
                    System.out.println(
                            "Logging out..."
                    );

                    running = false;
                    break;

                default:

                    System.out.println();
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }


    // =========================================================
    // MANAGE PENDING JOBS
    // =========================================================

    private void managePendingJobs() {

        System.out.println();
        System.out.println(
                "=============================================================="
        );

        System.out.println(
                "                    PENDING JOBS"
        );

        System.out.println(
                "=============================================================="
        );

        List<Job> jobs =
                jobDAO.findPendingJobs();

        if (jobs.isEmpty()) {

            System.out.println();
            System.out.println(
                    "No pending jobs found."
            );

            pause();
            return;
        }

        System.out.println();

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

        int selectedJobId;

        while (true) {

            System.out.print(
                    "\nEnter Job ID to review (0 to return): "
            );

            String input =
                    scanner.nextLine().trim();

            try {

                selectedJobId =
                        Integer.parseInt(input);

                if (selectedJobId == 0) {
                    return;
                }

                boolean exists = false;

                for (Job job : jobs) {

                    if (job.getJobId()
                            == selectedJobId) {

                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    break;
                }

                System.out.println(
                        "Invalid Job ID."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        Job selectedJob = null;

        for (Job job : jobs) {

            if (job.getJobId()
                    == selectedJobId) {

                selectedJob = job;
                break;
            }
        }

        if (selectedJob == null) {

            System.out.println(
                    "Job not found."
            );

            pause();
            return;
        }

        // =====================================================
        // DISPLAY JOB DETAILS
        // =====================================================

        System.out.println();

        System.out.println(
                "=============================================================="
        );

        System.out.println(
                "                       JOB DETAILS"
        );

        System.out.println(
                "=============================================================="
        );

        System.out.println();

        System.out.println(
                "Job ID               : "
                        + selectedJob.getJobId()
        );

        System.out.println(
                "Position             : "
                        + displayValue(
                                selectedJob.getTitle()
                        )
        );

        System.out.println(
                "Description          : "
                        + displayValue(
                                selectedJob.getDescription()
                        )
        );

        System.out.println(
                "Location             : "
                        + displayValue(
                                selectedJob.getLocation()
                        )
        );

        System.out.println(
                "Employment Type      : "
                        + displayValue(
                                selectedJob.getEmploymentType()
                        )
        );

        System.out.println(
                "Salary               : Rs."
                        + selectedJob.getSalary()
        );

        System.out.println(
                "Minimum CGPA         : "
                        + selectedJob.getMinCgpa()
        );

        System.out.println(
                "Maximum Backlogs     : "
                        + selectedJob.getMaxBacklogs()
        );

        System.out.println(
                "Application Deadline : "
                        + selectedJob.getApplicationDeadline()
        );

        System.out.println(
                "Status               : "
                        + selectedJob.getStatus()
        );

        System.out.println(
                "Created At           : "
                        + selectedJob.getCreatedAt()
        );

        System.out.println();

        // =====================================================
        // ADMIN ACTION
        // =====================================================

        System.out.println(
                "--------------------------------------------------------------"
        );

        System.out.println(
                "                     ADMIN ACTION"
        );

        System.out.println(
                "--------------------------------------------------------------"
        );

        System.out.println(
                "1. Approve Job"
        );

        System.out.println(
                "2. Reject Job"
        );

        System.out.println(
                "0. Cancel"
        );

        System.out.print(
                "\nSelect action: "
        );

        String choice =
                scanner.nextLine().trim();

        String newStatus;

        switch (choice) {

            case "1":
                newStatus = "APPROVED";
                break;

            case "2":
                newStatus = "REJECTED";
                break;

            case "0":
                return;

            default:

                System.out.println(
                        "Invalid choice."
                );

                pause();
                return;
        }

        // =====================================================
        // CONFIRMATION
        // =====================================================

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
                "Job ID      : "
                        + selectedJob.getJobId()
        );

        System.out.println(
                "Position    : "
                        + selectedJob.getTitle()
        );

        System.out.println(
                "Old Status  : "
                        + selectedJob.getStatus()
        );

        System.out.println(
                "New Status  : "
                        + newStatus
        );

        System.out.println();

        System.out.print(
                "Confirm update? (Y/N): "
        );

        String confirm =
                scanner.nextLine().trim();

        if (!"Y".equalsIgnoreCase(confirm)) {

            System.out.println();
            System.out.println(
                    "Update cancelled."
            );

            pause();
            return;
        }

        // =====================================================
        // UPDATE DATABASE
        // =====================================================

        boolean updated =
                jobDAO.updateJobStatus(
                        selectedJob.getJobId(),
                        newStatus
                );

        if (updated) {

            System.out.println();

            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "              JOB UPDATED SUCCESSFULLY"
            );

            System.out.println(
                    "=================================================="
            );

            System.out.println();

            System.out.println(
                    "Job ID     : "
                            + selectedJob.getJobId()
            );

            System.out.println(
                    "New Status : "
                            + newStatus
            );

        } else {

            System.out.println();

            System.out.println(
                    "Unable to update job."
            );
        }

        pause();
    }


    // =========================================================
    // UTILITY METHODS
    // =========================================================

    private String truncate(
            String value,
            int maxLength) {

        if (value == null) {
            return "";
        }

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(
                0,
                maxLength - 3
        ) + "...";
    }


    private String displayValue(
            String value) {

        if (value == null ||
                value.isBlank()) {

            return "-";
        }

        return value;
    }


    private void pause() {

        System.out.println();

        System.out.print(
                "Press ENTER to continue..."
        );

        scanner.nextLine();
    }
}