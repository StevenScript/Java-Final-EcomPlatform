package com.github.stevenscript.product;

import com.github.stevenscript.db.dataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of ProductDAO interface.
 * <p>
 * Uses DatabaseConnection to interact with the PostgreSQL database.
 */
public class productDAOImpl implements productDAO {

    /**
     * Inserts a new product into the database.
     * 
     * @param product The product to be inserted.
     * @return The generated ID of the inserted product, or -1 if the operation fails.
     */
    @Override
    public int create(product product) {
        String sql = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = dataBaseConnection.getConnection(); // Establish a connection to the database
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare the SQL statement
            // Set parameter values in the prepared statement
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            
            // Execute the query and retrieve the generated ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt("id"); // Fetch the auto-generated ID
                product.setId(generatedId); // Set the ID in the product object
                return generatedId; // Return the ID
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL exceptions
        }
        return -1; // Return -1 in case of failure
    }


    /**
     * Finds a product by its ID.
     * 
     * @param id The ID of the product to find.
     * @return The product object, or null if no product is found.
     */
    @Override
    public product findById(int id) {
        String sql = "SELECT id, name, price, quantity, seller_id FROM products WHERE id = ?";
        try (Connection conn = dataBaseConnection.getConnection(); // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            stmt.setInt(1, id); // Set the ID parameter
            
            try (ResultSet rs = stmt.executeQuery()) { // Execute the query
                if (rs.next()) {
                    // Map result set data to product object
                    product p = new product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setSellerId(rs.getInt("seller_id"));
                    return p; // Return the product object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return null; // Return null if no product is found
    }

    /**
     * Finds products by seller ID.
     * 
     * @param sellerId The ID of the seller.
     * @return A list of products associated with the seller.
     */
    @Override
    public List<product> findBySellerId(int sellerId) {
        List<product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, quantity, seller_id FROM products WHERE seller_id = ?";
        try (Connection conn = dataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sellerId); // Set seller ID parameter
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Map result set data to product objects and add to the list
                    product p = new product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setSellerId(rs.getInt("seller_id"));
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return products; // Return the list of products
    }


    /**
     * Finds all products in the database.
     * 
     * @return A list of all products.
     */
    @Override
    public List<product> findAll() {
        List<product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, quantity, seller_id FROM products";
        try (Connection conn = dataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Map result set data to product objects and add to the list
                product p = new product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSellerId(rs.getInt("seller_id"));
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return products; // Return the list of products
    }



    /**
     * Retrieves a list of products from the database whose names match the given pattern.
     * Uses a case-insensitive search ('ILIKE') to match the pattern anywhere in the product name.
     *
     * @param namePattern the substring or pattern used for matching product names.
     *                    It will be wrapped with '%' wildcards for partial matches.
     * @return a list of Product objects matching the pattern, or an empty list if no matches are found.
     */
    @Override
    public List<product> findByName(String namePattern) {
        List<product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, quantity, seller_id FROM products WHERE name ILIKE ?";
        try (Connection conn = dataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + namePattern + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    product p = new product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setSellerId(rs.getInt("seller_id"));
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    /**
     * Updates an existing product in the database.
     * 
     * @param product The product to update.
     * @return True if the product was updated successfully, false otherwise.
     */
    @Override
    public boolean update(product product) {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection conn = dataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameter values for the update
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            
            int rowsAffected = stmt.executeUpdate(); // Execute the update
            return rowsAffected > 0; // Return true if rows were updated
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return false; // Return false in case of failure
    }


    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete.
     * @return True if the product was deleted successfully, false otherwise.
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = dataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); // Set the ID parameter
            int rowsAffected = stmt.executeUpdate(); // Execute the delete operation
            return rowsAffected > 0; // Return true if rows were deleted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return false; // Return false in case of failure
    }
}
