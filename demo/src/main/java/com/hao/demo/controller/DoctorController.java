
package com.hao.demo.controller;

import com.hao.demo.model.*;
import com.hao.demo.repository.*;
import com.hao.demo.service.CustomerService;
import com.hao.demo.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PhancongLichkhamRepository phancongLichkhamRepository;

    @Autowired
    private LichTrungGianRepository lichTrungGianRepository;

    @Autowired
    private BacsiChuyenmonRepository bacsiChuyenmonRepository;

    @Autowired
    private KetquaKhambenhRepository ketquaKhambenhRepository;

    @Autowired
    private DichvuRepository dichvuRepository;

    // ✅ Dùng bảng customer để lấy tài khoản đang đăng nhập
    private Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        String email = authentication.getName();
        return customerService.findByEmail(email).orElse(null);
    }

    // ✅ Trang chính của bác sĩ
    @GetMapping("")
    public String loadDoctorPage(Model model) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        model.addAttribute("customer", doctor);
        model.addAttribute("customerName", doctor.getFullName());
        model.addAttribute("activePage", "home");

        return "doctor/doctor";
    }

    // ✅ Dashboard: danh sách lịch khám theo ngày
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
                                Model model) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        List<PhancongLichkham> lichKhams = (ngay != null)
                ? phancongLichkhamRepository.findByEmailBacSiAndNgayKhamOrderByLichKhamAsc(doctor.getEmail(), ngay)
                : phancongLichkhamRepository.findByEmailBacSiOrderByNgayKhamAscLichKhamAsc(doctor.getEmail());

        model.addAttribute("lichKhams", lichKhams);
        model.addAttribute("customer", doctor);
        model.addAttribute("activePage", "dashboard");

        return "doctor/doctor";
    }

    // ✅ Tạo lịch điều trị trung gian
    @GetMapping("/tao-lichtrunggian")
    public String formTaoLichTrungGian(Model model, Principal principal) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        List<String> emailList = ketquaKhambenhRepository.findByEmailBacSiIgnoreCase(doctor.getEmail())
                .stream().map(KetquaKhambenh::getEmailBenhNhan).distinct().toList();

        List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBacSi(doctor.getEmail());

        model.addAttribute("emailList", emailList);
        model.addAttribute("lichKhams", lichKhams);
        model.addAttribute("role", "ROLE_DOCTOR");
        model.addAttribute("customerName", doctor.getFullName());

        return "chung/lichtrunggian";
    }

    // ✅ Xóa lịch trung gian
    @PostMapping("/lichtrunggian/xoa/{id}")
    public String xoaLichTrungGian(@PathVariable Long id, Principal principal) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        LichTrungGian lich = lichTrungGianRepository.findById(id).orElse(null);
        if (lich != null && doctor.getEmail().equals(lich.getEmailBacSi())) {
            lichTrungGianRepository.deleteById(id);
        }

        return "redirect:/doctor/lichtrinh";
    }

    // ✅ Tự động điền thông tin khám gần nhất
    @GetMapping("/api/lichkham/{email}")
    @ResponseBody
    public Map<String, String> getLichKhamTheoEmail(@PathVariable String email) {
        Map<String, String> result = new HashMap<>();
        KetquaKhambenh lich = ketquaKhambenhRepository
                .findByEmailBenhNhanOrderByNgayKhamDesc(email).stream().findFirst().orElse(null);

        if (lich != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            result.put("tenBenhNhan", lich.getTenBenhNhan());
            result.put("tenDichVu", lich.getDichVu());
            result.put("ngayKham", lich.getNgayKham() != null ? lich.getNgayKham().format(formatter) : "");
            result.put("chiTiet", lich.getChiTiet());
        }

        return result;
    }

    // ✅ Danh sách bệnh nhân theo email bác sĩ
    @GetMapping("/patients")
    public String hienThiDanhSachBenhNhan(@RequestParam(value = "keyword", required = false) String keyword,
                                          Model model) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        List<PhancongLichkham> danhSachLichKham = phancongLichkhamRepository.findByEmailBacSi(doctor.getEmail());

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            danhSachLichKham = danhSachLichKham.stream()
                    .filter(p -> p.getTenBenhNhan().toLowerCase().contains(lowerKeyword)
                            || p.getEmailBenhNhan().toLowerCase().contains(lowerKeyword))
                    .toList();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("customer", doctor);
        model.addAttribute("patients", danhSachLichKham);
        model.addAttribute("activePage", "patients");

        return "doctor/doctor";
    }

    // ✅ Trang nhập kết quả khám
    @GetMapping("/examination")
    public String showExamination(Model model) {
        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        List<String> emailBenhNhans = phancongLichkhamRepository.findDistinctEmailBenhNhanByEmailBacSi(doctor.getEmail());
        List<Dichvu> dichvuList = dichvuRepository.findAll();

        model.addAttribute("emailBenhNhans", emailBenhNhans);
        model.addAttribute("dichvuList", dichvuList);
        model.addAttribute("customer", doctor);
        model.addAttribute("activePage", "examination");

        return "doctor/doctor";
    }

    // ✅ Lưu kết quả khám
    @PostMapping("/examination/submit")
    public String luuKetQuaKham(@RequestParam("patientEmail") String emailBenhNhan,
                                @RequestParam("patientName") String tenBenhNhan,
                                @RequestParam("examDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKham,
                                @RequestParam("examType") String dichVu,
                                @RequestParam("examNotes") String chiTiet,
                                @RequestParam("tenChong") String tenChong,
                                @RequestParam("ketQuaChong") String ketQuaChong,
                                @RequestParam("chiTietChong") String chiTietChong,
                                @RequestParam("tenVo") String tenVo,
                                @RequestParam("ketQuaVo") String ketQuaVo,
                                @RequestParam("chiTietVo") String chiTietVo,
                                Model model) {

        Customer doctor = getLoggedInCustomer();
        if (doctor == null) return "redirect:/auth/login";

        KetquaKhambenh ketqua = new KetquaKhambenh();
        ketqua.setEmailBenhNhan(emailBenhNhan);
        ketqua.setTenBenhNhan(tenBenhNhan);
        ketqua.setNgayKham(ngayKham);
        ketqua.setDichVu(dichVu);
        ketqua.setChiTiet(chiTiet);
        ketqua.setEmailBacSi(doctor.getEmail());
        ketqua.setTenBacSi(doctor.getFullName());

        ketqua.setTenChong(tenChong);
        ketqua.setKetQuaChong(ketQuaChong);
        ketqua.setChiTietChong(chiTietChong);
        ketqua.setTenVo(tenVo);
        ketqua.setKetQuaVo(ketQuaVo);
        ketqua.setChiTietVo(chiTietVo);

        ketquaKhambenhRepository.save(ketqua);

        return "redirect:/doctor/examination";
    }
}
