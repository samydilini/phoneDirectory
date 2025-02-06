package com.phone.directory.phoneDirectory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity class to represent the customer details.
 */
@Entity
@Table(name = "customer", schema = "my_schema")
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    private String surname;
    private String title;
    private String address;
    @OneToMany(mappedBy = "customer")
    private List<Phone> phoneNumbers;

    public Customer(UUID id, String firstName, String surname, String title, String address) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.title = title;
        this.address = address;
    }

    public void setPhoneNumbers(List<Phone> phones) {
        if(this.phoneNumbers == null || this.phoneNumbers.isEmpty()){
            this.phoneNumbers = new ArrayList<>();
        }
        this.phoneNumbers.addAll(phones);
    }
}
