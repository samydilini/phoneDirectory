package com.phone.directory.phoneDirectory.service;

import com.phone.directory.phoneDirectory.exception.CustomerNotFoundException;
import com.phone.directory.phoneDirectory.exception.PhoneNumberNotFoundException;
import com.phone.directory.phoneDirectory.model.Customer;
import com.phone.directory.phoneDirectory.model.CustomerPhoneNumberResponse;
import com.phone.directory.phoneDirectory.model.Phone;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.repository.CustomerRepository;
import com.phone.directory.phoneDirectory.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Service class to handle the business logic for phone number related operations.
 */
@Service
public class PhoneNumberService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberService.class);
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerPhoneNumberResponse> getPhoneNumberDetails() {

        Iterable<Phone> phones = phoneRepository.findAll();
        return StreamSupport.stream(phones.spliterator(), false).map(phone -> new CustomerPhoneNumberResponse(phone.getPhoneNumber(), phone.getCustomer().getFirstName(), phone.getCustomer().getId().toString())).toList();
    }

    /**
     * Method to get the phone numbers for a customer
     *
     * @param customerId the customer Id
     * @return the list of phone numbers for the customer
     * @throws CustomerNotFoundException if the customer is not found
     */
    public List<PhoneNumberResponse> customerPhoneNumbers(UUID customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer for the Id " + customerId + " not found"));

        return customer.getPhoneNumbers().stream()
                .map(phone -> new PhoneNumberResponse(phone.getId().toString(), phone.getPhoneNumber(), phone.getStatus()))
                .toList();
    }

    /**
     * Method to activate a phone number
     *
     * @param uuid the phone number Id
     * @throws PhoneNumberNotFoundException if the phone number is not found
     */
    public void activatePhoneNumber(UUID uuid) throws PhoneNumberNotFoundException {
        Phone phone = phoneRepository.findById(uuid)
                .orElseThrow(() -> new PhoneNumberNotFoundException("Phone number for the Id " + uuid + " not found"));

        phone.setStatus(true);
        phoneRepository.save(phone);
        logger.info("Phone number with Id {} has been activated", uuid);
    }
}
