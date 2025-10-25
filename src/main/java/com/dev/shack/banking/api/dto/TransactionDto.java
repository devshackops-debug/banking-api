package com.dev.shack.banking.api.dto;

import com.dev.shack.banking.api.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Double amount;
    private LocalDateTime timestamp;
    private String accountNumber;
    private TransactionType transactionType;
}