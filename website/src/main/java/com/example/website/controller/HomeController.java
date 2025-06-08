package com.example.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/trang-chu-update")
    public String trangChu() {
        return "trang-chu-update"; // tên file html trong resources/templates/
    }
}
