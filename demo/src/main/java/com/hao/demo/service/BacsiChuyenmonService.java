package com.hao.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hao.demo.repository.BacsiChuyenmonRepository;

@Service
public class BacsiChuyenmonService {

    @Autowired
    private BacsiChuyenmonRepository repository;

    @Transactional
    public void deleteByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
    }
}
