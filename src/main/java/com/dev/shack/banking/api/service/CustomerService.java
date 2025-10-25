package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.AccountDto;
import com.dev.shack.banking.api.dto.CustomerDto;
import com.dev.shack.banking.api.exceptions.CustomerEmailAlreadyExistsException;
import com.dev.shack.banking.api.exceptions.CustomerNotFoundException;
import com.dev.shack.banking.api.mapper.CustomerMapper;
import com.dev.shack.banking.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final CustomerMapper customerMapper;

    public CustomerDto createCustomer(CustomerDto customerDto){

       var optionalCustomer = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (optionalCustomer.isPresent())
            throw new CustomerEmailAlreadyExistsException("Customer with email " + customerDto.getEmail() + " already exists");

        var saved = customerRepository.save(customerMapper.toEntity(customerDto));
        return  customerMapper.toDto(saved, List.of());

    }

    public List<CustomerDto> getAllCustomers(){
        var allCustomers = customerRepository.findAll();

        return allCustomers.stream()
                .map(customer -> {
                    List<AccountDto> accountsForCustomerId = accountService.getAccountsForCustomerId(customer.getId());
                    return (customerMapper.toDto(customer, accountsForCustomerId));
                }).toList();
    }

    public CustomerDto getCustomerById(Long id){

        var customerRepositoryById = customerRepository.findById(id);
        if (customerRepositoryById.isPresent()){
            var customer = customerRepositoryById.get();
            return  customerMapper.toDto(customer, accountService.getAccountsForCustomerId(customer.getId()));
        }else {
            throw new CustomerNotFoundException("Customer with id not found");
        }
    }

}
