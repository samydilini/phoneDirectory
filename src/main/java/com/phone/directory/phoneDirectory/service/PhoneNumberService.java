package com.phone.directory.phoneDirectory.service;

import com.phone.directory.phoneDirectory.model.Phone;
import com.phone.directory.phoneDirectory.model.PhoneNumberResponse;
import com.phone.directory.phoneDirectory.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PhoneNumberService {
    @Autowired
    private PhoneRepository phoneRepository;

    public List<PhoneNumberResponse> getPhoneNumberDetails() {

        Iterable<Phone> phones = phoneRepository.findAll();
        return StreamSupport.stream(phones.spliterator(), false).map(phone -> new PhoneNumberResponse(phone.getPhoneNumber(), phone.getCustomer().getFirstName(), phone.getCustomer().getId().toString(), phone.getStatus())).toList();
    }
}
