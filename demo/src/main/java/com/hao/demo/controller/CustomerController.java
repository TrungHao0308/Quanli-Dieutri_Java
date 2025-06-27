package com.hao.demo.controller;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.DangkiDichvuRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.BacsiChuyenmonService;
import com.hao.demo.service.CustomerService;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomerPage(Model model) {
        return loadCustomerPage(model, "customer/customer");
    }

@GetMapping("/thongbao")
public String showThongBao(Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    List<PhancongLichkham> lichKhams = phancongLichkhamRepository.findByEmailBenhNhan(customer.getEmail());
    model.addAttribute("customer", customer);
    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("lichKhams", lichKhams);

    return "customer/thongbao";
}


@GetMapping("/dangkidichvu")
public String showDangKiDichVu(Model model) {
    return loadCustomerPage(model, "customer/dangkidichvu");
}

@GetMapping("/lichtrinhdieutri")
public String showLichTrinhDieuTri(Model model) {
    return loadCustomerPage(model, "customer/lichtrinhdieutri");
}

@GetMapping("/ketquadieutri")
public String showKetQuaDieuTri(Model model) {
    return loadCustomerPage(model, "customer/ketquadieutri");
}

@GetMapping("/lichsudondat")
public String showLichSuDonDat(Model model) {
    return loadCustomerPage(model, "customer/lichsudondat");
}

@GetMapping("/danhgia")
public String showDanhGia(Model model) {
    return loadCustomerPage(model, "customer/danhgia");
}

@GetMapping("/hosocanhan")
public String showHoSoCaNhan(Model model) {
    return loadCustomerPage(model, "customer/hosocanhan");
}


@Autowired
private BacsiChuyenmonService bacsiChuyenmonService;

@GetMapping("/api/bacsi")
@ResponseBody
public List<BacsiChuyenmon> getDoctorsByChuyenMon(@RequestParam("chuyenMon") String chuyenMon) {
    return bacsiChuyenmonService.getDoctorsBySpeciality(chuyenMon);
}


@Autowired
private DangkiDichvuRepository dangkiDichvuRepository;

@PostMapping("/dangkidichvu")
public String xuLyDangKiDichVu(
        @RequestParam("dichVu") String dichVu,
        @RequestParam("tenBacSi") String tenBacSi,
        @RequestParam("emailBacSi") String emailBacSi,
        @RequestParam("ngayKham") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKham,
        @RequestParam("chiTiet") String chiTiet,
        Model model) {

    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    DangkiDichvu dk = new DangkiDichvu();
    dk.setDichVu(dichVu);
    dk.setTenBacSi(tenBacSi);
    dk.setEmailBacSi(emailBacSi);
    dk.setNgayKham(ngayKham);
    dk.setChiTiet(chiTiet);
    dk.setEmailBenhNhan(customer.getEmail());
    dk.setTenBenhNhan(customer.getFullName());

    dangkiDichvuRepository.save(dk);

    return "redirect:/customer/lichtrinhdieutri"; // hoặc thông báo thành công
}

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
    @Autowired
private PhancongLichkhamRepository phancongLichkhamRepository;

    
}