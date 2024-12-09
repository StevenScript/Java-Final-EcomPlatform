package com.github.stevenscript.user;

/**
 * A generic placeholder for the user class.
 * 
 * This class is a temporary stub to avoid compilation errors in ProductService.
 * Replace with the actual user class code when available.
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

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
