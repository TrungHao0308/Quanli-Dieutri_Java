package com.example.website;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class Register{

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^(0|\\+84)(3|5|7|8|9)\\d{8}$");
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "birthdate", required = false) String birthdate,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "terms", required = false) String terms,
            HttpSession session,
            Model model) {

        String errorMessage = null;

        if (firstName == null || firstName.trim().isEmpty()) {
            errorMessage = "Họ không được để trống.";
        } else if (lastName == null || lastName.trim().isEmpty()) {
            errorMessage = "Tên không được để trống.";
        } else if (!isValidEmail(email)) {
            errorMessage = "Email không hợp lệ.";
        } else if (!isValidPhone(phone)) {
            errorMessage = "Số điện thoại không hợp lệ.";
        } else if (birthdate == null || birthdate.isEmpty()) {
            errorMessage = "Ngày sinh không được để trống.";
        } else if (gender == null || gender.isEmpty()) {
            errorMessage = "Giới tính không được để trống.";
        } else if (password == null || password.isEmpty()) {
            errorMessage = "Mật khẩu không được để trống.";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "Mật khẩu xác nhận không khớp.";
        } else if (terms == null) {
            errorMessage = "Bạn phải đồng ý với Điều khoản sử dụng.";
        }

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            // Giữ lại dữ liệu form nếu muốn (optional)
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            model.addAttribute("birthdate", birthdate);
            model.addAttribute("gender", gender);
            return "register";  // View register.html hoặc register.jsp
        }

        // TODO: Thêm logic lưu user vào database ở đây

        // Lưu email user vào session sau khi đăng ký thành công
        session.setAttribute("userEmail", email);

        System.out.println("Đăng ký thành công:");
        System.out.println("Họ: " + firstName);
        System.out.println("Tên: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Gender: " + gender);

        // Sau khi đăng ký thành công, chuyển hướng đến trang login
        return "redirect:/loginpage";
    }
}
