package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.dto.FundsTransferDto;
import com.dev.shack.banking.api.dto.TransactionDto;
import com.dev.shack.banking.api.exceptions.InsufficientFundsException;
import com.dev.shack.banking.api.models.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final TransactionService transactionService;


    @Transactional
    public void transferFunds(FundsTransferDto dto) {


        AccountDto fromAccount = accountService.getAccountByNumber(dto.fromAccountNumber());
        AccountDto toAccount = accountService.getAccountByNumber(dto.toAccountNumber());


        double fromBalance = fromAccount.getBalance();
        if (fromBalance < dto.amount()) {
            throw new InsufficientFundsException("Insufficient funds in account ");
        }


        var debitTransaction = TransactionDto.builder()
                .amount(dto.amount())
                .accountNumber(fromAccount.getAccountNumber())
                .transactionType(TransactionType.DEBIT)
                .build();


        var creditTransaction = TransactionDto.builder()
                .amount(dto.amount())
                .accountNumber(toAccount.getAccountNumber())
                .transactionType(TransactionType.CREDIT)
                .build();

        transactionService.createTransaction(debitTransaction);
        transactionService.createTransaction(creditTransaction);

    }
}
