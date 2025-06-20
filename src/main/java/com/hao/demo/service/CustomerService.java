package com.hao.demo.service;

import com.hao.demo.dto.CustomerLoginDto;
import com.hao.demo.dto.CustomerRegistrationDto;
import com.hao.demo.model.Customer;
import java.util.Optional;

public interface CustomerService {
    
    Customer registerCustomer(CustomerRegistrationDto registrationDto) throws Exception;
    
    Optional<Customer> loginCustomer(CustomerLoginDto loginDto);
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    Customer saveCustomer(Customer customer);
    
    boolean validatePassword(String rawPassword, String encodedPassword);
}