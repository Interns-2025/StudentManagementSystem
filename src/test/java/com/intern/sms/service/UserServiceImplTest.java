package com.intern.sms.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserServiceImpl userService = new UserServiceImpl();

    @BeforeEach
    void setUp() {

    }

    @Test
    void registerUser() {
        boolean success;
//        boolean success = userService.registerUser("UserA", "password", "userA@sms.com", "student" );
//        assertTrue(success);

        success = userService.registerUser("UserB", "password", "userA@sms.com", "student" );
        assertFalse(success);
    }

    @Test
    void authenticateAdmin() {
        boolean success = userService.authenticateAdmin("admin", "123456");
        assertTrue(success);

        success = userService.authenticateAdmin("admin", "password");
        assertFalse(success);
    }

    @Test
    void resetPassword() {
        boolean success = userService.resetPassword("admin", "123456");
        assertTrue(success);

        success = userService.resetPassword("admin1", "password");
        assertFalse(success);
    }

    @Test
    void authenticateAndGetRole() {
        String role;
        role = userService.authenticateAndGetRole("userA", "password");
        assertEquals("student", role);
    }

    @Test
    void deleteUser() {
        boolean success = userService.deleteUser(2);
        assertTrue(success);
    }
}