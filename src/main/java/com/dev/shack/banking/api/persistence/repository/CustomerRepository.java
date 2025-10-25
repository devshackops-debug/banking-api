package com.dev.shack.banking.api.persistence.repository;

import com.dev.shack.banking.api.persistence.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
}
