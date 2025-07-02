package com.hao.demo;

import com.hao.demo.model.Role;
import com.hao.demo.model.RoleName;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.repository.RoleRepository;
import com.hao.demo.repository.DangkiDichvuRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			for (RoleName roleName : RoleName.values()) {
				roleRepository.findByName(roleName.name())
					.orElseGet(() -> roleRepository.save(new Role(roleName.name())));
			}
			System.out.println("✅ Đã kiểm tra/khởi tạo các vai trò cần thiết trong CSDL.");
		};
	}
	
}
