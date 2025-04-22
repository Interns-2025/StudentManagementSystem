package com.intern.sms.controller;

import com.intern.PrintDB;
import com.khan.fazal.intern.PrintDash;
import com.intern.sms.dao.UserDAO;
import com.intern.sms.service.AuthService;
import com.intern.sms.service.AuthServiceImpl;
import com.intern.sms.view.ConsoleView;
import java.sql.SQLException;

public class UserController {
    private final ConsoleView view = new ConsoleView();
    private final AuthService authService = new AuthServiceImpl();
    private UserDAO UserDAO;

    public void start() {
        while (true) {
            view.showMenu("User Management Menu", new String[] {"Login", "Sign Up", "Forgot Password", "Exit"});
            int choice = view.getChoice();

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> signUp();
                    case 3 -> forgotPassword();
                    case 4 -> {
                        view.showMessage("Goodbye!");
                        return;
                    }
                    default -> view.showMessage("Invalid choice.");
                }
            } catch (Exception e) {
                view.showMessage("Error: " + e.getMessage());
            }
        }
    }

    private void login() throws SQLException {
        String username = view.prompt("Enter username: ");
        String password = view.prompt("Enter password: ");

        String role = authService.authenticateAndGetRole(username, password);

        if (role == null) {
            view.showMessage("Invalid credentials.");
            return;
        }

        view.showMessage("Login successful! Welcome, " + username);

        switch (role.toLowerCase()) {
            case "admin" -> adminDashboard();
            case "teacher" -> teacherDashboard();
            case "student" -> studentDashboard();
            case "parent" -> parentDashboard();
            default -> view.showMessage("Unknown role.");
        }
    }

    private void signUp() throws SQLException {
        String username = view.prompt("Choose a username: ");
        String password = view.prompt("Choose a password: ");
        String email = view.prompt("Enter your email: ");
        String role = view.prompt("Enter role (teacher/student/parent): ");
        if (role.equals("admin")) {
            view.showMessage("\n--- Admin Authentication ---");
            String adminUsername = view.prompt("Enter admin username: ");
            String adminPassword = view.prompt("Enter admin password: ");
            authService.authenticateAdmin(adminUsername, adminPassword);
        }
        boolean registered = authService.registerUser(username, password, email, role);
        view.showMessage(registered ? "User registered successfully." : "Username already exists.");
    }

    private void forgotPassword() throws SQLException {
        view.showMessage("\n--- Admin Authentication ---");
        String adminUsername = view.prompt("Enter admin username: ");
        String adminPassword = view.prompt("Enter admin password: ");

        String targetUsername = view.prompt("Enter username of user to reset password: ");
        String newPassword = view.prompt("Enter new password for " + targetUsername + ": ");

        boolean reset = authService.resetPassword(adminUsername, adminPassword, targetUsername, newPassword);
        view.showMessage(reset ? "Password reset successfully." : "Failed to reset password. Either admin auth failed or user not found.");
    }

    private void adminDashboard() {
        while (true) {
            view.showMenu("Admin Dashboard", new String[] {"Manage Users", "Manage System Settings", "Logout"});
            int choice = view.getChoice();
            switch (choice) {
                case 1 -> manageUsers();
                case 2 -> manageSystemSettings();
                case 3 -> {
                    view.showMessage("Logging out...");
                    return;
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    }

    private void manageUsers() {
        while (true) {
            view.showMenu("Manage Users", new String[] {"Add User", "View Users", "Delete User", "Back to Admin Dashboard"});
            int choice = view.getChoice();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> deleteUser();
                case 4 -> {
                    return; // Back to Admin Dashboard
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    }

    private void addUser() {
        String username = view.prompt("Enter username: ");
        String password = view.prompt("Enter password: ");
        String email = view.prompt("Enter email: ");
        String role = view.prompt("Enter role (admin/teacher/student/parent): ");

        try {
            boolean success = authService.registerUser(username, password, email, role);
            view.showMessage(success ? "User added successfully!" : "Failed to add user.");
        } catch (SQLException e) {
            view.showMessage("Error: " + e.getMessage());
        }
    }

    private void viewUsers() {
        PrintDash.printTable(PrintDB.fetchTable("user"));
    }

    private void deleteUser() {
        int userID = Integer.parseInt(view.prompt("Enter user ID to delete: "));
        try {
            UserDAO.deleteUser(userID);
            view.showMessage("User deleted successfully.");
        } catch (SQLException e) {
            view.showMessage("Error: " + e.getMessage());
        }
    }

    private void manageSystemSettings() {
        view.showMessage("System settings are under development.");
    }

    private void teacherDashboard() {
        while (true) {
            view.showMenu("Teacher Dashboard", new String[] {"Manage Courses", "Mark Attendance", "Grade Students", "Logout"});
            int choice = view.getChoice();
            switch (choice) {
                case 1 -> manageCourses();
                case 2 -> markAttendance();
                case 3 -> gradeStudents();
                case 4 -> {
                    view.showMessage("Logging out...");
                    return;
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    }

    private void manageCourses() {
        view.showMessage("Managing courses functionality is under development.");
    }

    private void markAttendance() {
        view.showMessage("Marking attendance functionality is under development.");
    }

    private void gradeStudents() {
        view.showMessage("Grading students functionality is under development.");
    }

    private void studentDashboard() {
        while (true) {
            view.showMenu("Student Dashboard", new String[] {"View Courses", "Track Attendance", "View Grades", "Logout"});
            int choice = view.getChoice();
            switch (choice) {
                case 1 -> viewCourses();
                case 2 -> trackAttendance();
                case 3 -> viewGrades();
                case 4 -> {
                    view.showMessage("Logging out...");
                    return;
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    }

    private void viewCourses() {
        view.showMessage("Viewing courses functionality is under development.");
    }

    private void trackAttendance() {
        view.showMessage("Tracking attendance functionality is under development.");
    }

    private void viewGrades() {
        view.showMessage("Viewing grades functionality is under development.");
    }

    private void parentDashboard() {
        while (true) {
            view.showMenu("Parent Dashboard", new String[] {"Monitor Student Progress", "Pay Fees", "Logout"});
            int choice = view.getChoice();
            switch (choice) {
                case 1 -> monitorStudentProgress();
                case 2 -> payFees();
                case 3 -> {
                    view.showMessage("Logging out...");
                    return;
                }
                default -> view.showMessage("Invalid choice.");
            }
        }
    }

    private void monitorStudentProgress() {
        view.showMessage("Monitoring student progress functionality is under development.");
    }

    private void payFees() {
        view.showMessage("Paying fees functionality is under development.");
    }
}
