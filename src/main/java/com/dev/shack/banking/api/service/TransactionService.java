package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.rest.dto.TransactionDto;
import com.dev.shack.banking.api.service.mapper.TransactionMapper;
import com.dev.shack.banking.api.persistence.models.Account;
import com.dev.shack.banking.api.persistence.models.Transaction;
import com.dev.shack.banking.api.persistence.repository.AccountRepository;
import com.dev.shack.banking.api.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;


    public void createTransaction(TransactionDto transactionDto) {

        Account account = accountRepository.findByAccountNumber((transactionDto.getAccountNumber()))
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setAccount(account);

        transactionRepository.save(transaction);


    }

    public List<TransactionDto> getTransactionsForAccount(String accountNumber) {
        var transactions = transactionRepository.findByAccount_AccountNumber(accountNumber);
        return transactions.stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    public double getNetTransactionsAmount(String accountNumber) {
        return transactionRepository.computeNetTransactionAmount(accountNumber);
    }


}
