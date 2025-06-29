package com.hao.demo.repository;

import com.hao.demo.model.LichTrungGian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichTrungGianRepository extends JpaRepository<LichTrungGian, Long> {
    List<LichTrungGian> findByEmailBenhNhan(String email);
    List<LichTrungGian> findByEmailBacSi(String email);
}
