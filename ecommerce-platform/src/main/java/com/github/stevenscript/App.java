
package com.github.stevenscript;

import java.util.Scanner;

import com.github.stevenscript.user.buyer;
import com.github.stevenscript.user.seller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Hello world!
 *
 */
public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;
    public static void main( String[] args ){

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/database", "username", "password");
            while(true){
                System.out.println("Welcome to the E-Commerce Platform");
                System.out.println("1. Log In");
                System.out.println("2. Register User");
                System.out.println("3. Exit");
                System.out.println("Enter your choice (1-3): ");
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1:
                       System.out.println("Logging in...");
                       users.loginUser(connection);
                       break;
                    case 2:
                       System.out.println("Registering user...");
                       users.registerUser(connection);
                       break;
                    case 3:
                       System.out.println("Exiting the system. Goodbye!");
                       return;
                    default:
                       System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
    }
        
    //Buyers menu
    public void buyersMenu() {
        buyer buyers = new buyer();
        while(true){
            System.out.println("Welcome to the E-Commerce Platform Buyers Menu");
            System.out.println("1. View All Products");
            System.out.println("2. Search For Product");
            System.out.println("3. Exit");
            System.out.println("Enter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing products...");
                    buyers.findAll(connection);
                    break;
                case 2:
                    System.out.println("Searching products...");
                    buyers.findByName(connection);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }


    //Sellers menu
    public void sellersMenu() {
        seller sellers = new seller();
        while(true){
            System.out.println("Welcome to the E-Commerce Platform Sellers Menu");
            System.out.println("1. View All Your Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.println("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing listed products...");
                    sellers.findBySellerId(connection);
                    break;
                case 2:
                    System.out.println("Adding products...");
                    sellers.create(connection);
                    break;
                case 3:
                    System.out.println("Editing products...");
                    sellers.update(connection);
                    break;
                case 4:
                    System.out.println("Deleting products...");
                    sellers.delete(connection);
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }


    //Admin menu
    public void adminMenu() {
        admin admin = new admin();
        while(true){
            System.out.println("Welcome to the E-Commerce Platform");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Exit");
            System.out.println("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing users...");
                    //Establish connection
                    break;
                case 2:
                    System.out.println("Deleting user...");
                    //Establish connection
                    break;
                case 3:
                    System.out.println("Viewing products...");
                    admin.findAll(connection);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}
