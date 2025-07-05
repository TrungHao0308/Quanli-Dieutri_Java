package com.hao.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.repository.BacsiChuyenmonRepository;

@Service
public class BacsiChuyenmonService {

    @Autowired
    private BacsiChuyenmonRepository repository;

    @Transactional
    public void deleteByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
    }
    public List<BacsiChuyenmon> getDoctorsBySpeciality(String chuyenMon) {
        return repository.findByChuyenMonIgnoreCase(chuyenMon);
    }
    public List<String> getDistinctSpecialities() {
    return repository.findAll().stream()

            .map(BacsiChuyenmon::getChuyenMon)
            .distinct()
            .collect(Collectors.toList());
}

}
