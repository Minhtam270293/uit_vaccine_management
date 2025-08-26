package com.uit.vaccinemanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12796261?useSSL=false&serverTimezone=UTC";
    private static final String USER = "sql12796261";
    private static final String PASSWORD = "w3gbfV71bu";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
