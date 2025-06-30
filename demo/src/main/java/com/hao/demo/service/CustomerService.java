
package com.hao.demo.service;

import com.hao.demo.dto.CustomerLoginDto;
import com.hao.demo.dto.CustomerRegistrationDto;
import com.hao.demo.model.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer registerCustomer(CustomerRegistrationDto registrationDto) throws Exception;
    Optional<Customer> loginCustomer(CustomerLoginDto loginDto);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    Customer saveCustomer(Customer customer);
    boolean validatePassword(String rawPassword, String encodedPassword);
    List<Customer> getAllCustomers();
    Optional<Customer> findById(Long id);
    Customer addCustomer(Customer customer);
    // Customer updateCustomer(Customer customer);
    void deleteCustomer(Long id);
    PasswordEncoder getPasswordEncoder();
}