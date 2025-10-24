package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.TransactionDto;
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


    public void createTransaction(TransactionDto transactionDto) {

        Account account = accountRepository.findByAccountNumber((transactionDto.accountNumber()))
                .orElseThrow(()-> new RuntimeException("Account not found"));

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDto.transactionType());
        transaction.setAmount(transactionDto.amount());
        transaction.setAccount(account);

        Transaction saved = transactionRepository.save(transaction);

        new TransactionDto(saved.getId(),
                saved.getAmount(),
                saved.getCreatedAt(),
                saved.getAccount().getAccountNumber(),
                saved.getTransactionType());
    }



    public List<TransactionDto> getTransactionsForAccount(String accountNumber) {
        var transactions = transactionRepository.findByAccount_AccountNumber(accountNumber);
        return transactions.stream()
                .map(txn -> new TransactionDto(
                        txn.getId(),
                        txn.getAmount(),
                        txn.getCreatedAt(),
                        txn.getAccount().getAccountNumber(),
                        txn.getTransactionType()
                ))
                .toList();
    }

    public double getNetTransactionsAmount(String accountNumber) {
        return transactionRepository.computeNetTransactionAmount(accountNumber);
    }


}
