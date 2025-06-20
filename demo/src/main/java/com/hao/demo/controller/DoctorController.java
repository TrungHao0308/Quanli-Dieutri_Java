
package com.hao.demo.controller;

import com.hao.demo.model.Customer;
import com.hao.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final CustomerService customerService;

    @Autowired
    public DoctorController(CustomerService customerService) {
        this.customerService = customerService;
    }


    private Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName();
        return customerService.findByEmail(email).orElse(null);
    }

    private String loadDoctorPage(Model model, String viewName, String activePage) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());
        model.addAttribute("activePage", activePage);

        return viewName;
    }
    @GetMapping("/doctor")
public String loadDoctorPage(Model model) {
    return loadDoctorPage(model, "doctor/doctor", "dashboard");
}
    // Trang tổng hợp
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Thêm thống kê nếu có
        model.addAttribute("todayPatients", "--");
        return loadDoctorPage(model, "doctor/doctor", "dashboard");
    }

    // Trang bệnh nhân
    @GetMapping("/patients")
    public String showPatients(Model model) {
        // model.addAttribute("patients", listPatientService.findAll());
        return loadDoctorPage(model, "doctor/doctor", "patients");
    }

    // Trang kết quả khám
    @GetMapping("/examination")
    public String showExamination(Model model) {
        return loadDoctorPage(model, "doctor/doctor", "examination");
    }
}
