package com.hao.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/trangchu"})
    public String showHomePage() {
        return "trangchu";
    }
}