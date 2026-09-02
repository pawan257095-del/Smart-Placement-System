package com.smartplacement;
import java.util.Scanner;

import com.smartplacement.model.User;
import com.smartplacement.service.LoginService;
import com.smartplacement.service.PasswordResetService;
import com.smartplacement.ui.AdminDashboard;
import com.smartplacement.ui.RecruiterDashboard;
import com.smartplacement.ui.RecruiterRegistration;
import com.smartplacement.ui.StudentDashboard;
import com.smartplacement.ui.StudentRegistration;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        LoginService loginService = new LoginService();
        PasswordResetService passwordResetService =
        new PasswordResetService();

        StudentRegistration studentRegistration =
        new StudentRegistration(scanner);

        RecruiterRegistration recruiterRegistration =
        new RecruiterRegistration(scanner);

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("     SMART PLACEMENT & RECRUITMENT SYSTEM");
            System.out.println("==============================================");

            System.out.println();
            System.out.println("1. Login");
            System.out.println("2. Student Sign Up");
            System.out.println("3. Recruiter Sign Up");
            System.out.println("4. Exit");

            System.out.print("\nEnter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

    boolean loginMenuRunning = true;

    while (loginMenuRunning) {

        System.out.println();
        System.out.println(
                "----------------------------------------------"
        );

        System.out.println(
                "                    LOGIN"
        );

        System.out.println(
                "----------------------------------------------"
        );

        System.out.println();

        System.out.println("1. Login");
        System.out.println("2. Forgot Password");
        System.out.println("3. Back");

        System.out.print("\nEnter choice: ");

        String loginChoice =
                scanner.nextLine().trim();

        switch (loginChoice) {

            // =================================================
            // LOGIN
            // =================================================

            case "1":

                System.out.print("Email: ");
                String email =
                        scanner.nextLine().trim();

                System.out.print("Password: ");
                String password =
                        scanner.nextLine();

                System.out.println();
                System.out.println(
                        "Authenticating..."
                );

                User user =
                        loginService.login(
                                email,
                                password
                        );

                if (user != null) {

                    System.out.println();
                    System.out.println(
                            "LOGIN SUCCESSFUL!"
                    );

                    System.out.println(
                            "----------------------------------------------"
                    );

                    System.out.println(
                            "Welcome, "
                                    + user.getName()
                    );

                    System.out.println(
                            "Role: "
                                    + user.getRole()
                    );

                    if (
                            "STUDENT".equalsIgnoreCase(
                                    user.getRole()
                            )
                    ) {

                        StudentDashboard dashboard =
                                new StudentDashboard(
                                        user,
                                        scanner
                                );

                        dashboard.show();

                    } else if (
                            "RECRUITER".equalsIgnoreCase(
                                    user.getRole()
                            )
                    ) {

                        RecruiterDashboard dashboard =
                                new RecruiterDashboard(
                                        user,
                                        scanner
                                );

                        dashboard.show();

                    } else if (
                            "ADMIN".equalsIgnoreCase(
                                    user.getRole()
                            )
                    ) {

                        AdminDashboard dashboard =
                                new AdminDashboard(
                                        user,
                                        scanner
                                );

                        dashboard.show();
                    }

                    loginMenuRunning = false;

                } else {

                    System.out.println();
                    System.out.println(
                            "LOGIN FAILED!"
                    );

                    System.out.println(
                            "Invalid email or password."
                    );
                }

                break;


            // =================================================
            // FORGOT PASSWORD
            // =================================================
            case "2":

    System.out.println();
    System.out.println(
            "=================================================="
    );

    System.out.println(
            "                 FORGOT PASSWORD"
    );

    System.out.println(
            "=================================================="
    );

    System.out.println();

    System.out.print(
            "Enter your registered email: "
    );

    String resetEmail =
            scanner.nextLine().trim();

    if (resetEmail.isBlank()) {

        System.out.println();
        System.out.println(
                "Email cannot be empty."
        );

        break;
    }

    User resetUser =
            passwordResetService.findActiveUser(
                    resetEmail
            );

    if (resetUser == null) {

        System.out.println();
        System.out.println(
                "No active account found with this email."
        );

        break;
    }

    // ---------------------------------------------------------
    // Generate OTP
    // ---------------------------------------------------------

    String verificationCode =
            passwordResetService
                    .generateVerificationCode();

    System.out.println();
    System.out.println(
            "Sending verification code..."
    );

    // ---------------------------------------------------------
    // Send OTP to registered email
    // ---------------------------------------------------------

    boolean emailSent =
            passwordResetService
                    .sendVerificationCode(
                            resetUser,
                            verificationCode
                    );

    if (!emailSent) {

        System.out.println();
        System.out.println(
                "Unable to send verification code."
        );

        System.out.println(
                "Please try again later."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "Verification code sent successfully!"
    );

    System.out.println(
            "Please check your registered email:"
    );

    System.out.println(
            resetUser.getEmail()
    );

    // ---------------------------------------------------------
    // Verify OTP
    // ---------------------------------------------------------

    System.out.println();

    System.out.print(
            "Enter verification code: "
    );

    String enteredCode =
            scanner.nextLine().trim();

    if (!verificationCode.equals(
            enteredCode)) {

        System.out.println();
        System.out.println(
                "Invalid verification code."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "Verification successful!"
    );

    // ---------------------------------------------------------
    // New password
    // ---------------------------------------------------------

    System.out.print(
            "Enter new password: "
    );

    String newPassword =
            scanner.nextLine();

    System.out.print(
            "Confirm new password: "
    );

    String confirmPassword =
            scanner.nextLine();

    if (newPassword.isBlank()) {

        System.out.println();
        System.out.println(
                "Password cannot be empty."
        );

        break;
    }

    if (!newPassword.equals(
            confirmPassword)) {

        System.out.println();
        System.out.println(
                "Passwords do not match."
        );

        break;
    }

    // ---------------------------------------------------------
    // Update password
    // ---------------------------------------------------------

    boolean passwordUpdated =
            passwordResetService.resetPassword(
                    resetUser.getUserId(),
                    newPassword
            );

    if (passwordUpdated) {

        System.out.println();

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "             PASSWORD RESET SUCCESSFUL"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println();

        System.out.println(
                "Your password has been updated successfully."
        );

        System.out.println(
                "You can now login using your new password."
        );

    } else {

        System.out.println();

        System.out.println(
                "Unable to reset password."
        );
    }

    break;
            

            


            // =================================================
            // BACK
            // =================================================

            case "3":

                loginMenuRunning = false;
                break;


            default:

                System.out.println();
                System.out.println(
                        "Invalid choice. Please select 1-3."
                );
        }
    }

    break;
                case "2":

        studentRegistration.register();
        break;

    case "3":

    recruiterRegistration.register();
    break;

    case "4":

        System.out.println();
        System.out.println("Thank you for using Smart Placement System.");

    running = false;
    break;

    default:

        System.out.println();
        System.out.println(
                "Invalid choice. Please try again."
        );
}
}

        scanner.close();
    }
}