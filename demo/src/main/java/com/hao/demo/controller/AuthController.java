package com.hao.demo.controller;

import com.hao.demo.dto.CustomerLoginDto;
import com.hao.demo.dto.CustomerRegistrationDto;
import com.hao.demo.model.Customer;
import com.hao.demo.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private CustomerService customerService;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new CustomerLoginDto());
        return "login";
    }

    // Lưu ý: Để sử dụng formLogin() của Spring Security, không cần xử lý POST /auth/login thủ công,
    // nên phương thức xử lý POST login sẽ không có ở đây.

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new CustomerRegistrationDto());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("registrationDto") CustomerRegistrationDto registrationDto,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            Customer customer = customerService.registerCustomer(registrationDto);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đăng ký thành công! Vui lòng đăng nhập.");
            
            return "redirect:/auth/login";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
        return "redirect:/auth/login";
    }

    // Kiểm tra email đã tồn tại (AJAX)
    @PostMapping("/check-email")
    @ResponseBody
    public boolean checkEmailExists(@RequestParam String email) {
        return customerService.existsByEmail(email);
    }
}

