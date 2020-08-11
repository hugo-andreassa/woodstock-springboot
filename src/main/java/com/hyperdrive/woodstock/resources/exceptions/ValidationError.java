package com.hyperdrive.woodstock.resources.exceptions;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	Set<FieldMessage> errors = new HashSet<>();
	
	public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public Set<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

}
