package com.hao.demo.repository;

import java.time.LocalDate;
import java.util.List;

import com.hao.demo.model.PhancongLichkham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhancongLichkhamRepository extends JpaRepository<PhancongLichkham, Long> {

    List<PhancongLichkham> findByEmailBenhNhan(String emailBenhNhan);

    List<PhancongLichkham> findByEmailBacSi(String emailBacSi);

    List<PhancongLichkham> findByEmailBacSiOrderByNgayKhamAsc(String emailBacSi);

    List<PhancongLichkham> findByEmailBacSiAndNgayKhamOrderByLichKhamAsc(String emailBacSi, LocalDate ngayKham);
List<PhancongLichkham> findByEmailBacSiOrderByNgayKhamAscLichKhamAsc(String emailBacSi);
PhancongLichkham findFirstByEmailBenhNhan(String email);

@Query("SELECT DISTINCT p.emailBenhNhan FROM PhancongLichkham p WHERE p.emailBacSi = :emailBacSi")
List<String> findDistinctEmailBenhNhanByEmailBacSi(@Param("emailBacSi") String emailBacSi);


}
