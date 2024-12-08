package com.github.stevenscript.product;

import com.github.stevenscript.user.user;
import java.util.List;


/**
 * Service layer for product operations.
 * <p>
 * Integrates with User objects to ensure role-based access:
 * - Sellers can add, update, and delete their own products.
 * - Buyers can view products.
 * - Admins can view all products.
 */
public class productService {
    private final productDAO productDAO;

    /**
     * Constructs a ProductService with a given ProductDAO.
     * 
     * @param productDAO an implementation of ProductDAO
     */
    public productService(productDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Allows a seller to add a new product.
     * 
     * @param product the product to add
     * @param seller the currently logged-in user (must be a seller)
     * @return the created product's ID, or -1 if unsuccessful
     * @throws IllegalAccessException if the user is not a seller
     */
    public int addProduct(product product, user seller) throws IllegalAccessException {
        if (!"seller".equalsIgnoreCase(seller.getRole())) {
            throw new IllegalAccessException("Only a seller can add products.");
        }
        product.setSellerId(seller.getId());
        return productDAO.create(product);
    }

    /**
     * Lists all products owned by a seller.
     * 
     * @param seller the currently logged-in user (must be a seller)
     * @return a list of products owned by the seller
     * @throws IllegalAccessException if the user is not a seller
     */
    public List<product> listProductsBySeller(user seller) throws IllegalAccessException {
        if (!"seller".equalsIgnoreCase(seller.getRole())) {
            throw new IllegalAccessException("Only a seller can view their products.");
        }
        return productDAO.findBySellerId(seller.getId());
    }

    /**
     * Updates a product if the logged-in user is the owner (seller).
     * 
     * @param product the product with updated information
     * @param seller the currently logged-in user (must be the product's seller)
     * @return true if updated successfully, false otherwise
     * @throws IllegalAccessException if the user is not allowed to update the product
     */
    public boolean updateProduct(product product, user seller) throws IllegalAccessException {
        if (!"seller".equalsIgnoreCase(seller.getRole())) {
            throw new IllegalAccessException("Only a seller can update products.");
        }
        product existing = productDAO.findById(product.getId());
        if (existing == null || existing.getSellerId() != seller.getId()) {
            throw new IllegalAccessException("You are not authorized to update this product.");
        }
        return productDAO.update(product);
    }

    /**
     * Deletes a product if the logged-in user is the owner (seller).
     * 
     * @param productId the product ID to delete
     * @param seller the currently logged-in user (must be the product's seller)
     * @return true if deleted successfully, false otherwise
     * @throws IllegalAccessException if the user is not allowed to delete this product
     */
    public boolean deleteProduct(int productId, user seller) throws IllegalAccessException {
        if (!"seller".equalsIgnoreCase(seller.getRole())) {
            throw new IllegalAccessException("Only a seller can delete products.");
        }
        product existing = productDAO.findById(productId);
        if (existing == null || existing.getSellerId() != seller.getId()) {
            throw new IllegalAccessException("You are not authorized to delete this product.");
        }
        return productDAO.delete(productId);
    }


    /**
     * Searches for products by a given name pattern.
     * 
     * This method delegates the database search to the underlying ProductDAO implementation.
     * The matching is case-insensitive and can return multiple products.
     *
     * @param namePattern the substring or partial name to search for
     * @return a list of products that contain the given pattern in their names
     */
    public List<product> searchProductsByName(String namePattern) {
    return productDAO.findByName(namePattern);
    }

    /**
     * Lists all products in the system.
     * <p>
     * Useful for buyers and admins. No special restrictions here, 
     * but typically called by CLI when a buyer wants to browse or admin wants overview.
     *
     * @return a list of all products
     */
    public List<product> listAllProducts() {
        return productDAO.findAll();
    }
}
