package com.hao.demo.repository;

import com.hao.demo.model.BacsiChuyenmon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BacsiChuyenmonRepository extends JpaRepository<BacsiChuyenmon, Long> {

    BacsiChuyenmon findByEmail(String email);

    Optional<BacsiChuyenmon> findByCustomerId(Long customerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BacsiChuyenmon b WHERE b.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
