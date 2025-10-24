package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.dto.CustomerDto;
import com.dev.shack.banking.api.exceptions.CustomerEmailAlreadyExistsException;
import com.dev.shack.banking.api.exceptions.CustomerNotFoundException;
import com.dev.shack.banking.api.models.Customer;
import com.dev.shack.banking.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    public CustomerDto createCustomer(CustomerDto customerDto){

       var optionalCustomer = customerRepository.findCustomerByEmail(customerDto.email());

        if (optionalCustomer.isPresent())
            throw new CustomerEmailAlreadyExistsException("Customer with email " + customerDto.email() + " already exists");

        var customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());

        var saved = customerRepository.save(customer);
        return  new CustomerDto(saved.getId(), saved.getName(),  saved.getEmail(), null);

    }

    public List<CustomerDto> getAllCustomers(){
        var allCustomers = customerRepository.findAll();

        return allCustomers.stream()
                .map(customer -> (
                    new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(),
                            accountService.getAccountsForCustomerId(customer.getId()))
                )).toList();
    }

    public CustomerDto getCustomerById(Long id){

        var customerRepositoryById = customerRepository.findById(id);
        if (customerRepositoryById.isPresent()){
            var customer = customerRepositoryById.get();
            return  new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(),
                    accountService.getAccountsForCustomerId(customer.getId()));
        }else {
            throw new CustomerNotFoundException("Customer with id not found");
        }
    }

}
