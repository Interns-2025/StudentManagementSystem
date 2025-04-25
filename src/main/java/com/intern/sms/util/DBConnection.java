package com.intern.sms.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static com.intern.sms.util.DBConstants.*;

public class DBConnection {
    public static Connection getConnection() {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db_config.properties")) {
            if (input == null) {
                throw new IOException(CONFIG_PATH_ERROR);
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty(DB_URL);
            String user = props.getProperty(DB_USERNAME);
            String pass = props.getProperty(DB_PASSWORD);

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
