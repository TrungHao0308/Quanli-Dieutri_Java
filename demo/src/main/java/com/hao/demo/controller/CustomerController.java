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
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomerPage(Model model) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());

        return "customer/customer";
    }

//  @GetMapping("/thongbao")
//  public String viewNotifications(Model model) {
//     return loadCustomerPage(model, "customer/thongbao");
//  }

//  @GetMapping("/dangkidichvu")
// public String viewServiceRegistration(Model model) {
//      return loadCustomerPage(model, "customer/dangkidichvu");
//  }

//     @GetMapping("/lichtrinhdieutri")
//     public String viewTreatmentSchedule(Model model) {
//         return loadCustomerPage(model, "customer/lichtrinhdieutri");
//     }

//     @GetMapping("/ketquadieutri")
//     public String viewTreatmentResults(Model model) {
//         return loadCustomerPage(model, "customer/ketquadieutri");
//     }

//     @GetMapping("/lichsodondat")
//     public String viewOrderHistory(Model model) {
//         return loadCustomerPage(model, "customer/lichsodondat");
//     }

//     @GetMapping("/danhgia")
//     public String viewReviews(Model model) {
//         return loadCustomerPage(model, "customer/danhgia");
//     }

//     @GetMapping("/hosocanhan")
//     public String viewProfile(Model model) {
//         return loadCustomerPage(model, "customer/hosocanhan");
//     }

     // Helper methods

    private Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName();
        return customerService.findByEmail(email).orElse(null);
    }

    private String loadCustomerPage(Model model, String viewName) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());

        return viewName;
    }
 }
