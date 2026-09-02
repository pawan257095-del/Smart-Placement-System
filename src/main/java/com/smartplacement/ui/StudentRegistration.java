package com.smartplacement.ui;

import com.smartplacement.dao.StudentDAO;
import com.smartplacement.dao.UserDAO;
import com.smartplacement.util.PasswordUtil;

import java.util.Scanner;

public class StudentRegistration {

    private final Scanner scanner;

    private final UserDAO userDAO;
    private final StudentDAO studentDAO;

    public StudentRegistration(Scanner scanner) {

        this.scanner = scanner;

        this.userDAO = new UserDAO();
        this.studentDAO = new StudentDAO();
    }

    public void register() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("                STUDENT SIGN UP");
        System.out.println("==================================================");

        // -----------------------------------------------------
        // Personal Information
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // Check existing email
        // -----------------------------------------------------

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

        // -----------------------------------------------------
        // Academic Information
        // -----------------------------------------------------

        System.out.println();
        System.out.println("Academic Information");
        System.out.println("--------------------");

        System.out.print("Enrollment Number: ");
        String enrollmentNo =
                scanner.nextLine().trim();

        System.out.print("Course: ");
        String course =
                scanner.nextLine().trim();

        System.out.print("Branch: ");
        String branch =
                scanner.nextLine().trim();

        int semester;

        while (true) {

            System.out.print("Semester: ");

            try {

                semester =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                if (semester >= 1 && semester <= 8) {
                    break;
                }

                System.out.println(
                        "Semester must be between 1 and 8."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid semester."
                );
            }
        }

        double cgpa;

        while (true) {

            System.out.print("CGPA (0 - 10): ");

            try {

                cgpa =
                        Double.parseDouble(
                                scanner.nextLine().trim()
                        );

                if (cgpa >= 0 && cgpa <= 10) {
                    break;
                }

                System.out.println(
                        "CGPA must be between 0 and 10."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid CGPA."
                );
            }
        }

        int backlogs;

        while (true) {

            System.out.print("Backlogs: ");

            try {

                backlogs =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                if (backlogs >= 0) {
                    break;
                }

                System.out.println(
                        "Backlogs cannot be negative."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        int graduationYear;

        while (true) {

            System.out.print("Graduation Year: ");

            try {

                graduationYear =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                if (graduationYear >= 2020 &&
                        graduationYear <= 2100) {

                    break;
                }

                System.out.println(
                        "Please enter a valid graduation year."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid year."
                );
            }
        }

        // -----------------------------------------------------
        // Password
        // -----------------------------------------------------

        System.out.println();
        System.out.println("Account Security");
        System.out.println("----------------");

        System.out.print("Password: ");
        String password =
                scanner.nextLine();

        if (password.length() < 6) {

            System.out.println();
            System.out.println(
                    "Password must contain at least 6 characters."
            );

            pause();
            return;
        }

        System.out.print("Confirm Password: ");
        String confirmPassword =
                scanner.nextLine();

        if (!password.equals(confirmPassword)) {

            System.out.println();
            System.out.println(
                    "Passwords do not match."
            );

            pause();
            return;
        }

        // -----------------------------------------------------
        // Preview
        // -----------------------------------------------------

        System.out.println();
        System.out.println("==================================================");
        System.out.println("             REGISTRATION PREVIEW");
        System.out.println("==================================================");

        System.out.println();
        System.out.println("Name             : " + name);
        System.out.println("Email            : " + email);
        System.out.println("Phone            : " + phone);
        System.out.println("Enrollment No.   : " + enrollmentNo);
        System.out.println("Course           : " + course);
        System.out.println("Branch           : " + branch);
        System.out.println("Semester         : " + semester);
        System.out.println("CGPA             : " + cgpa);
        System.out.println("Backlogs         : " + backlogs);
        System.out.println("Graduation Year  : " + graduationYear);
        System.out.println("Role             : STUDENT");
        System.out.println("Status           : ACTIVE");

        System.out.println();
        System.out.print(
                "Create this account? (Y/N): "
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

        // -----------------------------------------------------
        // Hash password
        // -----------------------------------------------------

        String passwordHash =
                PasswordUtil.hashPassword(password);

        // -----------------------------------------------------
        // Create user
        // -----------------------------------------------------

        System.out.println();
        System.out.println(
                "Creating student account..."
        );

        int userId =
                userDAO.createUser(
                        name,
                        email,
                        passwordHash,
                        "STUDENT",
                        "ACTIVE"
                );

        if (userId == -1) {

            System.out.println();
            System.out.println(
                    "Registration failed."
            );

            pause();
            return;
        }

        // -----------------------------------------------------
        // Create student profile
        // -----------------------------------------------------

        boolean studentCreated =
                studentDAO.createStudent(
                        userId,
                        enrollmentNo,
                        phone,
                        course,
                        branch,
                        semester,
                        cgpa,
                        backlogs,
                        graduationYear
                );

        if (!studentCreated) {

            System.out.println();
            System.out.println(
                    "Student profile creation failed."
            );

            System.out.println(
                    "The user account was created, "
                            + "but the student profile could not be created."
            );

            pause();
            return;
        }

        // -----------------------------------------------------
        // Success
        // -----------------------------------------------------

        System.out.println();
        System.out.println(
                "=================================================="
        );

        System.out.println(
                "        STUDENT REGISTRATION SUCCESSFUL"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println();

        System.out.println(
                "User ID          : " + userId
        );

        System.out.println(
                "Name             : " + name
        );

        System.out.println(
                "Email            : " + email
        );

        System.out.println(
                "Role             : STUDENT"
        );

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