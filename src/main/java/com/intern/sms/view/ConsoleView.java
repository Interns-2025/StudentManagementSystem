// Consolejava
package com.intern.sms.view;

import com.intern.sms.controller.UserController;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner sc = new Scanner(System.in);
    private final UserController controller = new UserController();

    public void showMenu(String title, String[] options) {
        System.out.println("\n==== " + title + " ====");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Choose an option: ");
    }

    public void mainMenu() {
        while (true) {
            showMenu("User Management Menu", new String[]{"Login", "Sign Up", "Forgot Password", "Exit"});
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> signUp();
                    case 3 -> forgotPassword();
                    case 4 -> {
                        showMessage("Goodbye!");
                        return;
                    }
                    default -> showMessage("Invalid choice.");
                }
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage());
            }
        }
    }

    private void login() {
        String username = prompt("Enter username: ");
        String password = prompt("Enter password: ");

        String role = controller.login(username, password);

        if (role == null) {
            showMessage("Invalid credentials.");
            return;
        }

        showMessage("Login successful! Welcome, " + username);

        switch (role.toLowerCase()) {
            case "admin" -> adminDashboard();
            case "teacher" -> teacherDashboard();
            case "student" -> studentDashboard();
            case "parent" -> parentDashboard();
            default -> showMessage("Unknown role.");
        }
    }

    private void adminDashboard() {
        while (true) {
            showMenu("Admin Dashboard", new String[]{"Manage Users", "Manage System Settings", "Logout"});
            int choice = getChoice();
            switch (choice) {
                case 1 -> manageUsers();
                case 2 -> manageSystemSettings();
                case 3 -> {
                    showMessage("Logging out...");
                    return;
                }
                default -> showMessage("Invalid choice.");
            }
        }
    }

    private void manageUsers() {
        while (true) {
            showMenu("Manage Users", new String[]{"Add User", "View Users", "Delete User", "Back to Admin Dashboard"});
            int choice = getChoice();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> deleteUser();
                case 4 -> {
                    return; // Back to Admin Dashboard
                }
                default -> showMessage("Invalid choice.");
            }
        }
    }

    private void addUser() {
        String username = prompt("Enter username: ");
        String password = prompt("Enter password: ");
        String email = prompt("Enter email: ");
        String role = prompt("Enter role (admin/teacher/student/parent): ");

        String response = controller.addUser(username, password, email, role);
        showMessage(response);
    }

    private void viewUsers() {
        controller.viewUsers();
    }

    private void deleteUser() {
        int userID = Integer.parseInt(prompt("Enter user ID to delete: "));
        String response = controller.deleteUser(userID);
        showMessage(response);
    }

    private void manageSystemSettings() {
        showMessage("Managing courses functionality is under development.");
    }

    private void teacherDashboard() {
        while (true) {
            showMenu("Teacher Dashboard", new String[] {"Manage Courses", "Mark Attendance", "Grade Students", "Logout"});
            int choice = getChoice();
            switch (choice) {
                case 1 -> manageCourses();
                case 2 -> markAttendance();
                case 3 -> gradeStudents();
                case 4 -> {
                    showMessage("Logging out...");
                    return;
                }
                default -> showMessage("Invalid choice.");
            }
        }
    }

    private void manageCourses() {
        showMessage("Managing courses functionality is under development.");
    }

    private void markAttendance() {
        showMessage("Marking attendance functionality is under development.");
    }

    private void gradeStudents() {
        showMessage("Grading students functionality is under development.");
    }
    
    private void studentDashboard() {
        while (true) {
            showMenu("Student Dashboard", new String[] {"View Courses", "Track Attendance", "View Grades", "Logout"});
            int choice = getChoice();
            switch (choice) {
                case 1 -> viewCourses();
                case 2 -> trackAttendance();
                case 3 -> viewGrades();
                case 4 -> {
                    showMessage("Logging out...");
                    return;
                }
                default -> showMessage("Invalid choice.");
            }
        }
    }

    private void viewCourses() {
        showMessage("Viewing courses functionality is under development.");
    }

    private void trackAttendance() {
        showMessage("Tracking attendance functionality is under development.");
    }

    private void viewGrades() {
        showMessage("Viewing grades functionality is under development.");
    }

    private void parentDashboard() {
        while (true) {
            showMenu("Parent Dashboard", new String[] {"Monitor Student Progress", "Pay Fees", "Logout"});
            int choice = getChoice();
            switch (choice) {
                case 1 -> monitorStudentProgress();
                case 2 -> payFees();
                case 3 -> {
                    showMessage("Logging out...");
                    return;
                }
                default -> showMessage("Invalid choice.");
            }
        }
    }

    private void monitorStudentProgress() {
        showMessage("Monitoring student progress functionality is under development.");
    }

    private void payFees() {
        showMessage("Paying fees functionality is under development.");
    }
    
    private void signUp() {
        String username = prompt("Choose a username: ");
        String password = prompt("Choose a password: ");
        String email = prompt("Enter your email: ");
        String role = prompt("Enter role (teacher/student/parent): ");
        if (role.equals("admin")) {
            prompt("Contact Admin to sign up as an Administrator.");
        }
        boolean registered = controller.signUp(username, password, email, role);
        showMessage(registered ? "User registered successfully." : "Username already exists.");
    }

    private void forgotPassword() {
        showMessage("\n--- Admin Authentication ---");
        String adminUsername = prompt("Enter admin username: ");
        String adminPassword = prompt("Enter admin password: ");
        String role = controller.login(adminUsername, adminPassword);
        if (role == "admin") {
            String targetUsername = prompt("Enter username of user to reset password: ");
            String newPassword = prompt("Enter new password for " + targetUsername + ": ");
            boolean reset = controller.forgotPassword(targetUsername, newPassword);
            showMessage(reset ? "Password reset successfully." : "Failed to reset password. Either admin auth failed or user not found.");
        }
    }

    public void showLoggingOutMessage() {
        System.out.println("Logging out...");
    }

    public void showInvalidChoice() {
        System.out.println("Invalid choice.");
    }

    public void showUnderDevelopment(String featureName) {
        System.out.println(featureName + " functionality is under development.");
    }

    public int getChoice() {
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline
        return choice;
    }

    public String prompt(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
