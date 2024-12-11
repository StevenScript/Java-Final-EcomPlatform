package com.github.stevenscript.user;


/**
 * Represents a Buyer user.
 * <p>
 * When an instance of this class is created, it sets the role to "buyer",
 * which can be used to redirect the user to a buyer-specific menu.
 */
public class buyer extends user {
    /**
     * Constructs a new Buyer user and sets their role to "buyer".
     */
    public buyer() {                // small object class indicating that a 'buyer' user will be
        this.setRole("buyer");      // logged in and redirected towards the buyer menu (found in userService class).
    }
}
