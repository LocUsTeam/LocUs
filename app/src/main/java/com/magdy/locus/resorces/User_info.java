package com.magdy.locus.resorces;

public class User_info {

	private String username,password,email;

	public User_info(String username, String pass, String email) {
		
		this.username=username;
		this.password=pass;
		this.email=email;
		
		
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
