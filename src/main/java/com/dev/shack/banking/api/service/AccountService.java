package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.persistence.repository.AccountRepository;
import com.dev.shack.banking.api.rest.dto.AccountDto;
import com.dev.shack.banking.api.service.exceptions.AccountNumberNotFoundException;
import com.dev.shack.banking.api.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    static final double INITIAL_BALANCE = 100_000.0;

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;


    public AccountDto createAccount(AccountDto accountDto) {

        var customer = customerService.getCustomer(accountDto.getCustomerId());
        var account = accountMapper.toEntity(accountDto);
        account.setCustomer(customer);
        var saved = accountRepository.save(account);
        return accountMapper.toDto(saved, INITIAL_BALANCE, List.of());
    }

    public List<AccountDto> getAllAccounts() {
        var accountList = accountRepository.findAll();

        return accountList.stream()
                .map(account -> {
                    var balance = calculateBalance(account.getAccountNumber());
                    var transactions = transactionService.getTransactionsForAccount(account.getAccountNumber());

                    return accountMapper.toDto(account, balance, transactions);
                })
                .toList();

    }

    public List<AccountDto> getAccountsForCustomerId(Long id) {

        var accountsForCustomerId = accountRepository.findByCustomer_Id(id);
        return accountsForCustomerId.stream()
                .map(account -> {
                    var balance = calculateBalance(account.getAccountNumber());
                    var transactions = transactionService.getTransactionsForAccount(account.getAccountNumber());

                    return accountMapper.toDto(account, balance, transactions);
                })
                .toList();

    }


    public AccountDto getAccountByNumber(String accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNumberNotFoundException("Account not found " + accountNumber));

        var balance = calculateBalance(account.getAccountNumber());
        var transactions = transactionService.getTransactionsForAccount(account.getAccountNumber());
        return accountMapper.toDto(account, balance, transactions);
    }

    private double calculateBalance(String accountNumber) {
        var netAmount = transactionService.getNetTransactionsAmount(accountNumber);
        return INITIAL_BALANCE + netAmount;
    }
}
