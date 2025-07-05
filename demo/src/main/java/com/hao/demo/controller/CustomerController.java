package com.hao.demo.controller;
import java.time.Duration;

import java.time.LocalDate;

import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.Customer;
import com.hao.demo.model.DangkiDichvu;
import com.hao.demo.model.Danhgia;
import com.hao.demo.model.KetquaKhambenh;
import com.hao.demo.model.LichTrungGian;
import com.hao.demo.model.PhancongLichkham;
import com.hao.demo.repository.DangkiDichvuRepository;
import com.hao.demo.repository.DanhgiaRepository;
import com.hao.demo.repository.DichvuRepository;
import com.hao.demo.repository.KetquaKhambenhRepository;
import com.hao.demo.repository.LichTrungGianRepository;
import com.hao.demo.repository.PhancongLichkhamRepository;
import com.hao.demo.service.BacsiChuyenmonService;
import com.hao.demo.service.CustomerService;
import java.util.List;
import java.security.Principal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.stream.Collectors;

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

// @GetMapping("/dangkidichvu")
// public String showDangKiDichVu(Model model) {
//     Customer customer = getLoggedInCustomer();
//     if (customer == null) return "redirect:/auth/login";

//     model.addAttribute("customerName", customer.getFullName());
//     model.addAttribute("dichVuList", dichvuRepository.findAll()); // truyền dịch vụ

//     return "customer/dangkidichvu";
// }
@GetMapping("/dangkidichvu")
public String showDangKiDichVu(@RequestParam(value = "dichVu", required = false) String selectedDichVu,
                               Model model) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    model.addAttribute("customerName", customer.getFullName());
    model.addAttribute("dichVuList", bacsiChuyenmonService.getDistinctSpecialities());
    model.addAttribute("selectedDichVu", selectedDichVu);

    if (selectedDichVu != null && !selectedDichVu.isEmpty()) {
        List<BacsiChuyenmon> bacSiList = bacsiChuyenmonService.getDoctorsBySpeciality(selectedDichVu);
        model.addAttribute("bacSiList", bacSiList);
    }

    return "customer/dangkidichvu";
}
@GetMapping("/api/dichvu-tu-bacsis")
@ResponseBody
public List<String> getDistinctDichVuFromBacSi() {
    return bacsiChuyenmonService.getDistinctSpecialities();
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
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    String email = customer.getEmail();

    List<KetquaKhambenh> ketQuaList = ketquaKhambenhRepository.findByEmailBenhNhanOrderByNgayKhamDesc(email);
    List<LichTrungGian> lichTrinhList = lichTrungGianRepository.findByEmailBenhNhan(email);

    // Fix lỗi null
    model.addAttribute("ketQuaList", ketQuaList != null ? ketQuaList : new ArrayList<>());
    model.addAttribute("lichTrinhList", lichTrinhList != null ? lichTrinhList : new ArrayList<>());
    model.addAttribute("customerName", customer.getFullName());

    return "customer/lichsudondat";
}


@GetMapping("/danhgia")
public String showDanhGiaForm(Model model, Principal principal) {
    String email = principal.getName();
    List<KetquaKhambenh> ketquaList = ketquaKhambenhRepository.findByEmailBenhNhan(email);

    Set<String> dichVuSet = ketquaList.stream()
        .map(KetquaKhambenh::getDichVu)
        .collect(Collectors.toSet());

    Set<String> bacSiSet = ketquaList.stream()
        .map(KetquaKhambenh::getTenBacSi)
        .collect(Collectors.toSet());

    model.addAttribute("dichVuList", dichVuSet);
    model.addAttribute("bacSiList", bacSiSet);
    model.addAttribute("customerName", "Khách hàng");

    return "customer/danhgia";
}



@Autowired
private DanhgiaRepository danhgiaRepository;

