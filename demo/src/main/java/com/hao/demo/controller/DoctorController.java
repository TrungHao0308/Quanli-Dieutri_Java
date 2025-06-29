package com.hao.demo.controller;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.LichTrungGian;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.BacsiChuyenmonRepository;
import com.hao.demo.repository.LichTrungGianRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.CustomerService;
import com.hao.demo.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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




    // @GetMapping("/schedule")
    // public String showSchedule(Model model) {
    //     return loadDoctorPage(model, "doctor/doctor", "schedule");
    // }

    @Autowired
    private LichTrungGianRepository lichTrungGianRepository;
        @Autowired
        private DoctorService doctorService;
    @GetMapping("/tao-lichtrunggian")
    public String formTaoLichTrungGian(Model model, Principal principal) {
        BacsiChuyenmon doctor = doctorService.findByEmail(principal.getName()).orElse(null);
        if (doctor == null) return "redirect:/auth/login";

        // Lấy các phân công khám để đổ vào select bệnh nhân
        List<PhancongLichkham> all = phancongLichkhamRepository.findAll();
        Map<String, PhancongLichkham> uniqueEmails = new LinkedHashMap<>();
        for (PhancongLichkham lich : all) {
            uniqueEmails.putIfAbsent(lich.getEmailBenhNhan(), lich);
        }

        model.addAttribute("phanCongList", new ArrayList<>(uniqueEmails.values()));

        // Lấy danh sách lịch đã tạo của bác sĩ này
        List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBacSi(doctor.getEmail());
        model.addAttribute("lichKhams", lichKhams);

        // Truyền thêm role để view hiện form
        model.addAttribute("role", "ROLE_DOCTOR");
        model.addAttribute("customerName", doctor.getFullName());

        return "chung/lichtrunggian";
    }




@GetMapping("/doctor/api/lichkham/{email}")
@ResponseBody
public Map<String, String> getLichKhamTheoEmail(@PathVariable String email) {
    Map<String, String> result = new HashMap<>();
    PhancongLichkham lich = phancongLichkhamRepository.findFirstByEmailBenhNhan(email);

    if (lich != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        result.put("tenBenhNhan", lich.getTenBenhNhan());
        result.put("tenDichVu", lich.getDichVu());
        result.put("ngayKham", lich.getNgayKham() != null ? lich.getNgayKham().format(formatter) : "");
        result.put("chiTiet", lich.getChiTiet());
    }

    return result;
}
@PostMapping("/lichtrunggian/xoa/{id}")
public String xoaLichTrungGian(@PathVariable Long id, Principal principal) {
    BacsiChuyenmon doctor = doctorService.findByEmail(principal.getName()).orElse(null);
    if (doctor == null) return "redirect:/auth/login";

    LichTrungGian lich = lichTrungGianRepository.findById(id).orElse(null);
    if (lich != null && doctor.getEmail().equals(lich.getEmailBacSi())) {
        lichTrungGianRepository.deleteById(id);
    }

    return "redirect:/doctor/lichtrinh";
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
