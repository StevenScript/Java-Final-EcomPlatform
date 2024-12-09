package com.github.stevenscript.user;

import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class to manage user operations like registration and login processes.
 */
public class userService {
    private userDAO userDAO;

    /**
     * Constructs a userService with the specified database (DB) connection
     * 
     * @param connection the DB connection to be used in operations
     */
    public userService(Connection connection) {
        this.userDAO = new userDAO(connection);
    }

    /**
     * Reigsters a new user with the following details:
     * 
     * @param username id of the new user
     * @param password the plaintext password of new user
     * @param email the email address for the new user
     * @param role of new user (in this case, 'buyer', 'seller', and 'admin')
     * @throws SQLException for DB access error/SQL statement fail
     */
    public void registerUser(String username, String password, String email, String role) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user user;
        switch (role.toLowerCase()) {
            case "buyer":
                user = new buyer();
                break;
            case "seller":
                user = new seller();
                break;
            case "admin":
                user = new admin();
                break;
            default:
                throw new IllegalArgumentException("Invalid role " + role);
        }
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setRole(role);
        userDAO.registerUser(user);
    }

    /**
     * Logs in user by verifying username & pasword.
     * 
     * @param username id of the new user
     * @param password the plaintext password of new user                      - copy and pasted from above since it required the same things anyway LOL
     * @param email the email address for the new user
     * @param role of new user (in this case, 'buyer', 'seller', and 'admin')
     * @throws SQLException for DB access error/SQL statement fail
     */
    public user loginUser(String username, String password) throws SQLException {
        return userDAO.loginUser(username, password);
    }

    /**
     * Displays a menu based on role of user.
     * 
     * @param user verifies from the User object
     */
    public void displayMenu(user user) {
        switch (user.getRole().toLowerCase()) {
            case "buyer":
                displayBuyerMenu();
                break;
            case "seller":
                displaySellerMenu();
                break;
            case "admin":
                displayAdminMenu();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }
    }

    // Lastly, placeholder methods for the three role-based menus ('buyer', 'seller', 'admin' respectively)
    private void displayBuyerMenu() {
        System.out.println("Buyer menu: \n1. Browse products!\n2. View cart\n3. Check out\n");
    }

    private void displaySellerMenu() {
        System.out.println("Seller menu: \n1. List product\n2. View sales!\n3. Manage inventory\n");
    }

    private void displayAdminMenu() {
        System.out.println("Admin menu: \n1. manage Users\n2. View reports\n3. System settings\n");
    }
}
