package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.exceptions.AccountNumberNotFoundException;
import com.dev.shack.banking.api.exceptions.CustomerNotFoundException;
import com.dev.shack.banking.api.models.Account;
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


    public AccountDto createAccount(AccountDto accountDto){

        var customer = customerRepository.findById(accountDto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        var account = new Account();
        account.setAccountNumber(accountDto.accountNumber());
        account.setStatus(accountDto.accountStatus());
        account.setCustomer(customer);
        var saved = accountRepository.save(account);
        return  new AccountDto(saved.getId(),
                saved.getAccountNumber(),
                saved.getStatus(),
                calculateBalance(accountDto.accountNumber()),
                transactionService.getTransactionsForAccount(accountDto.accountNumber()),
                customer.getId());
    }

    public List<AccountDto> getAllAccounts() {
        var accountList = accountRepository.findAll();

        return accountList.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getStatus(),
                        calculateBalance(account.getAccountNumber()),
                        transactionService.getTransactionsForAccount(account.getAccountNumber()),
                        account.getId()
                ))
                .toList();
    }

    public List<AccountDto> getAccountsForCustomerId(Long id) {

        var accountsForCustomerId = accountRepository.findByCustomer_Id(id);
        return accountsForCustomerId.stream()
                .map(account -> new AccountDto(

                        account.getId(),
                        account.getAccountNumber(),
                        account.getStatus(),
                        calculateBalance(account.getAccountNumber()),
                        transactionService.getTransactionsForAccount(account.getAccountNumber()),
                        account.getId()
                ))
                .toList();
    }

    public double calculateBalance(String accountNumber) {
        var netAmount = transactionService.getNetTransactionsAmount(accountNumber);
        return INITIAL_BALANCE + netAmount;
    }

    public AccountDto getAccountByNumber(String accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNumberNotFoundException("Account not found "+ accountNumber));

        var balance = calculateBalance(account.getAccountNumber());
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getStatus(),
                balance,
                List.of(),
                account.getCustomer().getId()
        );
    }
    public AccountDto getAccountByNumberWithTransaction(String accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNumberNotFoundException("Account not found "+ accountNumber));

        var balance = calculateBalance(account.getAccountNumber());
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getStatus(),
                balance,
                transactionService.getTransactionsForAccount(account.getAccountNumber()),
                account.getCustomer().getId()
        );
    }
}
