package com.smartplacement.ui;

import com.smartplacement.dao.RecruiterDAO;
import com.smartplacement.dao.UserDAO;
import com.smartplacement.util.PasswordUtil;

import java.util.Scanner;

public class RecruiterRegistration {

    private final Scanner scanner;

    private final UserDAO userDAO;
    private final RecruiterDAO recruiterDAO;

    public RecruiterRegistration(Scanner scanner) {

        this.scanner = scanner;

        this.userDAO = new UserDAO();
        this.recruiterDAO = new RecruiterDAO();
    }

    public void register() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("               RECRUITER SIGN UP");
        System.out.println("==================================================");

        // =====================================================
        // PERSONAL INFORMATION
        // =====================================================

        System.out.println();
        System.out.println("Personal Information");
        System.out.println("--------------------");

        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            System.out.println("Name cannot be empty.");
            pause();
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        if (email.isBlank()) {
            System.out.println("Email cannot be empty.");
            pause();
            return;
        }

        // Check existing email
        if (userDAO.findByEmail(email) != null) {

            System.out.println();
            System.out.println(
                    "An account with this email already exists."
            );

            pause();
            return;
        }

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Designation: ");
        String designation = scanner.nextLine().trim();

        // =====================================================
        // COMPANY INFORMATION
        // =====================================================

        System.out.println();
        System.out.println("Company Information");
        System.out.println("-------------------");

        System.out.print("Company Name: ");
        String companyName = scanner.nextLine().trim();

        if (companyName.isBlank()) {
            System.out.println("Company name cannot be empty.");
            pause();
            return;
        }

        // Check whether company already exists
        int existingCompanyId =
                recruiterDAO.findCompanyByName(companyName);

        if (existingCompanyId != -1) {

            System.out.println();
            System.out.println(
                    "A company with this name already exists."
            );

            System.out.println(
                    "Existing Company ID: " + existingCompanyId
            );

            System.out.println(
                    "Please contact the administrator "
                            + "to add another recruiter to this company."
            );

            pause();
            return;
        }

        System.out.print("Industry: ");
        String industry = scanner.nextLine().trim();

        System.out.print("Website: ");
        String website = scanner.nextLine().trim();

        System.out.print("Company Email: ");
        String companyEmail = scanner.nextLine().trim();

        System.out.print("Company Phone: ");
        String companyPhone = scanner.nextLine().trim();

        System.out.print("Company Address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Company Description: ");
        String description = scanner.nextLine().trim();

        // =====================================================
        // PASSWORD
        // =====================================================

        System.out.println();
        System.out.println("Account Security");
        System.out.println("----------------");

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (password.length() < 6) {

            System.out.println();
            System.out.println(
                    "Password must contain at least 6 characters."
            );

            pause();
            return;
        }

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {

            System.out.println();
            System.out.println(
                    "Passwords do not match."
            );

            pause();
            return;
        }

        // =====================================================
        // REGISTRATION PREVIEW
        // =====================================================

        System.out.println();
        System.out.println("==================================================");
        System.out.println("            REGISTRATION PREVIEW");
        System.out.println("==================================================");

        System.out.println();
        System.out.println("Recruiter Information");
        System.out.println("---------------------");
        System.out.println("Name             : " + name);
        System.out.println("Email            : " + email);
        System.out.println("Phone            : " + phone);
        System.out.println("Designation      : " + designation);

        System.out.println();
        System.out.println("Company Information");
        System.out.println("-------------------");
        System.out.println("Company Name     : " + companyName);
        System.out.println("Industry         : " + industry);
        System.out.println("Website          : " + website);
        System.out.println("Company Email    : " + companyEmail);
        System.out.println("Company Phone    : " + companyPhone);
        System.out.println("Address          : " + address);
        System.out.println("Description      : " + description);

        System.out.println();
        System.out.println("Role             : RECRUITER");
        System.out.println("Status           : ACTIVE");

        System.out.println();
        System.out.print(
                "Create this recruiter account? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println();
            System.out.println(
                    "Registration cancelled."
            );

            pause();
            return;
        }

        // =====================================================
        // HASH PASSWORD
        // =====================================================

        String passwordHash =
                PasswordUtil.hashPassword(password);

        // =====================================================
        // CREATE USER
        // =====================================================

        System.out.println();
        System.out.println(
                "Creating recruiter account..."
        );

        int userId =
                userDAO.createUser(
                        name,
                        email,
                        passwordHash,
                        "RECRUITER",
                        "ACTIVE"
                );

        if (userId == -1) {

            System.out.println();
            System.out.println(
                    "Recruiter account creation failed."
            );

            pause();
            return;
        }

        System.out.println(
                "DEBUG: User ID = " + userId
        );

        // =====================================================
        // CREATE COMPANY
        // =====================================================

        System.out.println(
                "Creating company profile..."
        );

        int companyId =
                recruiterDAO.createCompany(
                        companyName,
                        industry,
                        website,
                        companyEmail,
                        companyPhone,
                        address,
                        description
                );

        if (companyId == -1) {

            System.out.println();
            System.out.println(
                    "Company creation failed."
            );

            System.out.println(
                    "User account was created with User ID: "
                            + userId
            );

            pause();
            return;
        }

        System.out.println(
                "DEBUG: Company ID = " + companyId
        );

        // =====================================================
        // CREATE RECRUITER
        // =====================================================

        System.out.println(
                "Creating recruiter profile..."
        );

        int recruiterId =
                recruiterDAO.createRecruiter(
                        userId,
                        companyId
                );

        if (recruiterId == -1) {

            System.out.println();
            System.out.println(
                    "Recruiter profile creation failed."
            );

            System.out.println(
                    "User ID    : " + userId
            );

            System.out.println(
                    "Company ID : " + companyId
            );

            pause();
            return;
        }

        // =====================================================
        // SUCCESS
        // =====================================================

        System.out.println();
        System.out.println("==================================================");
        System.out.println(
                "       RECRUITER REGISTRATION SUCCESSFUL"
        );
        System.out.println("==================================================");

        System.out.println();
        System.out.println("User ID       : " + userId);
        System.out.println("Recruiter ID  : " + recruiterId);
        System.out.println("Company ID    : " + companyId);
        System.out.println("Name          : " + name);
        System.out.println("Email         : " + email);
        System.out.println("Company       : " + companyName);
        System.out.println("Role          : RECRUITER");

        System.out.println();
        System.out.println(
                "You can now login using your email and password."
        );

        System.out.println(
                "=================================================="
        );

        pause();
    }

    private void pause() {

        System.out.println();
        System.out.println(
                "Press ENTER to continue..."
        );

        scanner.nextLine();
    }
}