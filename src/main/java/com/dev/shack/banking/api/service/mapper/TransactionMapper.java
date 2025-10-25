package com.dev.shack.banking.api.service.mapper;

import com.dev.shack.banking.api.rest.dto.TransactionDto;
import com.dev.shack.banking.api.persistence.models.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto toDto(Transaction transaction) {

        return TransactionDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .timestamp(transaction.getCreatedAt())
                .build();

    }

    public Transaction toEntity(TransactionDto transactionDto) {
        var transaction = new Transaction();
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAmount(transactionDto.getAmount());

        return transaction;
    }
}
