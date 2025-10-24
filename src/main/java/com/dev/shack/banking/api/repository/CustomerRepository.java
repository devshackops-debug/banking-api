package com.dev.shack.banking.api.repository;

import com.dev.shack.banking.api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
}
