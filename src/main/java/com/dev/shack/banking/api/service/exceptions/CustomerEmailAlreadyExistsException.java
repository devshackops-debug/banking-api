package com.dev.shack.banking.api.service.exceptions;

public class CustomerEmailAlreadyExistsException extends RuntimeException {
    public CustomerEmailAlreadyExistsException(String message) {
        super(message);
    }
}
