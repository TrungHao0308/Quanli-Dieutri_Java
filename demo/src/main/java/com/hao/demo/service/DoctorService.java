package com.hao.demo.service;

import com.hao.demo.model.BacsiChuyenmon;

import java.util.Optional;

public interface DoctorService {
    Optional<BacsiChuyenmon> findByEmail(String email);
}
