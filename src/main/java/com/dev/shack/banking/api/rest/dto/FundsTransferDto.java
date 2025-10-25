package com.dev.shack.banking.api.rest.dto;

public record FundsTransferDto(
        String fromAccountNumber,
        String toAccountNumber,
        Double amount
) {}