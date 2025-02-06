package com.phone.directory.phoneDirectory.model;

/**
 * Response class to handle the phone number details.
 */
public record CustomerPhoneNumberResponse(String phoneNumber, String customerName, String customerId) {
}
