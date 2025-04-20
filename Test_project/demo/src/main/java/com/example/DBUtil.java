package com.example;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3307/cyborg";
    private static final String USER = "root";
    private static final String PASSWORD = "shivansh09";
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());

    // This method will return a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the MySQL JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC driver not found", e);
            throw new SQLException("MySQL JDBC driver not found", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
            throw e;  // Rethrow the exception to be handled later
        }
    }
}