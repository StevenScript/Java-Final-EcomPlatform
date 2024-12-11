package com.github.stevenscript.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class to manage user operations such as registration and login.
 * <p>
 * This class uses a UserDAO to perform database interactions and provides
 * business logic for user-related operations.
 */
public class userService {
    private userDAO userDAO;

    /**
     * Constructs a UserService with the specified database connection.
     * 
     * @param connection the database connection to be used for user operations
     */
    public userService(Connection connection) {
        this.userDAO = new userDAO(connection);
    }

    /**
     * Registers a new user with the given details.
     * 
     * @param username the username of the new user
     * @param password the plaintext password of the new user (will be hashed)
     * @param email the email address of the new user
     * @param role the role of the new user ("buyer", "seller", or "admin")
     * @throws SQLException if a database error occurs or SQL execution fails
     * @throws IllegalArgumentException if the given role is invalid
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
     * Logs in a user by verifying their username and password.
     * 
     * @param username the username of the user attempting to log in
     * @param password the plaintext password provided by the user
     * @return the User object if login is successful, or null if it fails
     * @throws SQLException if a database error occurs or SQL execution fails
     */
    public user loginUser(String username, String password) throws SQLException {
        return userDAO.loginUser(username, password);
    }

    /**
     * Displays a menu based on the role of the given user.
     * <p>
     * This is a placeholder method and should be integrated into the actual
     * application menu logic.
     * 
     * @param user the user for whom the menu should be displayed
     * @throws IllegalArgumentException if the user's role is invalid
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

    /**
     * Retrieves a list of all users from the database.
     *
     * @return a {@link List} of {@code user} objects representing all users in the database.
     * @throws SQLException if a database access error occurs.
     */
    public List<user> findAllUsers() throws SQLException {
        return userDAO.findAllUsers();
    }

    /**
     * Deletes a user from the database based on the provided user ID.
     *
     * @param userId the unique identifier of the user to be deleted.
     * @throws SQLException if a database access error occurs or the user ID is invalid.
     */
    public void deleteUser(int userId) throws SQLException {
        userDAO.deleteUser(userId);
    }    

    // Lastly, placeholder methods for the three role-based menus ('buyer', 'seller', 'admin' respectively)

    /**
     * Displays a placeholder buyer menu.
     */
    private void displayBuyerMenu() {
        System.out.println("Buyer menu: \n1. Browse products!\n2. View cart\n3. Check out\n");
    }

    /**
     * Displays a placeholder seller menu.
     */
    private void displaySellerMenu() {
        System.out.println("Seller menu: \n1. List product\n2. View sales!\n3. Manage inventory\n");
    }

    /**
     * Displays a placeholder admin menu.
     */
    private void displayAdminMenu() {
        System.out.println("Admin menu: \n1. manage Users\n2. View reports\n3. System settings\n");
    }
}
