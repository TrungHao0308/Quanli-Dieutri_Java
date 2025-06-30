package com.hao.demo.service;
import java.util.List;
import com.hao.demo.dto.CustomerLoginDto;
import com.hao.demo.dto.CustomerRegistrationDto;
import com.hao.demo.model.Customer;
import com.hao.demo.model.RoleName;
import com.hao.demo.repository.CustomerRepository;
import com.hao.demo.repository.RoleRepository;
import com.hao.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
public Customer registerCustomer(CustomerRegistrationDto registrationDto) throws Exception {
    if (customerRepository.existsByEmail(registrationDto.getEmail())) {
        throw new Exception("Email đã được sử dụng!");
    }

    if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
        throw new Exception("Mật khẩu xác nhận không khớp!");
    }

    Customer customer = new Customer();
    customer.setFullName(registrationDto.getFullName());
    customer.setEmail(registrationDto.getEmail());
    customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
    // customer.setPhone(registrationDto.getPhone());
    // customer.setAddress(registrationDto.getAddress());
    // customer.setDateOfBirth(registrationDto.getDateOfBirth());
    // customer.setGender(registrationDto.getGender());
    customer.setIsActive(true); 

    // ✅ Gán role CUSTOMER mặc định
    Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER.name())
        .orElseThrow(() -> new Exception("Không tìm thấy vai trò CUSTOMER"));

    customer.getRoles().add(customerRole);

    return customerRepository.save(customer);
}

    @Override
public Optional<Customer> findByEmail(String email) {
    return customerRepository.findByEmail(email);
}

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    @Override
public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
}

@Override
public Optional<Customer> findById(Long id) {
    return customerRepository.findById(id);
}


@Override
public Customer addCustomer(Customer customer) {
    // Mã hóa mật khẩu nếu chưa mã hóa
    if (!customer.getPassword().startsWith("$2a$")) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    }

    // ⚠️ Chuyển đổi các chuỗi role ("doctor", "manager") thành entity Role
    Set<Role> convertedRoles = customer.getRoles().stream()
        .map(r -> {
            String raw = r.getName();
            String roleName = raw.startsWith("ROLE_") ? raw : "ROLE_" + raw.toUpperCase();
            return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy vai trò: " + roleName));
        })
        .collect(Collectors.toSet());

    customer.setRoles(convertedRoles);
    customer.setIsActive(true);
        System.out.println(">>> Đang lưu customer: " + customer.getEmail());
System.out.println(">>> Role được gán: " + customer.getRoles().stream().map(Role::getName).toList());

    Customer saved = customerRepository.save(customer);
    System.out.println("✅ Đã lưu tài khoản: " + saved.getId() + " - " + saved.getEmail());
    return saved;
}


@Override
public void deleteCustomer(Long id) {
    customerRepository.findById(id).ifPresent(customer -> {
        customer.getRoles().clear(); // xóa liên kết trong bảng customer_roles
        customerRepository.save(customer); 
        customerRepository.deleteById(id);
    });
}

@Override
public Optional<Customer> loginCustomer(CustomerLoginDto loginDto) {
    Optional<Customer> customer = customerRepository.findByEmail(loginDto.getEmail());
    if (customer.isPresent() && validatePassword(loginDto.getPassword(), customer.get().getPassword())) {
        return customer;
    }
    return Optional.empty();
}
@Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
// @Override
// public Customer addCustomer(Customer customer) {
//     // Mã hóa mật khẩu nếu chưa mã hóa
//     if (!customer.getPassword().startsWith("$2a$")) {
//         customer.setPassword(passwordEncoder.encode(customer.getPassword()));
//     }

//     // ✅ CHUẨN HÓA tên role (thêm ROLE_ nếu thiếu)
//     Set<Role> roles = customer.getRoles().stream()
//         .map(role -> {
//             String rawName = role.getName();
//             String finalName = rawName.startsWith("ROLE_") ? rawName : "ROLE_" + rawName.toUpperCase();
//             return roleRepository.findByName(finalName)
//                 .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò: " + finalName));
//         })
//         .collect(Collectors.toSet());

//     customer.setRoles(roles);
//     customer.setIsActive(true);
//     return customerRepository.save(customer);
// }