package com.github.stevenscript.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This is referred to as a Data Access Object (DAO) class file, used to perform database operations on Use: 
 */
public class userDAO {
    private Connection connection;

    /**
     * Construct a userDAO with specified database connections.
     * 
     * @param connection the database connection, will be used for operations
     */
    public userDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Register a new user in the database provided.
     * 
     * @param user contains user details to be registered
     * @throws SQLException for when a database error occurs/SQL statement fails
     */
    public void registerUser(user user) throws SQLException {   // used to insert user info into the users table for SQL queries
        String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { // using this should return any auto generated keys (like user id)
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());      // these are just parameters from the PreparedStatement being set
            stmt.setString(3, user.getEmail());         
            stmt.setString(4, user.getRole());
            stmt.executeUpdate(); // executes the INSERT query, which then adds new user record into the database

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) { 
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1)); // generates a key on the user object using setId method
                }
            }
        }
    }

    /**
     * Logs in a user by verifying both username and password.
     * 
     * @param username is the name of the user attempting to login
     * @param password contains a plaintext password of the user trying to login
     * @return this returns the User object if a login is successful, and null if it fails
     * @throws SQLException occurs with a database error/SQL statement fail
     */
    public user loginUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?"; // SELECT query retrieves user info from the users table, given the username matches the proper params
        try (PreparedStatement stmt = connection.prepareStatement(query)) { 
           stmt.setString(1, username); 
           try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) { // using BCrypt.checkpw method to compare the plaintext password with the hashed from the database
                    user user = new user();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                }
            }
           } 
        }
        return null;   // if there's no existing user found, this returns null (login failure)
    }
}
