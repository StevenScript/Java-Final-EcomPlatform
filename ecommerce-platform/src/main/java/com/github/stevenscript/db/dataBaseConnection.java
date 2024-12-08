package com.github.stevenscript.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a database connection to the PostgreSQL database.
 * <p>
 * Update the URL, username, and password for final product. Just made this to be able to write my code in /products.
 */
public class dataBaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    /**
     * Obtains a new connection to the database.
     *
     * @return a Connection object to the configured database
     * @throws RuntimeException if a database connection cannot be established
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Log the exception and rethrow a runtime exception
            e.printStackTrace();
            throw new RuntimeException("Unable to establish a database connection.", e);
        }
    }
}
