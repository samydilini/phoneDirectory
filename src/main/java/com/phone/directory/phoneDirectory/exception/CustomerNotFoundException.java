package com.phone.directory.phoneDirectory.exception;

/**
 * Exception class to handle the customer not found scenario.
 */
public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
