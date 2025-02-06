package com.phone.directory.phoneDirectory.exception;

/**
 * Exception class to handle the phone number not found scenario.
 */
public class PhoneNumberNotFoundException extends Throwable {
    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
