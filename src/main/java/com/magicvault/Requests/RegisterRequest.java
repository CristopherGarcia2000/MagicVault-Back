package com.magicvault.requests;

public class RegisterRequest {
    public String username;
    public String email;
	public String pass;
	public RegisterRequest(String name, String password) {
		super();
		username = name;
		pass = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		username = name;
	}
	public String getPassword() {
		return pass;
	}
	public void setPassword(String password) {
		pass = password;
	}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    } 	    
}
