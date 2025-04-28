package com.intern.sms.dao;

import com.intern.sms.model.User;
import java.util.List;

public interface UserDAO{

    public boolean addUser(String username, String password, String email, String role);

    public boolean authenticateAdmin(String username, String password);

    public boolean resetPassword(String targetUsername, String newPassword);

    public String authenticateAndGetRole(String username, String password);

    User getUserById(int userID);

    List<User> getAllUsers();

    void updateUser(User user);

    boolean deleteUser(int userID);
}
