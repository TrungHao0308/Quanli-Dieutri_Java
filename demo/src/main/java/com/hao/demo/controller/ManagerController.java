package com.hao.demo.controller;
import com.hao.demo.service.BacsiChuyenmonService;
import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.BacsiChuyenmonRepository;
import com.hao.demo.repository.CustomerRepository;
import com.hao.demo.repository.DangkiDichvuRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.CustomerService;
import com.hao.demo.model.Dichvu;
import com.hao.demo.repository.DichvuRepository;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
private CustomerRepository customerRepository;

@Autowired
private BacsiChuyenmonRepository bacsiChuyenmonRepository;

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


    

@Autowired
private DichvuRepository dichvuRepository;

@GetMapping("/quanlybacsi")
public String showBacSi(Model model) {
        List<Customer> doctors = customerRepository.findByRoleName("ROLE_DOCTOR");
    List<BacsiChuyenmon> danhSachDaCo = bacsiChuyenmonRepository.findAll();
List<Dichvu> dichVus = dichvuRepository.findAll();
    Map<Long, BacsiChuyenmon> daCapNhatMap = danhSachDaCo.stream()
        .filter(b -> b.getCustomer() != null)
        .collect(Collectors.toMap(
            b -> b.getCustomer().getId(),
            b -> b
        ));

    model.addAttribute("doctors", doctors);
    model.addAttribute("doctorUpdatedMap", daCapNhatMap); // rất quan trọng
model.addAttribute("dichVus", dichVus);
    return loadManagerPage(model, "manager/quanlybacsi");
}



    @GetMapping("/quanlydanhgia")
    public String showDanhGia(Model model) {
        return loadManagerPage(model, "manager/quanlydanhgia");
    }

   @Autowired
private DichvuRepository dichVuRepository;

@GetMapping("/quanlydichvu")
public String showDichvu(Model model) {
    model.addAttribute("dichVu", new Dichvu());
    model.addAttribute("dichVuList", dichVuRepository.findAll());
    return loadManagerPage(model, "manager/quanlydichvu");
}

@PostMapping("/quanlydichvu/add")
public String themDichvu(@ModelAttribute("dichVu") Dichvu dichVu) {
    dichVuRepository.save(dichVu);
    return "redirect:/manager/quanlydichvu";
}

// Xóa dịch vụ
@PostMapping("/quanlydichvu/delete/{id}")
public String xoaDichVu(@PathVariable Long id) {
    dichVuRepository.deleteById(id);
    return "redirect:/manager/quanlydichvu";
}

// Hiển thị form sửa dịch vụ
@GetMapping("/quanlydichvu/edit/{id}")
public String showFormSua(@PathVariable Long id, Model model) {
    Dichvu dichvu = dichVuRepository.findById(id).orElse(null);
    if (dichvu == null) return "redirect:/manager/quanlydichvu";
    model.addAttribute("dichVu", dichvu);
    model.addAttribute("dichVuList", dichVuRepository.findAll());
    return loadManagerPage(model, "manager/quanlydichvu");
}

