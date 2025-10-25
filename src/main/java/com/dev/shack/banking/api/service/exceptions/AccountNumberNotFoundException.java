package com.dev.shack.banking.api.service.exceptions;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String message) {
        super(message);
    }
}
