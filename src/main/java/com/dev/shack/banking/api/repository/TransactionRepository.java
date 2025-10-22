package com.dev.shack.banking.api.repository;

import com.dev.shack.banking.api.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
