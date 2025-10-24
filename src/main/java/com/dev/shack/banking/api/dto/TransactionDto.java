package com.dev.shack.banking.api.dto;

import com.dev.shack.banking.api.models.TransactionType;

import java.time.LocalDateTime;

public record TransactionDto(Long id,
                             Double amount,
                             LocalDateTime timestamp,
                             String accountNumber,
                             TransactionType transactionType) {
}
