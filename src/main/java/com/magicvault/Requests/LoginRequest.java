package com.magicvault.requests;

public class LoginRequest {
	public String username;
	public String pass;
	public LoginRequest(String name, String password) {
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
}
