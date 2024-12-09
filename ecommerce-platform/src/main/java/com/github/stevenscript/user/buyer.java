package com.github.stevenscript.user;

public class buyer extends user {
    public buyer() {                // small object class indicating that a 'buyer' user will be
        this.setRole("buyer");      // logged in and redirected towards the buyer menu (found in userService class).
    }
}
