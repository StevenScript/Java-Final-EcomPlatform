package com.github.stevenscript.user;

public class seller extends user {
    public seller() {               // tiny object used to verify whether someone is of 'seller' status,
        this.setRole("seller");     // and will be redirected to the sellers menu.
    }
}
