package com.dev.shack.banking.api.service.mapper;

import com.dev.shack.banking.api.rest.dto.AccountDto;
import com.dev.shack.banking.api.rest.dto.CustomerDto;
import com.dev.shack.banking.api.persistence.models.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer customer, List<AccountDto> accounts) {

        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .accounts(accounts)
                .build();

    }

    public Customer toEntity(CustomerDto customerDto) {
        var customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());

        return customer;
    }
}
