package com.intern.sms.service;

import org.mindrot.jbcrypt.BCrypt;
import com.intern.sms.util.DBConnection;

import java.sql.*;

public class AuthServiceImpl implements AuthService {

    // Hash the password before saving
    @Override
    public boolean registerUser(String username, String password, String email, String role) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Hash the password
        String query = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);  // Save the hashed password
            stmt.setString(3, email);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0;
        }
    }

    // Authenticate admin by checking admin credentials
    @Override
    public boolean authenticateAdmin(String username, String password) throws SQLException {
        String query = "SELECT password, role FROM user WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String role = rs.getString("role");

                // Check if the role is 'admin' and compare password
                if ("admin".equals(role) && BCrypt.checkpw(password, storedHash)) {
                    return true; // Admin authenticated successfully
                }
            }
        }
        return false; // Admin authentication failed
    }

    // Reset a user's password (only if admin is authenticated)
    @Override
    public boolean resetPassword(String adminUsername, String adminPassword, String targetUsername, String newPassword) throws SQLException {
        // First, authenticate the admin user
        if (!authenticateAdmin(adminUsername, adminPassword)) {
            return false; // Admin authentication failed
        }

        // Hash the new password before storing it
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the target user's password
        String updateQuery = "UPDATE user SET password = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, hashedNewPassword);  // Set the hashed new password
            stmt.setString(2, targetUsername);     // Target user's username

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if password reset was successful
        }
    }

    @Override
    public String authenticateAndGetRole(String username, String password) throws SQLException {
        String query = "SELECT password, role FROM user WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return rs.getString("role"); // return role on successful match
                }
            }
        }
        return null;
    }
}
