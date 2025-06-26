package com.hao.demo.repository;
import java.util.List;
import com.hao.demo.model.PhancongLichkham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhancongLichkhamRepository extends JpaRepository<PhancongLichkham, Long> {
    List<PhancongLichkham> findByEmailBenhNhan(String emailBenhNhan);

}
