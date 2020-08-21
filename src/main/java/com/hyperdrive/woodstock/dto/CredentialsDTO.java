package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

public class CredentialsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String password;
	
	private String email;
	
	public CredentialsDTO() {
	
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
