package com.github.stevenscript.product;

import java.util.List;

/**
 * Data Access Object(DAO for short) for Products.
 * <p>
 * Provides methods for CRUD operations on product data.
 */
public interface productDAO {

    /**
     * Inserts a new product into our database.
     *
     * @param product the product to insert
     * @return the generated product ID, or -1 if the insertion failed
     */
    int create(product product);

    /**
     * Finds a product by its ID.
     *
     * @param id the product ID
     * @return the Product if found, otherwise null
     */
    product findById(int id);

    /**
     * Finds all products belonging to a specific seller.
     *
     * @param sellerId the seller's user ID
     * @return a list of products for that seller
     */
    List<product> findBySellerId(int sellerId);

    /**
     * Returns all products in the system.
     *
     * @return a list of all products
     */
    List<product> findAll();

    /**
     * Updates an existing product's details.
     *
     * @param product the product with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(product product);

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(int id);
}