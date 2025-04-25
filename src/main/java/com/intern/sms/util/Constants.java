package com.intern.sms.util;

public class Constants {
    //CLI
    public static final String CHOOSE_OPTION = "Choose an option: ";
    // Validate below with Ashwini/Gursimran
    public static final String TITLE_USER_MANAGEMENT = "User Management Menu";
    public static final String[] OPTIONS_USER_MANAGEMENT = new String[]{"Login", "Sign Up", "Forgot Password", "Exit"};
    public static final String TITLE_ADMIN = "Admin Dashboard";
    public static final String[] OPTIONS_ADMIN = new String[]{"Manage Users", "Manage System Settings", "Logout"};
    public static final String TITLE_MANAGE_USERS = "Manage Users";
    public static final String[] OPTIONS_MANAGE_USERS = new String[]{"Add User", "View Users", "Delete User", "Back to Admin Dashboard"};
    public static final String TITLE_TEACHER = "Teacher Dashboard";
    public static final String[] OPTIONS_TEACHER = new String[]{"Manage Courses", "Mark Attendance", "Grade Students", "Logout"};
    public static final String TITLE_STUDENT = "Student Dashboard";
    public static final String[] OPTIONS_STUDENT =  new String[]{"View Courses", "Track Attendance", "View Grades", "Logout"};
    public static final String TITLE_PARENT = "Parent Dashboard";
    public static final String[] OPTIONS_PARENT = new String[]{"Monitor Student Progress", "Pay Fees", "Logout"};
    //
    public static final String CONTACT_ADMIN = "Contact Admin to sign up as an Administrator.";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_TEACHER = "teacher";
    public static final String ROLE_STUDENT = "student";
    public static final String ROLE_PARENT = "parent";
    public static final String ROLE_UNKNOWN = "Unknown role.";
    public static final String GREET_MESSAGE = "Login successful! Welcome, ";
    public static final String LOG_OUT = "Logging out...";
    public static final String BYE = "Goodbye!";
    public static final String INVALID_CREDS = "Invalid credentials.";
    public static final String INVALID_CHOICE = "Invalid choice.";
    public static final String USERNAME_PROMPT = "Enter username: ";
    public static final String PASSWORD_PROMPT = "Enter password: ";
    public static final String EMAIL_PROMPT = "Enter email: ";
    public static final String ROLE_PROMPT = "Enter role (admin/teacher/student/parent): ";
    public static final String DELETE_ID_PROMPT = "Enter user ID to delete: ";
    public static final String SUCCESS_ADD = "User added successfully!";
    public static final String FAIL_ADD = "Failed to add user.";
    public static final String SUCCESS_DEL = "User deleted successfully!";
    public static final String FAIL_DEL = "Failed to delete user.";
    public static final String SUCCESS_RESET = "Password reset successfully.";
    public static final String FAIL_RESET = "Failed to reset password. Either admin auth failed or user not found.";
    public static final String USER_ID = "userID";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String ADMIN = "admin";
}
