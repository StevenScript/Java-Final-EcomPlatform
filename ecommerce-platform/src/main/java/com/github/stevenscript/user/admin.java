package com.github.stevenscript.user;


/**
 * Represents an Admin user.
 * <p>
 * When an instance of this class is created, it sets the role to "admin",
 * which can be used to redirect the user into an admin-specific menu.
 */
public class admin extends user {
    /**
     * Constructs a new Admin user and sets their role to "admin".
     */
    public admin() {                // a little object class that verifies whether an 'admin' is logged in
        this.setRole("admin");      // which will then redirect them into the admin menu.
    }
}
