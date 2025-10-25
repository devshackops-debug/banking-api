package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.TransactionDto;
import com.dev.shack.banking.api.mapper.TransactionMapper;
import com.dev.shack.banking.api.models.Account;
import com.dev.shack.banking.api.models.Transaction;
import com.dev.shack.banking.api.repository.AccountRepository;
import com.dev.shack.banking.api.repository.TransactionRepository;
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
                .orElseThrow(()-> new RuntimeException("Account not found"));

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
