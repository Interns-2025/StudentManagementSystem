package com.intern.sms.dao;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;
class UserDAOimplTest {

    @org.junit.jupiter.api.Test
    void addUser() {
    }

    @org.junit.jupiter.api.Test
    void authenticateAdmin() {
    }

    @org.junit.jupiter.api.Test
    void resetPassword() {
    }

    @Testable
     void authenticateAndGetRole() {
        UserDAO userDAO = new UserDAOimpl();
        String role = userDAO.authenticateAndGetRole("admin", "123456");
        assertNotNull(role);
    }

    @org.junit.jupiter.api.Test
    void getUserById() {
    }

    @org.junit.jupiter.api.Test
    void getAllUsers() {
    }

    @org.junit.jupiter.api.Test
    void updateUser() {
    }

    @org.junit.jupiter.api.Test
    void deleteUser() {
    }
}