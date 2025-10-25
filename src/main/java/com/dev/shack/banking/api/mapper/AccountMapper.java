package com.dev.shack.banking.api.mapper;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.dto.TransactionDto;
import com.dev.shack.banking.api.models.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

    public Account toEntity(AccountDto accountDto) {
        var account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setStatus(accountDto.getAccountStatus());

        return account;
    }

    public AccountDto toDto(Account account,
                            Double balance,
                            List<TransactionDto> transactions) {
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountStatus(account.getStatus())
                .balance(balance)
                .transactions(transactions)
                .customerId(account.getCustomer().getId())
                .build();
    }
}

