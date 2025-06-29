package com.hao.demo.service;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.repository.BacsiChuyenmonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private BacsiChuyenmonRepository bacsiChuyenmonRepository;

    @Override
    public Optional<BacsiChuyenmon> findByEmail(String email) {
        return Optional.ofNullable(bacsiChuyenmonRepository.findByEmail(email));
    }
}
