package com.dev.shack.banking.api.exceptions;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String message) {
        super(message);
    }
}
