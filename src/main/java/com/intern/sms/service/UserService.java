package com.intern.sms.service;

import java.sql.SQLException;

public interface UserService {

    boolean registerUser(String username, String password, String email, String role);

    boolean authenticateAdmin(String username, String password);

    boolean resetPassword(String targetUsername, String newPassword);

    String authenticateAndGetRole(String username, String password);

    boolean deleteUser(int userID);
}
