package com.dev.shack.banking.api.persistence.repository;

import com.dev.shack.banking.api.persistence.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_AccountNumber(String accountNumber);

    @Query("""
        SELECT COALESCE(SUM(
            CASE
                WHEN t.transactionType = 'CREDIT' THEN t.amount
                WHEN t.transactionType = 'DEBIT' THEN -t.amount
                ELSE 0
            END
        ), 0)
        FROM Transaction t
        WHERE t.account.accountNumber = :accountNumber
    """)
    Double computeNetTransactionAmount(@Param("accountNumber") String accountNumber);
}
