package com.dev.shack.banking.api.rest.exceptionHandler;

import com.dev.shack.banking.api.rest.dto.BankAPIResponse;
import com.dev.shack.banking.api.service.exceptions.AccountNumberNotFoundException;
import com.dev.shack.banking.api.service.exceptions.CustomerEmailAlreadyExistsException;
import com.dev.shack.banking.api.service.exceptions.CustomerNotFoundException;
import com.dev.shack.banking.api.service.exceptions.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    public BankAPIResponse<Void> handleCustomerEmailExists(CustomerEmailAlreadyExistsException ex) {
        return BankAPIResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();


    }

    @ExceptionHandler({AccountNumberNotFoundException.class,
            CustomerNotFoundException.class})
    public BankAPIResponse<Void> handleNotFoundExceptions(RuntimeException ex) {
        return BankAPIResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();


    }

    @ExceptionHandler(InsufficientFundsException.class)
    public BankAPIResponse<Void> handleInsufficientFundsException(InsufficientFundsException e) {
        return BankAPIResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    // : generic fallback
    @ExceptionHandler(Exception.class)
    public BankAPIResponse<Void> handleGenericException(Exception ex) {
        return BankAPIResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();

    }
}
