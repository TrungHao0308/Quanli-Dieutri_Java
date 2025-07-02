package com.hao.demo.config;

import com.hao.demo.model.Customer;
import com.hao.demo.model.Role;
import com.hao.demo.repository.CustomerRepository;
import com.hao.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Tạo các ROLE nếu chưa có
        Role adminRole = createRoleIfNotExists("ROLE_ADMIN");
        Role doctorRole = createRoleIfNotExists("ROLE_DOCTOR");
        Role managerRole = createRoleIfNotExists("ROLE_MANAGER");
        Role customerRole = createRoleIfNotExists("ROLE_CUSTOMER");

        // 2. Tạo tài khoản admin mặc định nếu chưa tồn tại
        if (!customerRepository.existsByEmail("admin@gmail.com")) {
            Customer admin = new Customer();
            admin.setFullName("Quản trị viên");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setIsActive(true);
            admin.setRoles(new HashSet<>(Set.of(adminRole))); // gán ROLE_ADMIN

            customerRepository.save(admin);
            System.out.println("✔️ Admin account created: admin@gmail.com / admin123");
        }
    }

    private Role createRoleIfNotExists(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
