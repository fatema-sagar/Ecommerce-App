package com.ecommerce.ecommApp.customers.exceptions;

public class EmailExistsException extends Exception{
    private static final long serialVersionUID = 1L;
    public EmailExistsException(String message) {
        super(message);
    }
}
