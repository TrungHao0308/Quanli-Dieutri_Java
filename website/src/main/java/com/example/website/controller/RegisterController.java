package com.example.website.controller;

import com.example.website.dto.RegisterRequest;
import com.example.website.model.User;
import com.example.website.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register-page")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register-page"; // Trả về register.html
    }

    @PostMapping("/register-page")
    public String register(@Valid RegisterRequest request, BindingResult result, Model model) {
        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            return "register-page";
        }

        // Kiểm tra trùng email/phone
        if (userRepository.existsByEmail(request.getEmail())) {
            result.rejectValue("email", "error.email", "Email đã được sử dụng.");
            return "register-page";
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            result.rejectValue("phone", "error.phone", "Số điện thoại đã được sử dụng.");
            return "register-page";
        }

        // Kiểm tra ngày sinh
        try {
            LocalDate parsedDate = LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE);
            if (parsedDate.isAfter(LocalDate.now())) {
                result.rejectValue("birthdate", "error.birthdate", "Ngày sinh không thể là tương lai.");
                return "register-page";
            }
        } catch (DateTimeParseException e) {
            result.rejectValue("birthdate", "error.birthdate", "Ngày sinh không hợp lệ.");
            return "register-page";
        }

        // Kiểm tra mật khẩu khớp
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Mật khẩu xác nhận không khớp.");
            return "register-page";
        }

        // Lưu user vào database
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setBirthdate(request.getBirthdate());
        user.setGender(request.getGender());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Thông báo thành công
        model.addAttribute("successMessage", "Đăng ký thành công, vui lòng đăng nhập.");
        return "redirect:/login-page";
    }
}