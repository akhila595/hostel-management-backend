package com.hostel_management.repository;

import com.hostel_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndStatus(
            String email,
            String status
    );

    boolean existsByEmail(String email);
}