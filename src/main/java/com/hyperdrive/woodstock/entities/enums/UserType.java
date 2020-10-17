package com.hyperdrive.woodstock.entities.enums;

/** UserType
 * 
 * @author Hugo Andreassa Amaral 
 */
public enum UserType {
	ADMIN(1, "ROLE_ADMIN"),
	MARCENEIRO(2, "ROLE_WOODWORKER"),
	ESTOQUISTA(3, "ROLE_STOCKIST");
	
	private int cod;
	private String description;
	
	private UserType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
