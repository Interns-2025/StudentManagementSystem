package com.intern.sms.view;

import com.intern.sms.controller.UserController;
import java.util.Scanner;
import static com.intern.sms.util.Constants.*;

public class ConsoleView {
    private final UserController controller = new UserController();
    Scanner sc;
    public void showMenu(String title, String[] options) {

        System.out.println("\n==== " + title + " ====");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print(CHOOSE_OPTION);
    }

    public void mainMenu() {
        sc = new Scanner(System.in);
        while (true) {
            showMenu(TITLE_USER_MANAGEMENT, OPTIONS_USER_MANAGEMENT);
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> signUp();
                    case 3 -> forgotPassword();
                    case 4 -> {
                        showMessage(BYE);
                        sc.close();
                        return;
                    }
                    default -> showInvalidChoice();
                }
            } catch (Exception e) {
                showMessage("Error: " + e.getMessage());
            }
        }
    }

    private void login() {
        String username = prompt(USERNAME_PROMPT);
        String password = prompt(PASSWORD_PROMPT);

        String role = controller.login(username, password);

        if (role == null) {
            showMessage(INVALID_CREDS);
            return;
        }

        showMessage(GREET_MESSAGE + username);

        switch (role.toLowerCase()) {
            case ROLE_ADMIN -> adminDashboard();
            case ROLE_TEACHER -> teacherDashboard();
            case ROLE_STUDENT -> studentDashboard();
            case ROLE_PARENT -> parentDashboard();
            default -> showMessage(ROLE_UNKNOWN);
        }
    }

    private void adminDashboard() {
        while (true) {
            showMenu(TITLE_ADMIN, OPTIONS_ADMIN);
            int choice = getChoice();
            switch (choice) {
                case 1 -> manageUsers();
                case 2 -> manageSystemSettings();
                case 3 -> {
                    showLoggingOutMessage();
                    return;
                }
                default -> showInvalidChoice();
            }
        }
    }

    private void manageUsers() {
        while (true) {
            showMenu(TITLE_MANAGE_USERS, OPTIONS_MANAGE_USERS);
            int choice = getChoice();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> deleteUser();
                case 4 -> {
                    return; // Back to Admin Dashboard
                }
                default -> showInvalidChoice();
            }
        }
    }

    private void addUser() {
        String username = prompt(USERNAME_PROMPT);
        String password = prompt(PASSWORD_PROMPT);
        String email = prompt(EMAIL_PROMPT);
        String role = prompt(ROLE_PROMPT);

        String response = controller.addUser(username, password, email, role);
        showMessage(response);
    }

    private void viewUsers() {
        controller.viewUsers();
    }

    private void deleteUser() {
        int userID = Integer.parseInt(prompt(DELETE_ID_PROMPT));
        String response = controller.deleteUser(userID);
        showMessage(response);
    }

    private void manageSystemSettings() {
        showUnderDevelopment("Managing System Settings");
    }

    private void teacherDashboard() {
        while (true) {
            showMenu(TITLE_TEACHER, OPTIONS_TEACHER);
            int choice = getChoice();
            switch (choice) {
                case 1 -> manageCourses();
                case 2 -> markAttendance();
                case 3 -> gradeStudents();
                case 4 -> {
                    showLoggingOutMessage();
                    ;
                    return;
                }
                default -> showInvalidChoice();
            }
        }
    }

    private void manageCourses() {
        showUnderDevelopment("Managing courses");
    }

    private void markAttendance() {
        showUnderDevelopment("Marking attendance");
    }

    private void gradeStudents() {
        showUnderDevelopment("Grading students");
    }

    private void studentDashboard() {
        while (true) {
            showMenu(TITLE_STUDENT, OPTIONS_STUDENT);
            int choice = getChoice();
            switch (choice) {
                case 1 -> viewCourses();
                case 2 -> trackAttendance();
                case 3 -> viewGrades();
                case 4 -> {
                    showLoggingOutMessage();
                    ;
                    return;
                }
                default -> showInvalidChoice();
            }
        }
    }

    private void viewCourses() {
        showUnderDevelopment("Viewing courses");
    }

    private void trackAttendance() {
        showUnderDevelopment("Tracking attendance");
    }

    private void viewGrades() {
        showUnderDevelopment("Viewing grades");
    }

    private void parentDashboard() {
        while (true) {
            showMenu(TITLE_PARENT, OPTIONS_PARENT);
            int choice = getChoice();
            switch (choice) {
                case 1 -> monitorStudentProgress();
                case 2 -> payFees();
                case 3 -> {
                    showLoggingOutMessage();
                    ;
                    return;
                }
                default -> showInvalidChoice();
            }
        }
    }

    private void monitorStudentProgress() {
        showUnderDevelopment("Monitoring student progress");
    }

    private void payFees() {
        showUnderDevelopment("Paying fees");
    }

    private void signUp() {
        String username = prompt(USERNAME_PROMPT);
        String password = prompt(PASSWORD_PROMPT);
        String email = prompt(EMAIL_PROMPT);
        String role = prompt(ROLE_PROMPT);
        if (role.equals(ROLE_ADMIN)) {
            prompt(CONTACT_ADMIN);
        }
        boolean registered = controller.signUp(username, password, email, role);
        showMessage(registered ? SUCCESS_ADD : FAIL_ADD);
    }

    private void forgotPassword() {
        showMessage("\n--- Admin Authentication ---");
        String adminUsername = prompt(USERNAME_PROMPT);
        String adminPassword = prompt(PASSWORD_PROMPT);
        String role = controller.login(adminUsername, adminPassword);
        if (role.equals(ROLE_ADMIN)) {
            String targetUsername = prompt("Enter username of user to reset password: ");
            String newPassword = prompt("Enter new password for " + targetUsername + ": ");
            boolean reset = controller.forgotPassword(targetUsername, newPassword);
            showMessage(reset ? SUCCESS_RESET : FAIL_RESET);
        }
    }

    public void showLoggingOutMessage() {
        showMessage(LOG_OUT);
    }

    public void showInvalidChoice() {
        showMessage(INVALID_CHOICE);
    }

    public void showUnderDevelopment(String featureName) {
        showMessage(featureName + " functionality is under development.");
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
