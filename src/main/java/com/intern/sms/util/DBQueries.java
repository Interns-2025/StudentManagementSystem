package com.intern.sms.util;

public class DBQueries {
    public static final String ADD_USER = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";
    public static final String AUTH_ADMIN = "SELECT password, role FROM user WHERE username = ?";
    public static final String RESET_PASS = "UPDATE user SET password = ? WHERE username = ?";
    public static final String AUTH_AND_ROLE = "SELECT password, role FROM user WHERE username = ?";
    public static final String GET_BY_ID = "SELECT * FROM user WHERE userID = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String UPDATE_USER = "UPDATE user SET username = ?, password = ?, email = ?, role = ? WHERE userID = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE userID = ?";
}
