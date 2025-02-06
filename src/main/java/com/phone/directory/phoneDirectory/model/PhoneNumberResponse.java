package com.phone.directory.phoneDirectory.model;

/**
 * Response class to handle the phone number details.
 */
public record PhoneNumberResponse(String phoneNumberID, String phoneNumber, Boolean status) {
}