// Cập nhật dịch vụ sau khi sửa
@PostMapping("/quanlydichvu/update")
public String capNhatDichVu(@ModelAttribute("dichVu") Dichvu dichVu) {
    dichVuRepository.save(dichVu); // save sẽ update nếu có id
    return "redirect:/manager/quanlydichvu";
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

    // Khớp chính xác với key ở Thymeleaf (toLowerCase + trim)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Map<String, PhancongLichkham> lichPhanCongMap = new HashMap<>();
    for (PhancongLichkham lich : phancongLichkhamRepository.findAll()) {
        String key = lich.getEmailBenhNhan().trim().toLowerCase() + "_" + lich.getNgayKham().format(formatter);
        lichPhanCongMap.put(key, lich);
    }

    model.addAttribute("lichPhanCongMap", lichPhanCongMap);
    return "manager/quanlylichkham";
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

public String luuPhanCong(
    @RequestParam String emailBenhNhan,
    @RequestParam String tenBenhNhan,
    @RequestParam String ngayKham,
    @RequestParam String dichVu,
    @RequestParam String chiTiet,
    @RequestParam String emailBacSi,
    @RequestParam String tenBacSi,
    @RequestParam String lichKham,
    RedirectAttributes redirectAttributes
) {
    
    BacsiChuyenmon bacsi = bacsiChuyenmonRepository.findByEmail(emailBacSi);
    if (bacsi == null) {
        redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin ca làm của bác sĩ.");
        return "redirect:/manager/quanlylichkham";
    }

    // Kiểm tra giờ hợp lệ
    String caLam = bacsi.getCaLam();
    String gioPhanCong = lichKham.split("-")[0].trim();

    try {
        String[] parts = gioPhanCong.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        int minute = Integer.parseInt(parts[1].trim());
        int totalMinutes = hour * 60 + minute;

        boolean hopLe = false;
        if ("sáng".equalsIgnoreCase(caLam)) {
            hopLe = (totalMinutes >= 420 && totalMinutes <= 660); // 07:00–11:00
        } else if ("chiều".equalsIgnoreCase(caLam)) {
            hopLe = (totalMinutes >= 780 && totalMinutes <= 1020); // 13:00–17:00
        }

        if (!hopLe) {
            redirectAttributes.addFlashAttribute("error", "Bác sĩ có ca làm " + caLam + ", vui lòng phân công giờ phù hợp.");
            return "redirect:/manager/quanlylichkham";
        }
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Định dạng giờ không hợp lệ. VD: 09:00 - Phòng 3");
        return "redirect:/manager/quanlylichkham";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate ngayKhamDate = LocalDate.parse(ngayKham, formatter);
    // Lưu lịch khám
    PhancongLichkham pc = new PhancongLichkham();
    pc.setEmailBenhNhan(emailBenhNhan);
    pc.setTenBenhNhan(tenBenhNhan);
    pc.setNgayKham(ngayKhamDate);
    pc.setDichVu(dichVu);
    pc.setChiTiet(chiTiet);
    pc.setEmailBacSi(emailBacSi);
    pc.setTenBacSi(tenBacSi);
    pc.setLichKham(lichKham);

    phancongLichkhamRepository.save(pc);
    redirectAttributes.addFlashAttribute("success", "Phân công lịch khám thành công!");
    return "redirect:/manager/quanlylichkham";
}



@PostMapping("/themBacSi")
@ResponseBody
public ResponseEntity<String> themBacSi(@RequestBody Map<String, String> data) {
    Long customerId = Long.parseLong(data.get("id"));
    String chuyenMon = data.get("specialty");
    String caLam = data.get("shift");

    Optional<Customer> optCustomer = customerRepository.findById(customerId);
    if (optCustomer.isEmpty()) return ResponseEntity.badRequest().body("Không tìm thấy bác sĩ");

    Customer customer = optCustomer.get();

    BacsiChuyenmon bacsi = new BacsiChuyenmon();
    bacsi.setCustomer(customer); // Gắn liên kết
    bacsi.setEmail(customer.getEmail());
    bacsi.setTen(customer.getFullName());
    bacsi.setChuyenMon(chuyenMon);
    bacsi.setCaLam(caLam);

    bacsiChuyenmonRepository.save(bacsi);

    return ResponseEntity.ok("Đã thêm bác sĩ");

}
@Autowired
private BacsiChuyenmonService bacsiChuyenmonService;

@DeleteMapping("/huyBacSi/{id}")
@ResponseBody
public ResponseEntity<String> huyBacSi(@PathVariable("id") Long customerId) {
    bacsiChuyenmonService.deleteByCustomerId(customerId);
    return ResponseEntity.ok("Đã hủy bác sĩ");
}
@PostMapping("/huylich/{id}")
public String huyLich(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    phancongLichkhamRepository.deleteById(id);
    redirectAttributes.addFlashAttribute("success", "Đã hủy phân công lịch khám.");
    return "redirect:/manager/quanlylichkham";
}

}