package com.hao.demo.controller;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.model.Danhgia;
import com.hao.demo.model.KetquaKhambenh;
import com.hao.demo.model.LichTrungGian;
import com.hao.demo.dto.LichTrungGianDTO;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.DangkiDichvuRepository;
import com.hao.demo.repository.DanhgiaRepository;
import com.hao.demo.repository.DichvuRepository;
import com.hao.demo.repository.LichTrungGianRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.BacsiChuyenmonService;
import com.hao.demo.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Autowired
private DichvuRepository dichvuRepository;

@GetMapping("/dangkidichvu")
public String showDangKiDichVu(Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("dichVuList", dichvuRepository.findAll()); // truyền dịch vụ

    return "customer/dangkidichvu";
}

// @GetMapping("/lichtrinhdieutri")
// public String showLichTrinhDieuTri(Model model) {
//     return loadCustomerPage(model, "customer/lichtrinhdieutri");
// }

@Autowired
private LichTrungGianRepository lichTrungGianRepository;


   

    @GetMapping("/lichtrinhdieutri")
    public String lichCustomer(Model model) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBenhNhan(customer.getEmail());

        model.addAttribute("role", "ROLE_CUSTOMER");
        model.addAttribute("lichKhams", lichKhams);
        model.addAttribute("customerName", customer.getFullName());

        return "chung/lichtrunggian";
    }


@Autowired
private com.hao.demo.repository.KetquaKhambenhRepository ketquaKhambenhRepository;

@GetMapping("/ketquadieutri")
public String showKetQuaDieuTri(Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    List<KetquaKhambenh> treatmentResults = ketquaKhambenhRepository
        .findByEmailBenhNhanOrderByNgayKhamDesc(customer.getEmail());

    model.addAttribute("customer", customer);
    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("treatmentResults", treatmentResults);

    return "customer/ketquadieutri";
}




@GetMapping("/lichsudondat")
public String showLichSuDonDat(Model model) {
    return loadCustomerPage(model, "customer/lichsudondat");
}

@Autowired
private DanhgiaRepository danhgiaRepository;

@GetMapping("/danhgia")
public String showDanhGiaForm(Model model, Principal principal) {
    String email = principal.getName();
    List<LichTrungGian> lichList = lichTrungGianRepository.findByEmailBenhNhan(email);

    List<LichTrungGianDTO> dtoList = lichList.stream()
        .map(lich -> new LichTrungGianDTO(
            lich.getId(),
            lich.getTenDichVu(),
            lich.getTenBacSi(),
            lich.getNgayKham()
        ))
        .collect(Collectors.toList());

    model.addAttribute("lichDTOList", dtoList);
    return "customer/danhgia";
}

@PostMapping("/danhgia")
public String xuLyDanhGia(
        @RequestParam("lichId") Long lichId,
        @RequestParam("rating") int mucDoHaiLong,
        @RequestParam("comment") String nhanXet,
        Principal principal,
        RedirectAttributes redirectAttributes) {

    String emailBenhNhan = principal.getName();

    LichTrungGian lich = lichTrungGianRepository.findById(lichId).orElse(null);
    if (lich == null || !lich.getEmailBenhNhan().equals(emailBenhNhan)) {
        redirectAttributes.addFlashAttribute("errorMessage", "Lịch khám không hợp lệ.");
        return "redirect:/customer/danhgia";
    }

    Danhgia danhgia = new Danhgia();
    danhgia.setTenBacSi(lich.getTenBacSi());
    danhgia.setDichVu(lich.getTenDichVu());
    danhgia.setMucDoHaiLong(mucDoHaiLong);
    danhgia.setNhanXet(nhanXet);
    danhgia.setEmailBenhNhan(emailBenhNhan);
    danhgia.setDaXem(false);

    danhgiaRepository.save(danhgia);

    redirectAttributes.addFlashAttribute("successMessage", "Đánh giá đã được gửi thành công.");
    return "redirect:/customer/danhgia";
}
@GetMapping("/hosocanhan")
public String showHoSoCaNhan(Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("customerEmail", customer.getEmail());

    return "customer/hosocanhan";
}
@PostMapping("/hosocanhan")
public String capNhatHoSoCaNhan(
    @RequestParam("fullName") String fullName,
    @RequestParam("email") String email,
    Model model
) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    // Cập nhật dữ liệu từ form
    customer.setFullName(fullName);
    customer.setEmail(email);

    // Lưu vào database
    customerService.saveCustomer(customer); 

    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("customerEmail", customer.getEmail());
    model.addAttribute("message", "Cập nhật thành công!");

    return "customer/hosocanhan";
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

    return "redirect:/customer"; // hoặc thông báo thành công
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