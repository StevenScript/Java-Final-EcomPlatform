package com.github.stevenscript.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Data Access Object (DAO) class for performing database operations on users.
 * <p>
 * This class handles user registration and login operations against the database.
 */
public class userDAO {
    private Connection connection;

    /**
     * Constructs a UserDAO with the specified database connection.
     * 
     * @param connection the database connection, will be used for operations
     */
    public userDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Registers a new user in the database.
     * 
     * @param user the user object containing details to be registered
     * @throws SQLException if a database error occurs or SQL execution fails
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
     * Logs in a user by verifying their username and password.
     * 
     * @param username the username of the user attempting to log in
     * @param password the plaintext password provided by the user
     * @return the User object if login is successful, or null if it fails
     * @throws SQLException if a database error occurs or SQL execution fails
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

    /**
     * Deletes a user from the database based on the provided user ID.
     * 
     * @param userId the ID of the user to delete
     * @throws SQLException if a database error occurs or SQL execution fails
     */
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all users from the database.
     * <p>
     * Executes a SELECT query to get all users, mapping the result set to a list
     * of User objects.
     * 
     * @return a list of all users in the database, or an empty list if none are found
     * @throws SQLException if a database access error occurs or SQL statement fails
     */
    public List<user> findAllUsers() throws SQLException {
        List<user> users = new ArrayList<>();
        String query = "SELECT id, username, password, email, role FROM users";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                user u = new user();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        }
        return users;
    }
}
