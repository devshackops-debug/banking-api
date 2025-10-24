package com.dev.shack.banking.api.repository;

import com.dev.shack.banking.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomer_Id(Long customerId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
