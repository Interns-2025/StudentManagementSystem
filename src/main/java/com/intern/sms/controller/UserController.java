package com.intern.sms.controller;

import com.intern.sms.util.PrintDB;
import com.khan.fazal.intern.PrintDash;
import com.intern.sms.service.UserService;
import com.intern.sms.service.UserServiceImpl;
import java.util.List;
import static com.intern.sms.util.Constants.*;

public class UserController {
    private final UserService userService = new UserServiceImpl();

    public String login(String username, String password) {
        return userService.authenticateAndGetRole(username, password);
    }

    public boolean signUp(String username, String password, String email, String role) {
        return userService.registerUser(username, password, email, role);
    }

    public boolean forgotPassword(String targetUsername, String newPassword) {
        return userService.resetPassword(targetUsername, newPassword);
    }

    public String addUser(String username, String password, String email, String role) {

        boolean success = userService.registerUser(username, password, email, role);
        return (success ? SUCCESS_ADD : FAIL_ADD);
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
        return (success ? SUCCESS_DEL : FAIL_DEL);

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
