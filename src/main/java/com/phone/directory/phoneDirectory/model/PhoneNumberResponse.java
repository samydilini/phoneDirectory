package com.phone.directory.phoneDirectory.model;

public record PhoneNumberResponse(Long phoneNumber, String customerName, String customerId, Boolean status) {
}
