package com.hao.demo.repository;

import com.hao.demo.model.KetquaKhambenh;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KetquaKhambenhRepository extends JpaRepository<KetquaKhambenh, Long> {
    List<KetquaKhambenh> findByEmailBenhNhanOrderByNgayKhamDesc(String emailBenhNhan);
}
