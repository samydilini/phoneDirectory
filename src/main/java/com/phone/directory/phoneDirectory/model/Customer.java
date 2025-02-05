package com.phone.directory.phoneDirectory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    public Customer(UUID id, String firstName, String surname, String title, String address) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.title = title;
        this.address = address;
    }
}
