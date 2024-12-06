package com.github.stevenscript.product;

/**
 * Represents a product in the e-commerce platform.
 * <p>
 * Each product is associated with a seller (referenced by sellerId).
 */
public class product {
    /** The unique ID of the product (primary key in the database). */
    private int id;
    
    /** The name of the product. */
    private String name;
    
    /** The price of the product. */
    private double price;
    
    /** The quantity of the product in stock. */
    private int quantity;
    
    /** The seller's user ID who owns this product. */
    private int sellerId;

    /**
     * Default constructor.
     */
    public product() {
    }

    /**
     * Parameterized constructor.
     * 
     * @param name the product name
     * @param price the product price
     * @param quantity the product quantity
     * @param sellerId the ID of the seller that owns this product
     */
    public product(String name, double price, int quantity, int sellerId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    /** @return the product ID */
    public int getId() {
        return id;
    }

    /** @param id set the product ID */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the product name */
    public String getName() {
        return name;
    }

    /** @param name set the product name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the product price */
    public double getPrice() {
        return price;
    }

    /** @param price set the product price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return the product quantity */
    public int getQuantity() {
        return quantity;
    }

    /** @param quantity set the product quantity */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** @return the seller ID who owns this product */
    public int getSellerId() {
        return sellerId;
    }

    /** @param sellerId set the seller ID */
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}