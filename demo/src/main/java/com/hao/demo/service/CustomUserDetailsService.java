package com.hao.demo.service;

import com.hao.demo.model.Customer;
import com.hao.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm customer đang active theo email
        Customer customer = customerRepository.findActiveCustomerByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Tạo UserDetails để Spring Security sử dụng xác thực
        return org.springframework.security.core.userdetails.User.builder()
                .username(customer.getEmail())       // email làm username
                .password(customer.getPassword())    // password đã được mã hóa
                .authorities(new ArrayList<>())      // Thêm quyền nếu có
                .build();
    }
}

