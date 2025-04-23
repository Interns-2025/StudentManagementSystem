package com.intern.sms.controller;

import com.intern.PrintDB;
import com.khan.fazal.intern.PrintDash;
import com.intern.sms.dao.UserDAO;
import com.intern.sms.service.AuthService;
import com.intern.sms.service.AuthServiceImpl;
import com.intern.sms.view.ConsoleView;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private final ConsoleView view = new ConsoleView();
    private final AuthService authService = new AuthServiceImpl();

    public String login(String username, String password) {
        String role = authService.authenticateAndGetRole(username, password);
        return role;
    }

    public boolean signUp(String username, String password, String email,String role) throws SQLException {
        boolean registered = authService.registerUser(username, password, email, role);
        return registered;
    }

    public boolean forgotPassword(String targetUsername, String newPassword) {
        boolean reset = authService.resetPassword(targetUsername, newPassword);
    }

    public String addUser(String username, String password, String email, String role) {

        try {
            boolean success = authService.registerUser(username, password, email, role);
            return(success ? "User added successfully!" : "Failed to add user.");
        } catch (SQLException e) {
            return("Error: " + e.getMessage());
        }
    }

    public void viewUsers() {
        viewUsers("user");
    }

    public void viewUsers(String tableName) {
        List<String[]> table = PrintDB.fetchTable(tableName);
        PrintDash.printTable(table);
    }

    public String deleteUser(int userID) {
        try {
            UserDAO.deleteUser(userID);
            return "User deleted successfully.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public void manageSystemSettings() {}

    public void manageCourses() {}

    public void markAttendance() {}

    public void gradeStudents() {}

    public void viewCourses() {}

    public void trackAttendance() {}

    public void viewGrades() {}

    public void monitorStudentProgress() {}

    public void payFees() {}
}
