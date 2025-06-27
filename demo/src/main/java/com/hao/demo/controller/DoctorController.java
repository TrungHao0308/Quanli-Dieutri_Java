package com.hao.demo.controller;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.BacsiChuyenmonRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("")
    public String loadDoctorPage(Model model) {
        return loadDoctorPage(model, "doctor/doctor", "dashboard");
    }

@GetMapping("/dashboard")
public String showDashboard(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
        Model model) {

    BacsiChuyenmon bacsi = getLoggedInDoctor();
    if (bacsi == null) return "redirect:/auth/login";

    List<PhancongLichkham> lichKhams;

    if (ngay != null) {
        lichKhams = phancongLichkhamRepository.findByEmailBacSiAndNgayKhamOrderByLichKhamAsc(bacsi.getEmail(), ngay);
    } else {
        lichKhams = phancongLichkhamRepository.findByEmailBacSiOrderByNgayKhamAscLichKhamAsc(bacsi.getEmail());
    }

    model.addAttribute("lichKhams", lichKhams);
    model.addAttribute("customer", bacsi);
    model.addAttribute("activePage", "dashboard");

    return "doctor/doctor";
}




    @GetMapping("/schedule")
    public String showSchedule(Model model) {
        return loadDoctorPage(model, "doctor/doctor", "schedule");
    }

    // ✅ Sửa: Dùng đúng Repository và Model
    @Autowired
    private BacsiChuyenmonRepository bacsiChuyenmonRepository;

    @Autowired
    private PhancongLichkhamRepository phancongLichkhamRepository;

    @GetMapping("/patients")
    public String hienThiDanhSachBenhNhan(Model model) {
        BacsiChuyenmon bacsi = getLoggedInDoctor();
        if (bacsi == null) return "redirect:/auth/login";

        List<PhancongLichkham> danhSachLichKham = phancongLichkhamRepository.findByEmailBacSi(bacsi.getEmail());

        model.addAttribute("customer", bacsi);
        model.addAttribute("patients", danhSachLichKham);
        model.addAttribute("activePage", "patients");

        return "doctor/doctor";
    }

    // ✅ Sửa: method trả về BacsiChuyenmon
    private BacsiChuyenmon getLoggedInDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return bacsiChuyenmonRepository.findByEmail(email);
    }

    @GetMapping("/examination")
    public String showExamination(Model model) {
        return loadDoctorPage(model, "doctor/doctor", "examination");
    }

}
