package com.example.website;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginPage {

    private static final String SAMPLE_EMAIL = "admin@example.com";
    private static final String SAMPLE_PHONE = "0123456789";
    private static final String SAMPLE_PASSWORD = "123456";

    // Hiển thị trang login (GET)
    @GetMapping("/loginpage")
    public String showLoginPage() {
        return "loginPage"; // trả về LoginPage.html hoặc LoginPage.jsp
    }

    // Xử lý form login (POST)
    @PostMapping("/loginpage")
    public String login(
            @RequestParam("email") String emailOrPhone,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        boolean isValidUser =
            (emailOrPhone.equals(SAMPLE_EMAIL) || emailOrPhone.equals(SAMPLE_PHONE))
            && password.equals(SAMPLE_PASSWORD);

        if (isValidUser) {
            // Đăng nhập thành công, lưu user vào session
            session.setAttribute("user", emailOrPhone);
            return "Redirect:/Home";  // chuyển hướng đến trang home
        } else {
            // Đăng nhập thất bại, trả về trang login với thông báo lỗi
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng.");
            return "loginPage";
        }
    }

    // Trang home (GET)
    @GetMapping("/home")
    public String showHome() {
        return "home";  // trả về home.html hoặc home.jsp
    }
}
