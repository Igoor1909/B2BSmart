package com.example.B2BSmart.exceptions;

public class ResourceNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id) {
		super("ID: " + id + " Não localizado na base de dados");
	}
	
}
