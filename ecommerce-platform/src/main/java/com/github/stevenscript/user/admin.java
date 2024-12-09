package com.github.stevenscript.user;

public class admin extends user {
    public admin() {                // a little object class that verifies whether an 'admin' is logged in
        this.setRole("admin");      // which will then redirect them into the admin menu.
    }
}
