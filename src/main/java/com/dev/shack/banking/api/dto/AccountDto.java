package com.dev.shack.banking.api.dto;

import com.dev.shack.banking.api.models.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    private AccountStatus accountStatus;
    private Double balance;
    private List<TransactionDto> transactions;
    private Long customerId;
}