package com.example.website.controller;

import com.example.website.model.User;
import com.example.website.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login-page")
    public String showLoginPage(HttpSession session) {
        // Nếu đã đăng nhập, chuyển hướng đến home
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login-page"; // Trả về loginPage.html
    }

    @PostMapping("/login-page")
    public String login(@RequestParam("emailOrPhone") String emailOrPhone,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {
        User user = userRepository.findByEmailOrPhone(emailOrPhone, emailOrPhone);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Đăng nhập thành công
            session.setAttribute("user", user.getEmail());
            session.setMaxInactiveInterval(1800); // Session timeout 30 phút
            return "redirect:/trang-chu-update"; // Chuyển hướng đến trang chủ
        } else {
            // Đăng nhập thất bại
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng.");
            return "login-page";
        }
    }

    @GetMapping("/trang-chu-update")
    public String showHome(HttpSession session, Model model) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("user") == null) {
            return "redirect:/login-page";
        }
        model.addAttribute("userEmail", session.getAttribute("user"));
        return "trang-chu-update"; // Trả về home.html
    }
}