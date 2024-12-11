Below is an example `README.md` file that you can adapt as needed for your project. It assumes the structure and details provided in previous discussions. Update any sections that need more detail (like actual database credentials, setup instructions, or class diagrams) once the information is finalized.

---

# E-Commerce Platform (Console-Based)

**Authors:** Steven Norris, Emily Warford, and Cameron Beanland

## Overview

This project is a console-based E-Commerce platform, written in Java and integrated with a PostgreSQL database. The application simulates a small online marketplace where users can register as buyers, sellers, or admins. Each user type has specific functionalities:

- **Buyers:** Browse and search products.
- **Sellers:** Add, view, update, and delete their own products.
- **Admins:** View all users, manage users (e.g., delete), and view all products.

The application uses JDBC for database connectivity, BCrypt for password hashing, and follows an MVC-like(Model-View-Controller) approach with DAOs for database access and service classes for business logic.

## Features

- **User Registration & Authentication:**  
  Users can register with a username, email, and password. Passwords are hashed using BCrypt. After registering, users can log in and interact with the system according to their role.
- **Role-Based Menus:**

  - **Buyer Menu:** View all products, search products by name.
  - **Seller Menu:** Add new products, view their products, update, or delete them.
  - **Admin Menu:** View all users, delete users, and view all products in the system.

- **Database Integration (PostgreSQL):**  
  The application uses a PostgreSQL database to store user and product information.

- **Maven Build System:**  
  The project uses Maven for dependency management and building.

## Project Structure

```
Java-Final-EcomPlatform/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com/github/stevenscript/ecommerce-platform
    │   │       ├── App.java
    │   │       ├── db
    │   │       │   └── DatabaseConnection.java
    │   │       ├── user
    │   │       │   ├── User.java
    │   │       │   ├── Buyer.java
    │   │       │   ├── Seller.java
    │   │       │   ├── Admin.java
    │   │       │   ├── UserDAO.java
    │   │       │   └── UserService.java
    │   │       └── product
    │   │           ├── Product.java
    │   │           ├── ProductDAO.java
    │   │           ├── ProductDAOImpl.java
    │   │           └── ProductService.java
    │   └── resources
    │       └── db.sql
    └── test
        └── ...
```

- **`App.java`**: The main entry point that starts the application, handles user login/registration flows, and directs the user to the appropriate menu.
- **`db/DatabaseConnection.java`**: Provides a connection to the PostgreSQL database.
- **`user/`**: Contains user-related classes and DAOs.
- **`product/`**: Contains product-related classes, DAOs, and services.

## Requirements

- **Java Version:** Java 11 or later
- **Database:** PostgreSQL (ensure you have the JDBC driver and correct credentials)
- **Maven:** To build and run the project

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/StevenScript/Java-Final-EcomPlatform
   ```
2. **Configure Database:**
   - Create a PostgreSQL database.
   - Update the `DatabaseConnection.java` file with your database URL, username, and password.
   - Run the `db.sql` script (located in `src/main/resources`) to create the required tables.
3. **Install Dependencies & Build:**
   Navigate to the project root and run:

   ```bash
   mvn clean install
   ```

   This command will download all dependencies and build the project.

## Running the Application

After a successful build, you can run the application from the command line:

```bash
mvn exec:java -Dexec.mainClass="com.github.stevenscript.ecommerce-platform.App"
```

This will start the console-based interface, allowing you to register or log in and then navigate through the menus.

## Usage

- **Register a New User:**  
  When prompted in the main menu, choose the "Register" option and provide a username, password, email, and role.
- **Login:**  
  Existing users can log in by providing their username and password. Successful login displays a menu based on their role.

- **Role-Based Actions:**
  - **Buyer:** View all products, search products.
  - **Seller:** Add new product, view your products, update, delete.
  - **Admin:** View all users, delete a user, view all products.

## Testing

If you have unit tests, you can run them with:

```bash
mvn test
```

## Authors

- **Steven Norris**
- **Emily Warford**
- **Cameron Beanland**

Each author contributed to different parts of the project.

- Steven worked on Github repo initilization, product management, and ensuring Javadocs compliancy
- Emily handled the menu and database integration
- Cameron worked on our User Registration/Authentication and Role-Based Functionality.
- All team members contributed to housekeeping and and missing elements during each others developments.
