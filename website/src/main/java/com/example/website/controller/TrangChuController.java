package com.example.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {

    @GetMapping("/trang-chu-update")
    public String showTrangChuUpdate() {
        return "trang-chu-update"; // không cần .html
    }
}
