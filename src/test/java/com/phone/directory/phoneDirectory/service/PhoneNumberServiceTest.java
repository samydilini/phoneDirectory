package com.phone.directory.phoneDirectory.service;

import com.phone.directory.phoneDirectory.model.Customer;
import com.phone.directory.phoneDirectory.model.Phone;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class PhoneNumberServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPhoneNumberDetails_returnsPhoneNumberDetails() {
        Customer customer = new Customer(UUID.randomUUID(), "John", "Doe", "Mr.", "123 Street");
        Phone phone = new Phone(UUID.randomUUID(), 1234567890L, customer, true, new java.sql.Date(System.currentTimeMillis()));
        when(phoneRepository.findAll()).thenReturn(Collections.singletonList(phone));

        List<PhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(1, result.size());
        PhoneNumberResponse firstPhoneResponse = result.getFirst();
        assertEquals(1234567890L, firstPhoneResponse.phoneNumber());
        assertEquals("John", firstPhoneResponse.customerName());
        assertNotNull(firstPhoneResponse.customerId());
        assertEquals(true, firstPhoneResponse.status());
    }

    @Test
    void getPhoneNumberDetails_returnsEmptyListWhenNoPhones() {
        when(phoneRepository.findAll()).thenReturn(Collections.emptyList());

        List<PhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(0, result.size());
    }

    @Test
    void getPhoneNumberDetails_handlesMultiplePhones() {
        Customer customer1 = new Customer(UUID.randomUUID(), "John", "Doe", "Mr.", "123 Street");
        Customer customer2 = new Customer(UUID.randomUUID(), "Jane", "Doe", "Ms.", "456 Avenue");
        Phone phone1 = new Phone(UUID.randomUUID(), 1234567890L, customer1, true, new java.sql.Date(System.currentTimeMillis()));
        Phone phone2 = new Phone(UUID.randomUUID(), 9876543210L, customer2, false, new java.sql.Date(System.currentTimeMillis()));
        when(phoneRepository.findAll()).thenReturn(Arrays.asList(phone1, phone2));

        List<PhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(2, result.size());
        assertEquals(1234567890L, result.getFirst().phoneNumber());
        assertEquals("John", result.get(0).customerName());
        assertEquals(true, result.get(0).status());
        assertEquals(9876543210L, result.get(1).phoneNumber());
        assertEquals("Jane", result.get(1).customerName());
        assertEquals(false, result.get(1).status());
    }
}