// Class representing a registration request.
package com.magicvault.requests;

public class RegisterRequest {
    public String username; // Username
    public String email; // Email
    public String pass; // Password

    // Constructor
    public RegisterRequest(String name, String password) {
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

    // Getter and setter for the email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }      
}