@PostMapping("/danhgia")
public String guiDanhGia(
    @RequestParam("service") String dichVu,
    @RequestParam("rating") int mucDoHaiLong,
    @RequestParam(name = "doctor", required = false) String doctorCode,
    @RequestParam("comment") String nhanXet,
    RedirectAttributes redirectAttributes
) {
    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    BacsiChuyenmon bacSi = null;
    if (doctorCode != null && !doctorCode.isEmpty()) {
        List<BacsiChuyenmon> ds = bacsiChuyenmonService.getDoctorsBySpeciality(dichVu);
        if (!ds.isEmpty()) {
            bacSi = ds.stream()
                .filter(b -> b.getFullName().equalsIgnoreCase(doctorCode))
                .findFirst()
                .orElse(null);
        }
    }

    Danhgia dg = new Danhgia();
    dg.setDichVu(dichVu);
    dg.setMucDoHaiLong(mucDoHaiLong);
    dg.setNhanXet(nhanXet);
    dg.setTenKhachHang(customer.getFullName());
    dg.setNgayDanhGia(LocalDate.now());
    if (bacSi != null) {
        dg.setTenBacSi(bacSi.getFullName());
        dg.setEmailBacSi(bacSi.getEmail());
    }

    danhgiaRepository.save(dg);

    redirectAttributes.addFlashAttribute("message", "Cảm ơn bạn đã đánh giá!");
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
        @RequestParam("gioKham") String gioKham,
        @RequestParam("chiTiet") String chiTiet,
        Model model) {

    Customer customer = getLoggedInCustomer();
    if (customer == null) return "redirect:/auth/login";

    DangkiDichvu dk = new DangkiDichvu();
    dk.setDichVu(dichVu);
    dk.setTenBacSi(tenBacSi);
    dk.setEmailBacSi(emailBacSi);
    dk.setNgayKham(ngayKham);
      dk.setGioKham(gioKham);
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

// @GetMapping("/api/thongbao")
// @ResponseBody
// public List<Map<String, String>> getThongBaoData() {
//     Customer customer = getLoggedInCustomer();
//     List<Map<String, String>> thongBaoList = new ArrayList<>();

//     if (customer == null) return thongBaoList;

//     String email = customer.getEmail();
//     DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

//     // Lịch khám
//     List<PhancongLichkham> lichKhams = phancongLichkhamRepository.findByEmailBenhNhan(email);
//     for (PhancongLichkham lich : lichKhams) {
//         Map<String, String> tb = new HashMap<>();
//         tb.put("loai", "lichkham");
//         tb.put("tieuDe", "📅 Bạn có lịch khám vào " + lich.getNgayKham().toString());
//         tb.put("link", "/customer/thongbao");
//         tb.put("id", "lichkham-" + lich.getId());
//         if (lich.getCreatedAt() != null) {
//             tb.put("thoiGian", lich.getCreatedAt().format(isoFormatter));
//         }
//         thongBaoList.add(tb);
//     }

//     // Kết quả khám
//     List<KetquaKhambenh> kqs = ketquaKhambenhRepository.findByEmailBenhNhanOrderByNgayKhamDesc(email);
//     for (KetquaKhambenh kq : kqs) {
//         Map<String, String> tb = new HashMap<>();
//         tb.put("loai", "ketqua");
//         tb.put("tieuDe", "📝 Có kết quả khám mới ngày " + kq.getNgayKham().toString());
//         tb.put("link", "/customer/ketquadieutri");
//         tb.put("id", "ketqua-" + kq.getId());
//         if (kq.getCreatedAt() != null) {
//             tb.put("thoiGian", kq.getCreatedAt().format(isoFormatter));
//         }
//         thongBaoList.add(tb);
//     }

//     // Lịch trình điều trị
//     List<LichTrungGian> lichTrinhs = lichTrungGianRepository.findByEmailBenhNhan(email);
//     for (PhancongLichkham lich : lichKhams) {
//     // Thêm nhắc nhở thông minh
//     if (lich.getNgayKham() != null && lich.getLichKham() != null) {
//         try {
//             LocalDate ngay = lich.getNgayKham();
//             LocalTime gio = LocalTime.parse(lich.getLichKham());
//             LocalDateTime thoiDiemKham = LocalDateTime.of(ngay, gio);
//             Duration duration = Duration.between(LocalDateTime.now(), thoiDiemKham);

//             long hours = duration.toHours();
//             String reminder = null;

//             if (hours <= 6 && hours >= 0) {
//                 reminder = "🕕 Bạn có lịch khám sau 6 giờ nữa, đừng quên nhé!";
//             } else if (hours <= 12 && hours > 6) {
//                 reminder = "⏰ Bạn có lịch khám sau 12 giờ nữa.";
//             } else if (hours <= 24 && hours > 12) {
//                 reminder = "📅 Bạn có lịch khám vào ngày mai.";
//             }

//             if (reminder != null) {
//                 Map<String, String> nhac = new HashMap<>();
//                 nhac.put("loai", "nhaclich");
//                 nhac.put("tieuDe", reminder);
//                 nhac.put("link", "/customer/thongbao");
//                 nhac.put("id", "nhaclich-" + lich.getId());
//                 nhac.put("thoiGian", LocalDateTime.now().format(isoFormatter));
//                 thongBaoList.add(nhac);
//             }
//         } catch (Exception e) {
//             System.err.println("Lỗi khi phân tích thời gian lịch khám: " + e.getMessage());
//         }
//     }

//     // Thông báo lịch khám mặc định
//     Map<String, String> tb = new HashMap<>();
//     tb.put("loai", "lichkham");
//     tb.put("tieuDe", "📅 Bạn có lịch khám vào " + lich.getNgayKham().toString());
//     tb.put("link", "/customer/thongbao");
//     tb.put("id", "lichkham-" + lich.getId());
//     if (lich.getCreatedAt() != null) {
//         tb.put("thoiGian", lich.getCreatedAt().format(isoFormatter));
//     }
//     thongBaoList.add(tb);
// }

//     // Sau khi đã add tất cả thông báo:
// thongBaoList = thongBaoList.stream()
//     .sorted((a, b) -> {
//         String timeA = a.get("thoiGian");
//         String timeB = b.get("thoiGian");
//         if (timeA == null) return 1;
//         if (timeB == null) return -1;
//         return timeB.compareTo(timeA); // giảm dần
//     })
//     .collect(Collectors.toList());

//     return thongBaoList;
// }
@GetMapping("/api/thongbao")
@ResponseBody
public List<Map<String, String>> getThongBaoData() {
    Customer customer = getLoggedInCustomer();
    List<Map<String, String>> thongBaoList = new ArrayList<>();

    if (customer == null) return thongBaoList;

    String email = customer.getEmail();
    DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // 1. Lịch khám
    List<PhancongLichkham> lichKhams = phancongLichkhamRepository.findByEmailBenhNhan(email);
    for (PhancongLichkham lich : lichKhams) {
        // Thông báo lịch khám
        Map<String, String> tb = new HashMap<>();
        tb.put("loai", "lichkham");
        tb.put("tieuDe", "📅 Bạn có lịch khám vào " + lich.getNgayKham());
        tb.put("link", "/customer/thongbao");
        tb.put("id", "lichkham-" + lich.getId());
        if (lich.getCreatedAt() != null) {
            tb.put("thoiGian", lich.getCreatedAt().format(isoFormatter));
        }
        thongBaoList.add(tb);

        // Nhắc lịch thông minh
        if (lich.getNgayKham() != null && lich.getLichKham() != null) {
            try {
                LocalDateTime thoiDiemKham = LocalDateTime.of(lich.getNgayKham(), LocalTime.parse(lich.getLichKham()));
                Duration duration = Duration.between(LocalDateTime.now(), thoiDiemKham);
                long hours = duration.toHours();

                String reminder = null;
                if (hours <= 6 && hours >= 0) {
                    reminder = "🕕 Bạn có lịch khám sau 6 giờ nữa, đừng quên nhé!";
                } else if (hours <= 12) {
                    reminder = "⏰ Bạn có lịch khám sau 12 giờ nữa.";
                } else if (hours <= 24) {
                    reminder = "📅 Bạn có lịch khám vào ngày mai.";
                }

                if (reminder != null) {
                    Map<String, String> nhac = new HashMap<>();
                    nhac.put("loai", "nhaclich");
                    nhac.put("tieuDe", reminder);
                    nhac.put("link", "/customer/thongbao");
                    nhac.put("id", "nhaclich-" + lich.getId());
                    nhac.put("thoiGian", LocalDateTime.now().format(isoFormatter));
                    thongBaoList.add(nhac);
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi xử lý giờ lịch khám: " + e.getMessage());
            }
        }
    }

    // 2. Kết quả khám
    List<KetquaKhambenh> kqs = ketquaKhambenhRepository.findByEmailBenhNhanOrderByNgayKhamDesc(email);
    for (KetquaKhambenh kq : kqs) {
        Map<String, String> tb = new HashMap<>();
        tb.put("loai", "ketqua");
        tb.put("tieuDe", "📝 Có kết quả khám mới ngày " + kq.getNgayKham());
        tb.put("link", "/customer/ketquadieutri");
        tb.put("id", "ketqua-" + kq.getId());
        if (kq.getCreatedAt() != null) {
            tb.put("thoiGian", kq.getCreatedAt().format(isoFormatter));
        }
        thongBaoList.add(tb);
    }

    // 3. Lịch trình điều trị
    List<LichTrungGian> lichTrinhs = lichTrungGianRepository.findByEmailBenhNhan(email);
    for (LichTrungGian lich : lichTrinhs) {
        Map<String, String> tb = new HashMap<>();
        tb.put("loai", "lichtrinh");
        tb.put("tieuDe", "📌 Có cập nhật lịch điều trị mới ngày " + lich.getNgayKham());
        tb.put("link", "/customer/lichtrinhdieutri");
        tb.put("id", "lichtrinh-" + lich.getId());

        if (lich.getCreatedAt() != null) {
    tb.put("thoiGian", lich.getCreatedAt().format(isoFormatter));
}
 else {
            tb.put("thoiGian", LocalDateTime.now().format(isoFormatter));
        }

        thongBaoList.add(tb);
    }

    // 4. Sắp xếp thời gian giảm dần
    thongBaoList = thongBaoList.stream()
        .sorted((a, b) -> {
            String timeA = a.get("thoiGian");
            String timeB = b.get("thoiGian");
            if (timeA == null) return 1;
            if (timeB == null) return -1;
            return timeB.compareTo(timeA);
        })
        .collect(Collectors.toList());

    return thongBaoList;
}

}