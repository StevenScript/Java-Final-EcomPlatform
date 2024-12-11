package com.github.stevenscript.user;

/**
 * Represents a Seller user.
 * <p>
 * When an instance of this class is created, it sets the role to "seller",
 * which can be used to redirect the user to a seller-specific menu.
 */
public class seller extends user {
    /**
     * Constructs a new Seller user and sets their role to "seller".
     */
    public seller() {               // tiny object used to verify whether someone is of 'seller' status,
        this.setRole("seller");     // and will be redirected to the sellers menu.
    }
}
