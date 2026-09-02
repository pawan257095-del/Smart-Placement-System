package com.smartplacement.service;

import com.smartplacement.dao.UserDAO;
import com.smartplacement.model.User;
import com.smartplacement.util.PasswordUtil;

public class LoginService {

    private final UserDAO userDAO;

    public LoginService() {
        userDAO = new UserDAO();
    }

    public User login(String email, String password) {

        User user = userDAO.findByEmail(email);

        // User not found
        if (user == null) {
            return null;
        }

        // Account is not active
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            return null;
        }

        // Verify password
        boolean passwordCorrect = PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash()
        );

        if (!passwordCorrect) {
            return null;
        }

        return user;
    }
}