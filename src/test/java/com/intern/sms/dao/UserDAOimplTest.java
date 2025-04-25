package com.intern.sms.dao;

import com.intern.sms.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOimplTest {

    UserDAO dao = new UserDAOimpl();

    @BeforeAll
    static void setUp() {
        UserDAO dao = new UserDAOimpl();
        boolean res;
        if (dao.getUserById(1)==null) {
        res = dao.addUser("admin",encrypt("123456"), "admin@sms.com", "admin");
        assertTrue(res);}
    }

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Test
    void addUser() {
        boolean res;

        res = dao.addUser("test",encrypt("test"), "", "parent");
        assertTrue(res);

        res = dao.addUser("Test 1",encrypt("test"), "admin@sms.com", "admin");
        assertFalse(res);

        res = dao.addUser("Test 1",encrypt("test"), "", "admin");
        assertFalse(res);
    }

    @Test
    void authenticateAdmin() {
        boolean res;
        res = dao.authenticateAdmin("admin",encrypt("123456"));
        assertTrue(res);

//        res = dao.authenticateAdmin("admin",encrypt("admin"));
//        assertFalse(res);
    }

    @Test
    void resetPassword() {
        boolean res;
        res = dao.resetPassword("test",encrypt("TEST"));
        assertTrue(res);
        String role = dao.authenticateAndGetRole("test",encrypt("TEST"));
        assertEquals("parent", role);

    }

    @Test
     void authenticateAndGetRole() {
        String role = dao.authenticateAndGetRole("admin", encrypt("123456"));
        assertEquals("admin",role);

        role = dao.authenticateAndGetRole("admin", encrypt("wrongpassword"));
        assertNotEquals("admin",role);
    }

    @Test
    void getUserById() {
        User test = dao.getUserById(1);
        assertEquals("admin", test.getUsername());

        test = dao.getUserById(1);
        assertNotEquals("test", test.getUsername());
    }

    @Test
    void getAllUsers() {
        List<User> users = dao.getAllUsers();
        assertEquals("admin",users.getFirst().getUsername());
    }

    @Test
    void updateUser() {
        User test = new User(1, "admin",encrypt("123456"),"admin_updated@sms.com", "admin");
        dao.updateUser(test);

        boolean res = dao.authenticateAdmin("admin",encrypt("admin"));
        assertTrue(res);

        test.setUsername("admin_updated@sms.com");
        res = dao.authenticateAdmin("admin",encrypt("admin"));
        assertTrue(res);
    }

    @Test
    void deleteUser() {
        boolean res = dao.deleteUser(2);
        assertTrue(res);
    }
}