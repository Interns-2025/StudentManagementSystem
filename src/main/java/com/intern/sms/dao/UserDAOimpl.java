package com.intern.sms.dao;

import com.intern.sms.model.User;
import com.intern.sms.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.intern.sms.util.Constants.*;
import static com.intern.sms.util.DBConstants.*;

public class UserDAOimpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOimpl.class);

    @Override
    public boolean addUser(String username, String password, String email, String role) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try (PreparedStatement stmt = prepare(ADD_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, role);

            boolean success = stmt.executeUpdate() > 0;
            if (success) {
                logger.info("User '{}' added successfully with role '{}'.", username, role);
            } else {
                logger.warn("Failed to add user '{}'.", username);
            }
            return success;
        } catch (Exception e) {
            logger.error("Exception while adding user '{}': {}", username, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean authenticateAdmin(String username, String password) {
        try (PreparedStatement stmt = prepare(AUTH_ADMIN)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString(PASSWORD);
                String role = rs.getString(ROLE);
                if (ADMIN.equals(role) && BCrypt.checkpw(password, storedHash)) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Error authenticating admin '{}': {}", username, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean resetPassword(String targetUsername, String newPassword) {
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        try (PreparedStatement stmt = prepare(RESET_PASS)) {
            stmt.setString(1, hashedNewPassword);
            stmt.setString(2, targetUsername);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Password reset successful for user '{}'.", targetUsername);
            } else {
                logger.warn("Password reset failed for user '{}'.", targetUsername);
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Error resetting password for '{}': {}", targetUsername, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String authenticateAndGetRole(String username, String password) {
        try (PreparedStatement stmt = prepare(AUTH_AND_ROLE)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString(PASSWORD);
                if (BCrypt.checkpw(password, storedHash)) {
                    String role = rs.getString(ROLE);
                    logger.info("User '{}' authenticated successfully with role '{}'.", username, role);
                    return role;
                }
            }
        } catch (Exception e) {
            logger.error("Error during authentication for '{}': {}", username, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User getUserById(int userID) {
        try (PreparedStatement stmt = prepare(GET_BY_ID)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (Exception e) {
            logger.error("Error fetching user by ID '{}': {}", userID, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = prepare(GET_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (Exception e) {
            logger.error("Error fetching all users: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getUserID());
            stmt.executeUpdate();
            logger.info("Updated user '{}'.", user.getUsername());
        } catch (Exception e) {
            logger.error("Error updating user '{}': {}", user.getUsername(), e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteUser(int userID) {
        try (PreparedStatement stmt = prepare(DELETE_USER)) {
            stmt.setInt(1, userID);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Deleted user with ID '{}'.", userID);
            } else {
                logger.warn("No user found to delete with ID '{}'.", userID);
            }
            return rows > 0;
        } catch (Exception e) {
            logger.error("Error deleting user with ID '{}': {}", userID, e.getMessage(), e);
            return false;
        }
    }

    private User extractUser(ResultSet rs) {
        try {
            return new User(
                    rs.getInt(USER_ID),
                    rs.getString(USERNAME),
                    rs.getString(PASSWORD),
                    rs.getString(EMAIL),
                    rs.getString(ROLE)
            );
        } catch (Exception e) {
            logger.error("Error extracting user from ResultSet: {}", e.getMessage(), e);
            return null;
        }
    }

    public PreparedStatement prepare(String query) throws SQLException {
        Connection conn = DBConnection.getConnection();
        return conn.prepareStatement(query);
    }
}
