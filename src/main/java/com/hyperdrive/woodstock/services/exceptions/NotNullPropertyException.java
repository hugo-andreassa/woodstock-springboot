package com.hyperdrive.woodstock.services.exceptions;

public class NotNullPropertyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotNullPropertyException(String propertyName) {
		super("The following property cannot be null: " + propertyName);
	}
}
