package com.phone.directory.phoneDirectory.repository;

import com.phone.directory.phoneDirectory.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository class to handle the database operations for customers.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
