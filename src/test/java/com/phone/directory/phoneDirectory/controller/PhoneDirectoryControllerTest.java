package com.phone.directory.phoneDirectory.controller;

import com.phone.directory.phoneDirectory.exception.CustomerNotFoundException;
import com.phone.directory.phoneDirectory.exception.PhoneNumberNotFoundException;
import com.phone.directory.phoneDirectory.model.CustomerPhoneNumberResponse;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SuppressWarnings("resource")
class PhoneDirectoryControllerTest {

    @Mock
    private PhoneNumberService phoneNumberService;

    @InjectMocks
    private PhoneDirectoryController phoneDirectoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPhoneNumbers_returnsListOfPhoneNumbers() {
        List<CustomerPhoneNumberResponse> expectedResponse = List.of(
                new CustomerPhoneNumberResponse("1234567890", "John", "1"),
                new CustomerPhoneNumberResponse("0987654321", "Jane", "2")
        );
        when(phoneNumberService.getPhoneNumberDetails()).thenReturn(expectedResponse);

        List<CustomerPhoneNumberResponse> actualResponse = phoneDirectoryController.getPhoneNumbers();

        assertEquals(expectedResponse, actualResponse);
        verify(phoneNumberService, times(1)).getPhoneNumberDetails();
    }

    @Test
    void getPhoneNumbersForCustomer_validCustomerId_returnsListOfPhoneNumbers() throws CustomerNotFoundException {
        UUID customerId = UUID.randomUUID();
        List<PhoneNumberResponse> expectedResponse = List.of(
                new PhoneNumberResponse("1", "1234567890", true),
                new PhoneNumberResponse("2", "0987654321", false)
        );
        when(phoneNumberService.customerPhoneNumbers(customerId)).thenReturn(expectedResponse);

        List<PhoneNumberResponse> actualResponse = phoneDirectoryController.getPhoneNumbersForCustomer(customerId.toString());

        assertEquals(expectedResponse, actualResponse);
        verify(phoneNumberService, times(1)).customerPhoneNumbers(customerId);
    }

    @Test
    void getPhoneNumbersForCustomer_invalidCustomerId_throwsCustomerNotFoundException() throws CustomerNotFoundException {
        UUID customerId = UUID.randomUUID();
        when(phoneNumberService.customerPhoneNumbers(customerId)).thenThrow(new CustomerNotFoundException("Customer not found"));

        assertThrows(CustomerNotFoundException.class, () -> phoneDirectoryController.getPhoneNumbersForCustomer(customerId.toString()));
        verify(phoneNumberService, times(1)).customerPhoneNumbers(customerId);
    }

    @Test
    void activatePhoneNumbers_validPhoneNumber_activatesPhoneNumber() throws PhoneNumberNotFoundException {
        UUID phoneNumber = UUID.randomUUID();

        ResponseEntity<Void> response = phoneDirectoryController.activatePhoneNumbers(phoneNumber.toString());

        assertEquals(ResponseEntity.ok().build(), response);
        verify(phoneNumberService, times(1)).activatePhoneNumber(phoneNumber);
    }

    @Test
    void activatePhoneNumbers_invalidPhoneNumber_throwsPhoneNumberNotFoundException() throws PhoneNumberNotFoundException {
        UUID phoneNumber = UUID.randomUUID();
        doThrow(new PhoneNumberNotFoundException("Phone number not found")).when(phoneNumberService).activatePhoneNumber(phoneNumber);

        assertThrows(PhoneNumberNotFoundException.class, () -> phoneDirectoryController.activatePhoneNumbers(phoneNumber.toString()));
        verify(phoneNumberService, times(1)).activatePhoneNumber(phoneNumber);
    }
}