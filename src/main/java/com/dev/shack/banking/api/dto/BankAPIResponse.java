package com.dev.shack.banking.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BankAPIResponse<T> {

    private T data;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private HttpStatus status;
    @Builder.Default
    private String message = "Success";
}
