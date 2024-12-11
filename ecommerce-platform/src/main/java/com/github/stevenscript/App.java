
package com.github.stevenscript;

import java.util.Scanner;

import com.github.stevenscript.user.user;
import com.github.stevenscript.user.userService;
import com.github.stevenscript.product.productService;
import com.github.stevenscript.product.product;
import com.github.stevenscript.product.productDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Hello world!
 *
 */

/**
 * The main entry point of the E-Commerce application.
 * <p>
 * This class handles initial user actions such as logging in, registering, and
 * redirects users to role-specific menus (buyer, seller, admin) upon successful login.
 */
public class App {
    /**
     * Scanner for reading user input from the console.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The database connection used by the application.
     */
    private static Connection connection;

    /**
     * The UserService instance for handling user-related operations.
     */
    private static userService userService;

    /**
     * The ProductService instance for handling product-related operations.
     */
    private static productService productService;

    /**
     * The currently logged-in user.
     */
    private static user currentUser; // stores current user type that is logged in


    // Main App logic

    /**
     * The main method that starts the E-Commerce Platform application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main( String[] args ){

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EcommPlatform", "Postrgres", "Postrgres");
            userService = new userService(connection);
            productService = new productService(new productDAOImpl(connection));

            mainMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Displays the main menu for the application, allowing the user to log in,
     * register, or exit the system.
     */
    private static void mainMenu() {
        while (true) {
            System.out.println("Welcome to the E-Commerce Platform");
            System.out.println("1. Log In");
            System.out.println("2. Register User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    loginUserFlow();
                    break;
                case 2:
                    registerUserFlow();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }


    /**
     * Handles the user login flow, prompting for username and password,
     * then authenticating via the UserService.
     */
    private static void loginUserFlow() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        try {
            currentUser = userService.loginUser(username, password);
            if (currentUser != null) {
                System.out.println("You are now logged in as " + currentUser.getRole() + ".");
                switch (currentUser.getRole().toLowerCase()) {
                    case "buyer":
                        buyersMenu();
                        break;
                    case "seller":
                        sellersMenu();
                        break;
                    case "admin":
                        adminMenu();
                        break;
                    default:
                        System.out.println("Unknown role. Logging out.");
                        currentUser = null;
                }
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Handles user registration, prompting for username, password, email, and role,
     * then creating a new user record via UserService.
     */
    private static void registerUserFlow() {
        System.out.println("Choose Username: ");
        String username = scanner.nextLine();

        System.out.println("Choose Password: ");
        String password = scanner.nextLine();

        System.out.println("Choose Email: ");
        String email = scanner.nextLine();

        System.out.println("What is Your Role? (buyer, seller, or admin): ");
        String role = scanner.nextLine();

        try {
            userService.registerUser(username, password, email, role);
            System.out.println("Registration successful!");
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
        
    /**
     * Displays the buyer's menu and handles buyer-specific actions such as viewing all products
     * or searching for a product by name.
     */
    private static void buyersMenu() {
        while (true) {
            System.out.println("Buyer Menu:");
            System.out.println("1. View All Products");
            System.out.println("2. Search For Product");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    searchForProduct();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }

    /**
     * Retrieves and displays all products using the ProductService.
     */
    private static void viewAllProducts() {
        List<product> products = productService.listAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.forEach(p -> {
                System.out.println("ID: " + p.getId() + ", Name: " + p.getName() +
                                   ", Price: " + p.getPrice() + ", Qty: " + p.getQuantity());
            });
        }
    }

    /**
     * Prompts the user for a product name pattern and displays all matching products.
     */
    private static void searchForProduct() {
        System.out.print("Enter product name to search: ");
        String namePattern = scanner.nextLine();
        List<product> products = productService.searchProductsByName(namePattern);
        if (products.isEmpty()) {
            System.out.println("No products found matching that name.");
        } else {
            products.forEach(p -> {
                System.out.println("ID: " + p.getId() + ", Name: " + p.getName() +
                                   ", Price: " + p.getPrice() + ", Qty: " + p.getQuantity());
            });
        }
    }

    /**
     * Displays the seller's menu and handles seller-specific actions such as viewing their products,
     * adding, editing, or deleting products.
     */
    private static void sellersMenu() {
        while (true) {
            System.out.println("Seller Menu:");
            System.out.println("1. View All Your Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    viewMyProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    /**
     * Retrieves and displays all products owned by the currently logged-in seller.
     */
    private static void viewMyProducts() {
        try {
            List<product> myProducts = productService.listProductsBySeller(currentUser);
            if (myProducts.isEmpty()) {
                System.out.println("You have no products listed.");
            } else {
                myProducts.forEach(p -> {
                    System.out.println("ID: " + p.getId() + ", Name: " + p.getName() +
                                       ", Price: " + p.getPrice() + ", Qty: " + p.getQuantity());
                });
            }
        } catch (IllegalAccessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the seller for new product details and adds the product via ProductService.
     */
    private static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = readDouble();
        System.out.print("Enter Product Quantity: ");
        int qty = readInt();

        try {
            int productId = productService.addProduct(new product(name, price, qty, 0), currentUser);
            if (productId != -1) {
                System.out.println("Product added successfully with ID: " + productId);
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (IllegalAccessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the seller for product details to edit an existing product.
     */
    private static void editProduct() {
        System.out.print("Enter Product ID to edit: ");
        int productId = readInt();
        System.out.print("Enter new Product Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Product Price: ");
        double newPrice = readDouble();
        System.out.print("Enter new Product Quantity: ");
        int newQty = readInt();

        product updatedProduct = new product(newName, newPrice, newQty, currentUser.getId());
        updatedProduct.setId(productId);

        try {
            if (productService.updateProduct(updatedProduct, currentUser)) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (IllegalAccessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the seller for a product ID and attempts to delete that product.
     */
    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int productId = readInt();
        try {
            if (productService.deleteProduct(productId, currentUser)) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (IllegalAccessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Admin menu
    /**
     * Displays the admin's menu and handles admin-specific actions such as viewing all users,
     * deleting users, and viewing all products.
     */
    private static void adminMenu() {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    adminDeleteUser();
                    break;
                case 3:
                    viewAllProducts(); // reuse the buyer’s method, it just lists all products
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    /**
     * Retrieves and displays all users in the system (admin action).
     */
    private static void viewAllUsers() {
        try {
            List<user> allUsers = userService.findAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("No users found.");
            } else {
                for (user u : allUsers) {
                    System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() +
                                       ", Email: " + u.getEmail() + ", Role: " + u.getRole());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }

    /**
     * Prompts the admin for a user ID and attempts to delete that user.
     */
    private static void adminDeleteUser() {
        System.out.print("Enter User ID to delete: ");
        int userId = readInt();
        try {
            userService.deleteUser(userId);
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // Utility methods to safely read integers/doubles
    /**
     * Reads an integer from the console, prompting the user until a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return val;
    }


    /**
     * Reads a double (decimal) number from the console, prompting the user until a valid number is entered.
     *
     * @return the double value entered by the user
     */
    private static double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid decimal number.");
            scanner.nextLine();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // consume leftover newline
        return val;
    }
}