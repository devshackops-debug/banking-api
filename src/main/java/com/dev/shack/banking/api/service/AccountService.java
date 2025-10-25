package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.exceptions.AccountNumberNotFoundException;
import com.dev.shack.banking.api.exceptions.CustomerNotFoundException;
import com.dev.shack.banking.api.mapper.AccountMapper;
import com.dev.shack.banking.api.repository.AccountRepository;
import com.dev.shack.banking.api.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;
    private  final AccountMapper accountMapper;


    public AccountDto createAccount(AccountDto accountDto){

        var customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

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
                .orElseThrow(() -> new AccountNumberNotFoundException("Account not found "+ accountNumber));

        var balance = calculateBalance(account.getAccountNumber());
        var transactions = transactionService.getTransactionsForAccount(account.getAccountNumber());
        return accountMapper.toDto(account, balance, transactions);
    }

    private double calculateBalance(String accountNumber) {
        var netAmount = transactionService.getNetTransactionsAmount(accountNumber);
        return INITIAL_BALANCE + netAmount;
    }
}
