package com.hao.demo.controller;

import com.hao.demo.model.Customer;
import com.hao.demo.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManageController {

    private final CustomerService customerService;

    public ManageController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/baocao")
    public String showBaoCao(Model model) {
        return loadManagePage(model, "manager/baocao");
    }

    @GetMapping("/quanlybacsi")
    public String showBacSi(Model model) {
        return loadManagePage(model, "manager/quanlybacsi");
    }

    @GetMapping("/quanlydanhgia")
    public String showDanhGia(Model model) {
        return loadManagePage(model, "manager/quanlydanhgia");
    }

    @GetMapping("/quanlydichvu")
    public String showDichVu(Model model) {
        return loadManagePage(model, "manager/quanlydichvu");
    }

    // === Helper methods giống như trong CustomerController ===

    private Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName();
        return customerService.findByEmail(email).orElse(null);
    }

    private String loadManagePage(Model model, String viewName) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());

        return viewName;
    }
}