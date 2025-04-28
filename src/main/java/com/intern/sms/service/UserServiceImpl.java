package com.intern.sms.service;

import com.intern.sms.dao.UserDAO;
import com.intern.sms.dao.UserDAOimpl;

public class UserServiceImpl implements UserService {
    UserDAO userDAO = new UserDAOimpl();

    // Hash the password before saving
    @Override
    public boolean registerUser(String username, String password, String email, String role) {
        return userDAO.addUser(username, password, email, role);
    }

    // Authenticate admin by checking admin credentials
    @Override
    public boolean authenticateAdmin(String username, String password) {
        return userDAO.authenticateAdmin(username, password);
    }

    // Reset a user's password (only if admin is authenticated)
    @Override
    public boolean resetPassword(String targetUsername, String newPassword) {
        return userDAO.resetPassword(targetUsername, newPassword);
    }

    @Override
    public String authenticateAndGetRole(String username, String password) {
        return userDAO.authenticateAndGetRole(username, password);
    }

    public boolean deleteUser(int userID) {
        return userDAO.deleteUser(userID);
    }
}
