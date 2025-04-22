package com.intern.sms.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db_config.properties")) {
            if (input == null) {
                throw new SQLException("db_config.properties not found in classpath");
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new SQLException("Failed to load database configuration", e);
        }
    }
}
