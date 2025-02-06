package com.phone.directory.phoneDirectory.service;

import com.phone.directory.phoneDirectory.exception.CustomerNotFoundException;
import com.phone.directory.phoneDirectory.exception.PhoneNumberNotFoundException;
import com.phone.directory.phoneDirectory.model.Customer;
import com.phone.directory.phoneDirectory.model.Phone;
import com.phone.directory.phoneDirectory.model.CustomerPhoneNumberResponse;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.repository.CustomerRepository;
import com.phone.directory.phoneDirectory.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("resource")
public class PhoneNumberServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPhoneNumberDetails_returnsPhoneNumberDetails() {
        Customer customer = new Customer(UUID.randomUUID(), "John", "Doe", "Mr.", "123 Street");
        Phone phone = new Phone(UUID.randomUUID(), "1234567890", customer, true, new java.sql.Date(System.currentTimeMillis()));
        when(phoneRepository.findAll()).thenReturn(Collections.singletonList(phone));

        List<CustomerPhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(1, result.size());
        CustomerPhoneNumberResponse firstPhoneResponse = result.getFirst();
        assertEquals("1234567890", firstPhoneResponse.phoneNumber());
        assertEquals("John", firstPhoneResponse.customerName());
        assertNotNull(firstPhoneResponse.customerId());
    }

    @Test
    void getPhoneNumberDetails_returnsEmptyListWhenNoPhones() {
        when(phoneRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerPhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(0, result.size());
    }

    @Test
    void getPhoneNumberDetails_handlesMultiplePhones() {
        Customer customer1 = new Customer(UUID.randomUUID(), "John", "Doe", "Mr.", "123 Street");
        Customer customer2 = new Customer(UUID.randomUUID(), "Jane", "Doe", "Ms.", "456 Avenue");
        Phone phone1 = new Phone(UUID.randomUUID(), "1234567890", customer1, true, new java.sql.Date(System.currentTimeMillis()));
        Phone phone2 = new Phone(UUID.randomUUID(), "9876543210", customer2, false, new java.sql.Date(System.currentTimeMillis()));
        when(phoneRepository.findAll()).thenReturn(Arrays.asList(phone1, phone2));

        List<CustomerPhoneNumberResponse> result = phoneNumberService.getPhoneNumberDetails();

        assertEquals(2, result.size());
        assertEquals("1234567890", result.get(0).phoneNumber());
        assertEquals("John", result.get(0).customerName());
        assertEquals("9876543210", result.get(1).phoneNumber());
        assertEquals("Jane", result.get(1).customerName());
    }

    @Test
    void customerPhoneNumbers_validCustomerId_returnsPhoneNumbers() throws CustomerNotFoundException {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "Doe", "Mr.", "123 Street");
        Phone phone = new Phone(UUID.randomUUID(), "1234567890", customer, true, new java.sql.Date(System.currentTimeMillis()));
        customer.setPhoneNumbers(Collections.singletonList(phone));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        List<PhoneNumberResponse> result = phoneNumberService.customerPhoneNumbers(customerId);

        assertEquals(1, result.size());
        PhoneNumberResponse firstPhoneResponse = result.getFirst();
        assertEquals("1234567890", firstPhoneResponse.phoneNumber());
        assertTrue(firstPhoneResponse.status());
    }

    @Test
    void customerPhoneNumbers_invalidCustomerId_throwsCustomerNotFoundException() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> phoneNumberService.customerPhoneNumbers(customerId));
    }

    @Test
    void activatePhoneNumber_validPhoneNumber_activatesPhoneNumber() throws PhoneNumberNotFoundException {
        UUID phoneId = UUID.randomUUID();
        Phone phone = new Phone(phoneId, "1234567890", new Customer(), false, new java.sql.Date(System.currentTimeMillis()));
        when(phoneRepository.findById(phoneId)).thenReturn(Optional.of(phone));

        phoneNumberService.activatePhoneNumber(phoneId);

        assertTrue(phone.getStatus());
        verify(phoneRepository, times(1)).save(phone);
    }

    @Test
    void activatePhoneNumber_invalidPhoneNumber_throwsPhoneNumberNotFoundException() {
        UUID phoneId = UUID.randomUUID();
        when(phoneRepository.findById(phoneId)).thenReturn(Optional.empty());

        assertThrows(PhoneNumberNotFoundException.class, () -> phoneNumberService.activatePhoneNumber(phoneId));
    }
}