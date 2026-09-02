package com.smartplacement.service;

import com.smartplacement.dao.UserDAO;
import com.smartplacement.model.User;
import com.smartplacement.util.PasswordUtil;

import java.util.Random;

public class PasswordResetService {

    private final UserDAO userDAO;
    private final Random random;
    private final EmailService emailService;

    public PasswordResetService() {

        userDAO = new UserDAO();
        random = new Random();
        emailService = new EmailService();
    }

    /**
     * Finds an active user by email.
     */
    public User findActiveUser(String email) {

        if (email == null ||
                email.isBlank()) {

            return null;
        }

        User user =
                userDAO.findByEmail(
                        email.trim()
                );

        if (user == null) {
            return null;
        }

        if (!"ACTIVE".equalsIgnoreCase(
                user.getStatus())) {

            return null;
        }

        return user;
    }

    /**
     * Generates a six-digit OTP.
     */
    public String generateVerificationCode() {

        int code =
                100000 +
                random.nextInt(900000);

        return String.valueOf(code);
    }

    /**
     * Sends OTP to the user's registered email.
     */
    public boolean sendVerificationCode(
            User user,
            String otp) {

        if (user == null ||
                otp == null ||
                otp.isBlank()) {

            return false;
        }

        return emailService.sendOtpEmail(
                user.getEmail(),
                user.getName(),
                otp
        );
    }

    /**
     * Updates the user's password using BCrypt.
     */
    public boolean resetPassword(
            int userId,
            String newPassword) {

        if (newPassword == null ||
                newPassword.isBlank()) {

            return false;
        }

        String passwordHash =
                PasswordUtil.hashPassword(
                        newPassword
                );

        return userDAO.updatePassword(
                userId,
                passwordHash
        );
    }
}