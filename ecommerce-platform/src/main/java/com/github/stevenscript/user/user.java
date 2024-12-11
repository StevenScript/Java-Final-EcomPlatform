package com.github.stevenscript.user;

/**
 * Represents a generic user in the system.
 * <p>
 * This class includes basic user information: ID, username, password, email,
 * and role. It is intended as a base class for more specific user types
 * (such as Buyer, Seller, and Admin).
 */
public class user { // basic user information, includes their id, username, password, email, and role
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    /**
     * Returns the user's ID.
     * 
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns the user's password (hashed).
     *
     * @return the hashed password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's ID.
     * 
     * @param id the user ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's password (should be hashed).
     *
     * @param password the hashed password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's role (e.g., "buyer", "seller", "admin").
     * 
     * @return the user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * 
     * @param role the user role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
