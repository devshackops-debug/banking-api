package com.dev.shack.banking.api.dto;

public record FundsTransferDto(
        String fromAccountNumber,
        String toAccountNumber,
        Double amount
) {}