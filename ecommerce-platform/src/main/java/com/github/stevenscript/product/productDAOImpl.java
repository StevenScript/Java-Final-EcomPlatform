package com.github.stevenscript.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of the ProductDAO interface.
 * <p>
 * Uses DatabaseConnection to interact with the PostgreSQL database.
 */
public class productDAOImpl implements productDAO {
    private Connection connection;

    /**
     * Constructs a ProductDAOImpl with a specified database connection.
     * 
     * @param connection the Connection object to be used by this DAO
     */
    public productDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new product into the database.
     * 
     * @param product The product to be inserted.
     * @return The generated ID of the inserted product, or -1 if the operation fails.
     */
    @Override
    public int create(product product) {
        String sql = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt("id");
                product.setId(generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product p = new product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setSellerId(rs.getInt("seller_id"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);

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
     * Finds all products in the database.
     * 
     * @return A list of all products.
     */
    @Override
    public List<product> findAll() {
        List<product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, quantity, seller_id FROM products";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                product p = new product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSellerId(rs.getInt("seller_id"));
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}