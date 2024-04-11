package it.cgmconsulting.boccia.service;

import it.cgmconsulting.boccia.repository.CustomerRepository;

import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }




}
