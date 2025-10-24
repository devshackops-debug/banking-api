package com.dev.shack.banking.api.dto;

import com.dev.shack.banking.api.models.AccountStatus;

import java.util.List;

public record AccountDto(Long id ,
                         String accountNumber,
                         AccountStatus accountStatus,
                         Double balance,
                         List<TransactionDto> transactions,
                         Long customerId) {
}
