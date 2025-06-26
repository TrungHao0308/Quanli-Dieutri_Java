package com.hao.demo.controller;

import com.hao.demo.model.Customer;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.DangkiDichvuRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.CustomerService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    
    private final CustomerService customerService;

    public ManagerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("")
public String indexPage(Model model) {
    return loadManagerPage(model, "manager/baocao");
}


    @GetMapping("/baocao")
    public String showBaoCao(Model model) {
        return loadManagerPage(model, "manager/baocao");
    }

    @GetMapping("/quanlybacsi")
    public String showBacSi(Model model) {
        return loadManagerPage(model, "manager/quanlybacsi");
    }

    @GetMapping("/quanlydanhgia")
    public String showDanhGia(Model model) {
        return loadManagerPage(model, "manager/quanlydanhgia");
    }

    @GetMapping("/quanlydichvu")
    public String showDichVu(Model model) {
        return loadManagerPage(model, "manager/quanlydichvu");
    }
    // @GetMapping("/quanlylichkham")
    // public String showLichkham(Model model) {
    //     return loadManagerPage(model, "manager/quanlylichkham");
    // }

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

    private String loadManagerPage(Model model, String viewName) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());

        return viewName;
    }
    @Autowired
private DangkiDichvuRepository dangkiDichvuRepository;

@Autowired
private PhancongLichkhamRepository phancongLichkhamRepository;

@GetMapping("/quanlylichkham")
public String hienThiDangKy(Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    List<DangkiDichvu> danhSach = dangkiDichvuRepository.findAll();
    model.addAttribute("customer", customer);
    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("danhSachDangKy", danhSach);

    return "manager/quanlylichkham"; // Giao diện hiển thị danh sách
}

@GetMapping("/phancong")
public String hienThiFormPhanCong(@RequestParam("id") Long id, Model model) {
    Customer manager = getLoggedInCustomer();
    if (manager == null) return "redirect:/auth/login";

    DangkiDichvu dk = dangkiDichvuRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đăng ký"));

    PhancongLichkham pc = new PhancongLichkham();
    pc.setEmailBenhNhan(dk.getEmailBenhNhan());
    pc.setTenBenhNhan(dk.getTenBenhNhan());
    pc.setNgayKham(dk.getNgayKham());
    pc.setDichVu(dk.getDichVu());
    pc.setChiTiet(dk.getChiTiet());
    pc.setEmailBacSi(dk.getEmailBacSi());
    pc.setTenBacSi(dk.getTenBacSi());

    model.addAttribute("customer", manager);
    model.addAttribute("customerName", manager.getFullName());
    model.addAttribute("lich", pc);

    return "manager/phanconglichkham"; // Giao diện điền lịch khám
}

@PostMapping("/phancong")
public String luuPhanCong(@ModelAttribute("lich") PhancongLichkham pc, Model model) {
    phancongLichkhamRepository.save(pc);
    return "redirect:/manager/quanlylichkham";
}

}