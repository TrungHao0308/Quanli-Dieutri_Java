package com.hao.demo.repository;

import com.hao.demo.model.Danhgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhgiaRepository extends JpaRepository<Danhgia, Long> {
}
