package com.phone.directory.phoneDirectory.repository;

import com.phone.directory.phoneDirectory.model.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository class to handle the database operations for phone numbers.
 */
@Repository
public interface PhoneRepository extends CrudRepository<Phone, UUID> {
}
