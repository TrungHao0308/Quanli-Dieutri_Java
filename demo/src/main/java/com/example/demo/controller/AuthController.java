package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if ("testuser".equals(loginRequest.getUsername()) && "testpassword".equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Đăng nhập thành công!");
        } else {
            return ResponseEntity.status(401).body("Tên người dùng hoặc mật khẩu không đúng!");
        }
    }
}