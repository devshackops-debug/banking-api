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

import javax.naming.InsufficientResourcesException;
import java.time.LocalDateTime;

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


        double fromBalance = fromAccount.balance();
        if (fromBalance < dto.amount()) {
            throw new InsufficientFundsException("Insufficient funds in account ");
        }


        transactionService.createTransaction(new TransactionDto(fromAccount.id(),
                dto.amount(),
                LocalDateTime.now(),
                dto.fromAccountNumber(),
                TransactionType.DEBIT));
        transactionService.createTransaction(new TransactionDto(toAccount.id(),
                dto.amount(),
                LocalDateTime.now(),
                dto.toAccountNumber(),
                TransactionType.CREDIT));

    }
}
