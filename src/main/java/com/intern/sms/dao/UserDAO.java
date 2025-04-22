package com.intern.sms.dao;

import com.intern.sms.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO{

    void addUser(User user) throws SQLException;

    User getUserById(int userID) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(int userID) throws SQLException;
}
