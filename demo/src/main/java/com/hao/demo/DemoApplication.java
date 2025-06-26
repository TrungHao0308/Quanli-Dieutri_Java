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

	// @Bean
	// public CommandLineRunner initDangkiDichvu(DangkiDichvuRepository repo) {
	// 	return args -> {
	// 		DangkiDichvu dk = new DangkiDichvu();
	// 		dk.setEmailBenhNhan("abc@gmail.com");
	// 		dk.setTenBenhNhan("Nguyễn Văn A");
	// 		dk.setNgayKham(LocalDate.now());
	// 		dk.setDichVu("Khám tổng quát");
	// 		dk.setChiTiet("Phòng 101 lúc 9h");
	// 		dk.setEmailBacSi("doctor@example.com");
	// 		dk.setTenBacSi("Bác sĩ B");

	// 		repo.save(dk); // Hibernate sẽ tự tạo bảng nếu chưa có
	// 		System.out.println("✅ Dữ liệu mẫu đã được thêm vào bảng dang_ki_dich_vu.");
	// 	};
	// }
	
}
