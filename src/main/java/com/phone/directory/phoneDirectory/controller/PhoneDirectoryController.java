package com.phone.directory.phoneDirectory.controller;


import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneDirectoryController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Operation(summary = "Get a greeting message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/phone-numbers")
    public List<PhoneNumberResponse> getPhoneNumbers() {
        return phoneNumberService.getPhoneNumberDetails();
    }

}
//,
//            @ApiResponse(responseCode = "404", description = "Resource not found")