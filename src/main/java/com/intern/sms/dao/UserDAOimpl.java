package com.intern.sms.dao;

import com.intern.sms.util.DBConnection;
import com.intern.sms.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.intern.sms.util.DBQueries.*;

public class UserDAOimpl implements UserDAO {

    @Override
    public boolean addUser(String username, String password, String email, String role) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Hash the password

        try (PreparedStatement stmt = prepare(ADD_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateAdmin(String username, String password){
        try (PreparedStatement stmt = prepare(AUTH_ADMIN)) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false; // Admin authentication failed
    }

    @Override
    public boolean resetPassword(String targetUsername, String newPassword) {
        // Hash the new password before storing it
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the target user's password
        try (PreparedStatement stmt = prepare(RESET_PASS)) {
            stmt.setString(1, hashedNewPassword);  // Set the hashed new password
            stmt.setString(2, targetUsername);     // Target user's username

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if password reset was successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String authenticateAndGetRole(String username, String password) {
        try (PreparedStatement stmt = prepare(AUTH_AND_ROLE)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return rs.getString("role"); // return role on successful match
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = prepare(GET_ALL_USERS);
             ResultSet rs = stmt.executeQuery(stmt.toString())) {

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return users;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteUser(int userID) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private User extractUser(ResultSet rs) {
        try {
            return new User(
                    rs.getInt("userID"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PreparedStatement prepare(String query) throws SQLException {
        Connection conn = DBConnection.getConnection();
        return conn.prepareStatement(query);
    }
}
