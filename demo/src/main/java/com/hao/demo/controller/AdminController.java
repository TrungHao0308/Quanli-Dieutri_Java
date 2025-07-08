

package com.hao.demo.controller;

import com.hao.demo.dto.AdminCustomerDto;
import com.hao.demo.model.Customer;
import com.hao.demo.model.Role;
import com.hao.demo.repository.RoleRepository;
import com.hao.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String showAdminPage(Model model) {
        return loadAdminPage(model, "admin/admin");
    }

    
    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public List<Customer> getCustomers(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) String role) {
        List<Customer> customers = customerService.getAllCustomers();

        if (search != null && !search.isEmpty()) {
            String searchLower = search.toLowerCase();
            customers = customers.stream()
                .filter(customer -> customer.getFullName().toLowerCase().contains(searchLower)
                    || customer.getEmail().toLowerCase().contains(searchLower))
                .toList();
        }

        if (role != null && !role.isEmpty()) {
            String roleName = "ROLE_" + role.toUpperCase();
            customers = customers.stream()
                .filter(customer -> customer.getRoles().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase(roleName)))
                .toList();
        }

        return customers;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

@PostMapping("/save")
@ResponseBody
public ResponseEntity<String> saveCustomer(@Valid @RequestBody AdminCustomerDto dto) {
    System.out.println("🎯 Vào saveCustomer()");
    try {    
        System.out.println("📥 Nhận dữ liệu từ frontend: " + dto);
        // Kiểm tra role hợp lệ
        String roleName = "ROLE_" + dto.getRole().toUpperCase();
        if (!roleName.equals("ROLE_DOCTOR") && !roleName.equals("ROLE_MANAGER")) {
            System.out.println("❌ Vai trò không hợp lệ: " + roleName);
            return ResponseEntity.badRequest().body("Vai trò không hợp lệ! Chỉ được chọn 'doctor' hoặc 'manager'.");
        }

        // Kiểm tra email tồn tại
        if (customerService.existsByEmail(dto.getEmail()) && 
            (dto.getId() == null || 
             !customerService.findById(dto.getId()).map(c -> c.getEmail().equals(dto.getEmail())).orElse(false))) {
            System.out.println("❌ Email đã được sử dụng: " + dto.getEmail());
            return ResponseEntity.badRequest().body("Email đã được sử dụng!");
        }

        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setIsActive(true);

        // Mã hóa mật khẩu
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            customer.setPassword(customerService.getPasswordEncoder().encode(dto.getPassword()));
            System.out.println("🔒 Mật khẩu đã được mã hóa cho: " + dto.getEmail());
        } else if (dto.getId() == null) {
            System.out.println("❌ Mật khẩu trống khi tạo mới");
            return ResponseEntity.badRequest().body("Mật khẩu không được để trống khi tạo mới!");
        }

        // Tìm Role
        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> {
                System.out.println("❌ Không tìm thấy role: " + roleName);
                return new RuntimeException("Không tìm thấy role: " + roleName);
            });
        customer.setRoles(Set.of(role));

        if (dto.getId() == null) {
            System.out.println("📝 Thêm mới khách hàng: " + customer.getEmail());
            customerService.addCustomer(customer);
        }

        System.out.println("✅ Đã lưu: " + customer.getEmail() + " với vai trò: " + role.getName());

        return ResponseEntity.ok("Success");
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("❌ Lỗi khi lưu tài khoản: " + e.getMessage());
        return ResponseEntity.status(500).body("Lỗi: " + e.getMessage());
    }
}

    @DeleteMapping("/users/{id}")
@ResponseBody
public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
    if (customerService.findById(id).isPresent()) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Đã xóa thành công");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
    }
}


    @GetMapping("/users")
    @ResponseBody
    public List<Customer> getAllUsers() {
        return customerService.getAllCustomers();
    }

    private Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName();
        return customerService.findByEmail(email).orElse(null);
    }

    private String loadAdminPage(Model model, String viewName) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());
        model.addAttribute("customers", customerService.getAllCustomers());

        return viewName;
    }
}