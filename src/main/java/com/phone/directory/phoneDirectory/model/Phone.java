package com.phone.directory.phoneDirectory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.UUID;


@Entity
@Table(name = "phone", schema = "my_schema")
@Getter
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "status")
    private Boolean status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "last_updated")
    private Date lastUpdated;

    public Phone(UUID id, String phoneNumber, Customer customer, Boolean status, Date lastUpdated) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.customer = customer;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}