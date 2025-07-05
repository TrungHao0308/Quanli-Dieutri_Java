package com.hao.demo.repository;

import com.hao.demo.model.DangkiDichvu;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional; 

public interface DangkiDichvuRepository extends JpaRepository<DangkiDichvu, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM DangkiDichvu d WHERE d.emailBenhNhan = :email AND d.ngayKham = :ngayKham")
    void deleteByEmailAndNgayKham(@Param("email") String email, @Param("ngayKham") LocalDate ngayKham);
}
