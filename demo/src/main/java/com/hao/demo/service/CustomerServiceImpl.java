package com.hao.demo.service;

import com.hao.demo.dto.CustomerLoginDto;
import com.hao.demo.dto.CustomerRegistrationDto;
import com.hao.demo.model.Customer;
import com.hao.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer registerCustomer(CustomerRegistrationDto registrationDto) throws Exception {
        if (customerRepository.existsByEmail(registrationDto.getEmail())) {
            throw new Exception("Email đã được sử dụng!");
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new Exception("Mật khẩu xác nhận không khớp!");
        }
        Customer customer = new Customer();
        customer.setFullName(registrationDto.getFullName());
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setPhone(registrationDto.getPhone());
        customer.setAddress(registrationDto.getAddress());
        customer.setDateOfBirth(registrationDto.getDateOfBirth());
        customer.setGender(registrationDto.getGender());
        // customer.setActive(true); // Thêm sau khi sửa model Customer
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> loginCustomer(CustomerLoginDto loginDto) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(loginDto.getEmail());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        System.out.println("Tìm khách hàng với email: " + email);
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            System.out.println("Tìm thấy khách hàng: " + customer.get().getFullName());
        } else {
            System.out.println("Không tìm thấy khách hàng với email: " + email);
        }
        return customer;
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}