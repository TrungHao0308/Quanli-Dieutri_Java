package com.hao.demo.repository;
import com.hao.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT c FROM Customer c WHERE c.email = :email AND c.isActive = true")
    Optional<Customer> findActiveCustomerByEmail(@Param("email") String email);
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.isActive = true")
    long countActiveCustomers();

    @Query("SELECT c FROM Customer c JOIN c.roles r WHERE r.name = 'ROLE_DOCTOR'")
List<Customer> findAllDoctors();

@Query("SELECT c FROM Customer c JOIN c.roles r WHERE r.name = :roleName")
List<Customer> findByRoleName(@Param("roleName") String roleName);

}
