package com.dev.shack.banking.api.service;

import com.dev.shack.banking.api.models.Customer;
import com.dev.shack.banking.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getOneCustomer(Long id ){
        //todo: handle null returns
        
        return customerRepository.findById(id).orElse(null);
    }

    public  Customer getCustomerByEmail(String email){

        return  customerRepository.findByEmail(email).orElse(null);
    }


}
