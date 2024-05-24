// Class representing a login request.
package com.magicvault.requests;

public class LoginRequest {
    public String username; // Username
    public String pass; // Password

    // Constructor
    public LoginRequest(String name, String password) {
        super();
        username = name;
        pass = password;
    }

    // Getter and setter for the username
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    // Getter and setter for the password
    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        pass = password;
    }    
}
