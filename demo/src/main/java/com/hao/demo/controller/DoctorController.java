    package com.hao.demo.controller;
    import java.util.Map;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.LinkedHashMap;

    import com.hao.demo.model.BacsiChuyenmon;
    import com.hao.demo.model.Customer;
import com.hao.demo.model.KetquaKhambenh;
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
        // Lấy thông tin đăng nhập dựa vào email từ Spring Security
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


// Trang chính, gọi /doctor để truyền vào
        @GetMapping("")
public String loadDoctorPage(Model model) {
    BacsiChuyenmon bacsi = getLoggedInDoctor();
    if (bacsi == null) return "redirect:/auth/login";

    model.addAttribute("customer", bacsi);
    model.addAttribute("customerName", bacsi.getFullName());
    model.addAttribute("activePage", "home"); // <-- xác định đang mở trang chính

    return "doctor/doctor";
}

    // Lấy từ bảng phan_cong_lich_kham các lịch khám có thông tin tài khoản bác sĩ
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


        @Autowired
        private LichTrungGianRepository lichTrungGianRepository;
            @Autowired
            private DoctorService doctorService;

// Lấy bảng ket_qua_kham các email bệnh nhân đã có kết quả để tạo lịch điều trị và lấy bảng lich_trung_gian để hiển thị
@GetMapping("/tao-lichtrunggian")
public String formTaoLichTrungGian(Model model, Principal principal) {
    BacsiChuyenmon doctor = doctorService.findByEmail(principal.getName()).orElse(null);
    if (doctor == null) return "redirect:/auth/login";

    // ✅ Lọc email bệnh nhân từ bảng kết quả khám
    List<String> emailList = ketquaKhambenhRepository.findByEmailBacSiIgnoreCase(doctor.getEmail())
        .stream()
        .map(k -> k.getEmailBenhNhan())
        .distinct()
        .toList();

    model.addAttribute("emailList", emailList);

    // ✅ Lịch đã tạo của bác sĩ này
    List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBacSi(doctor.getEmail());
    model.addAttribute("lichKhams", lichKhams);

    model.addAttribute("role", "ROLE_DOCTOR");
    model.addAttribute("customerName", doctor.getFullName());

    return "chung/lichtrunggian";
}

    // Bấm nút xóa sẽ kiểm tra đúng thông tin bác sĩ mới xóa trên bảng lich_trung_gian
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



    // Tự điền form nhanh sau khi chọn bệnh nhân, lịch khám gần nhất của bệnh nhân
    @GetMapping("/api/lichkham/{email}")
@ResponseBody
public Map<String, String> getLichKhamTheoEmail(@PathVariable String email) {
    Map<String, String> result = new HashMap<>();
    KetquaKhambenh lich = ketquaKhambenhRepository
        .findByEmailBenhNhanOrderByNgayKhamDesc(email)
        .stream()
        .findFirst()
        .orElse(null);

    if (lich != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        result.put("tenBenhNhan", lich.getTenBenhNhan());
        result.put("tenDichVu", lich.getDichVu());
        result.put("ngayKham", lich.getNgayKham() != null ? lich.getNgayKham().format(formatter) : "");
        result.put("chiTiet", lich.getChiTiet());
    }

    return result;
}



        // ✅ Sửa: Dùng đúng Repository và Model
        @Autowired
        private BacsiChuyenmonRepository bacsiChuyenmonRepository;

        @Autowired
        private PhancongLichkhamRepository phancongLichkhamRepository;

        // Lấy danh sách bệnh nhân trên bảng phan_cong_lich_kham
        @GetMapping("/patients")
public String hienThiDanhSachBenhNhan(
        @RequestParam(value = "keyword", required = false) String keyword,
        Model model) {

    BacsiChuyenmon bacsi = getLoggedInDoctor();
    if (bacsi == null) return "redirect:/auth/login";

    List<PhancongLichkham> danhSachLichKham = phancongLichkhamRepository.findByEmailBacSi(bacsi.getEmail());

    if (keyword != null && !keyword.trim().isEmpty()) {
        String lowerKeyword = keyword.toLowerCase();
        danhSachLichKham = danhSachLichKham.stream()
            .filter(p -> p.getTenBenhNhan().toLowerCase().contains(lowerKeyword)
                      || p.getEmailBenhNhan().toLowerCase().contains(lowerKeyword))
            .toList();
    }

    model.addAttribute("keyword", keyword);
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

        @Autowired
    private com.hao.demo.repository.DichvuRepository dichvuRepository;

    // Lấy bảng phan_cong_lich_kham để nhập kết quả khám 
    @GetMapping("/examination")
    public String showExamination(Model model) {
        BacsiChuyenmon bacsi = getLoggedInDoctor();
        if (bacsi == null) return "redirect:/auth/login";

        // Lấy danh sách email bệnh nhân duy nhất từ lịch khám của bác sĩ này
        List<String> emailBenhNhans = phancongLichkhamRepository.findDistinctEmailBenhNhanByEmailBacSi(bacsi.getEmail());

        // Lấy danh sách dịch vụ từ bảng `dich_vu`
        List<com.hao.demo.model.Dichvu> dichvuList = dichvuRepository.findAll();

        model.addAttribute("emailBenhNhans", emailBenhNhans);
        model.addAttribute("dichvuList", dichvuList);
        model.addAttribute("customer", bacsi);
        model.addAttribute("activePage", "examination");

        return "doctor/doctor"; 
    }

@Autowired
private com.hao.demo.repository.KetquaKhambenhRepository ketquaKhambenhRepository;

// Lưu lại kết quả khám trên bảng ket_qua_kham để bệnh nhân xem
@PostMapping("/examination/submit")
public String luuKetQuaKham(
        @RequestParam("patientEmail") String emailBenhNhan,
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

    BacsiChuyenmon bacsi = getLoggedInDoctor();
    if (bacsi == null) return "redirect:/auth/login";

    KetquaKhambenh ketqua = new KetquaKhambenh();
    ketqua.setEmailBenhNhan(emailBenhNhan);
    ketqua.setTenBenhNhan(tenBenhNhan);
    ketqua.setNgayKham(ngayKham);
    ketqua.setDichVu(dichVu);
    ketqua.setChiTiet(chiTiet); // Nếu không cần chiTiet tổng quát thì bỏ
    ketqua.setEmailBacSi(bacsi.getEmail());
    ketqua.setTenBacSi(bacsi.getFullName());

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
