package com.dev.shack.banking.api.rest.endpoints;

import com.dev.shack.banking.api.rest.dto.BankAPIResponse;
import com.dev.shack.banking.api.rest.dto.FundsTransferDto;
import com.dev.shack.banking.api.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class FundsTransferController {

    private final TransferService transferService;

    @PostMapping
    public BankAPIResponse<String> handleFundsTransfer(@RequestBody FundsTransferDto fundsTransferDto) {

        transferService.transferFunds(fundsTransferDto);
        return
                BankAPIResponse.<String>builder()
                        .data("Transfer of US$" + fundsTransferDto.amount() +
                                " successful " + "from " + fundsTransferDto.fromAccountNumber() +
                                " to " + fundsTransferDto.toAccountNumber())
                        .status(HttpStatus.ACCEPTED)
                        .build();
    }
}
