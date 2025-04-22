package com.intern.sms.service;

import java.sql.SQLException;

public interface AuthService {

    boolean registerUser(String username, String password, String email, String role) throws SQLException;

    boolean authenticateAdmin(String username, String password) throws SQLException;

    boolean resetPassword(String adminUsername, String adminPassword, String targetUsername, String newPassword) throws SQLException;

    String authenticateAndGetRole(String username, String password) throws SQLException;
}
