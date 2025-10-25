package com.dev.shack.banking.api.rest.endpoints;

import com.dev.shack.banking.api.rest.dto.BankAPIResponse;
import com.dev.shack.banking.api.rest.dto.CustomerDto;
import com.dev.shack.banking.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public BankAPIResponse<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {

        return BankAPIResponse.<CustomerDto>builder()
                .data(customerService.createCustomer(customer))
                .status(HttpStatus.CREATED)
                .build();


    }

    @GetMapping
    public BankAPIResponse<List<CustomerDto>> getAllCustomers() {
        return BankAPIResponse.<List<CustomerDto>>builder()
                .data(customerService.getAllCustomers())
                .status(HttpStatus.ACCEPTED)
                .build();
    }


    @GetMapping("/{id}")
    public BankAPIResponse<CustomerDto> getCustomer(@PathVariable Long id) {

        return BankAPIResponse.<CustomerDto>builder()
                .data(customerService.getCustomerById(id))
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping
    public BankAPIResponse<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {

        return BankAPIResponse.<CustomerDto>builder()
                .data(customerService.updateCustomer(customerDto))
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public BankAPIResponse<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return BankAPIResponse.<Void>builder()
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
