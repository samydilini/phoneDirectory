package com.phone.directory.phoneDirectory.controller;


import com.phone.directory.phoneDirectory.exception.CustomerNotFoundException;
import com.phone.directory.phoneDirectory.exception.PhoneNumberNotFoundException;
import com.phone.directory.phoneDirectory.model.CustomerPhoneNumberResponse;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
public class PhoneDirectoryController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneDirectoryController.class);

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Operation(summary = "Get all phone numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/phone-numbers")
    public List<CustomerPhoneNumberResponse> getPhoneNumbers() {
        logger.info("Fetching all phone numbers");
        return phoneNumberService.getPhoneNumberDetails();
    }

    @Operation(summary = "Get all phone numbers of a single customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "customerId does not validate")
    })
    @GetMapping("/phone-numbers/{customerId}")
    public List<PhoneNumberResponse> getPhoneNumbersForCustomer(@PathVariable @NotBlank @Pattern(regexp = "^[0-9a-fA-F\\-]{36}$", message = "Invalid customer ID format") String customerId) throws CustomerNotFoundException {
        logger.info("Fetching phone numbers for customer ID: {}", customerId);
        return phoneNumberService.customerPhoneNumbers(UUID.fromString(customerId));
    }

    @Operation(summary = "Activate a phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "phone number not found"),
            @ApiResponse(responseCode = "400", description = "phone number does not validate")
    })
    @PatchMapping("/phone-numbers/{phoneNumber}/status")
    public ResponseEntity<Void> activatePhoneNumbers(@PathVariable @NotBlank @Pattern(regexp = "^[0-9a-fA-F\\-]{36}$", message = "Invalid phone number format") String phoneNumber) throws PhoneNumberNotFoundException {
        logger.info("Activating phone number: {}", phoneNumber);
        phoneNumberService.activatePhoneNumber(UUID.fromString(phoneNumber));
        return ResponseEntity.ok().build();
    }
}