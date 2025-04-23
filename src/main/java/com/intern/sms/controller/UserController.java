package com.intern.sms.controller;

import com.intern.sms.util.PrintDB;
import com.khan.fazal.intern.PrintDash;
import com.intern.sms.service.UserService;
import com.intern.sms.service.UserServiceImpl;
import java.util.List;

public class UserController {
    private final UserService userService = new UserServiceImpl();

    public String login(String username, String password) {
        String role = userService.authenticateAndGetRole(username, password);
        return role;
    }

    public boolean signUp(String username, String password, String email, String role) {
        boolean registered = userService.registerUser(username, password, email, role);
        return registered;
    }

    public boolean forgotPassword(String targetUsername, String newPassword) {
        boolean reset = userService.resetPassword(targetUsername, newPassword);
        return reset;
    }

    public String addUser(String username, String password, String email, String role) {

        boolean success = userService.registerUser(username, password, email, role);
        return (success ? "User added successfully!" : "Failed to add user.");
    }

    public void viewUsers() {
        viewUsers("user");
    }

    public void viewUsers(String tableName) {
        List<String[]> table = PrintDB.fetchTable(tableName);
        PrintDash.printTable(table);
    }

    public String deleteUser(int userID) {
        boolean success = userService.deleteUser(userID);
        return (success ? "User deleted successfully!" : "Failed to delete user.");

    }

    public void manageSystemSettings() {
    }

    public void manageCourses() {
    }

    public void markAttendance() {
    }

    public void gradeStudents() {
    }

    public void viewCourses() {
    }

    public void trackAttendance() {
    }

    public void viewGrades() {
    }

    public void monitorStudentProgress() {
    }

    public void payFees() {
    }
}
