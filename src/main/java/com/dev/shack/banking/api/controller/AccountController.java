package com.dev.shack.banking.api.controller;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.dto.BankAPIResponse;
import com.dev.shack.banking.api.dto.CustomerDto;
import com.dev.shack.banking.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public BankAPIResponse<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return  BankAPIResponse.<AccountDto>builder()
                .data(accountService.createAccount(accountDto))
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @GetMapping
    public BankAPIResponse<List<AccountDto>> fetchAllAccounts() {
        return  BankAPIResponse.<List<AccountDto>>builder()
                .data(accountService.getAllAccounts())
                .status(HttpStatus.OK)
                .build();

    }

    @GetMapping("/{accountNumber}")
    public BankAPIResponse<AccountDto> fetchAccount(@PathVariable String accountNumber) {

        return  BankAPIResponse.<AccountDto>builder()
                .data(accountService.getAccountByNumberWithTransaction(accountNumber))
                .status(HttpStatus.OK)
                .build();
    }
}
