package com.bibliotech.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection()
            throws SQLException {

        String url = AppConfig.get("db.url");
        String user = AppConfig.get("db.user");
        String password = AppConfig.get("db.password");

        return DriverManager.getConnection(
                url, user, password);
    }
}