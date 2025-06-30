// package com.hao.demo.controller;

// import com.hao.demo.model.Customer;
// import com.hao.demo.model.LichTrungGian;
// import com.hao.demo.model.BacsiChuyenmon;
// import com.hao.demo.repository.LichTrungGianRepository;
// import com.hao.demo.service.CustomerService;
// import com.hao.demo.service.DoctorService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.security.Principal;
// import java.time.LocalDate;
// import java.util.List;

// @Controller
// public class LichTrungGianController {

//     @Autowired
//     private LichTrungGianRepository lichTrungGianRepository;

//     @Autowired
//     private CustomerService customerService;

   
//     @Autowired
// private DoctorService doctorService;

// @GetMapping("/doctor/lichtrinh")
// public String lichDoctor(Model model) {
//     BacsiChuyenmon bacsi = getLoggedInDoctor();
//     if (bacsi == null) return "redirect:/auth/login";

//     List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBacSi(bacsi.getEmail());

//     model.addAttribute("role", "ROLE_DOCTOR");
//     model.addAttribute("lichKhams", lichKhams);
//     model.addAttribute("customerName", bacsi.getFullName());

//     return "chung/lichtrunggian";
// }

//     private Customer getLoggedInCustomer() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//             return null;
//         }
//         return customerService.findByEmail(auth.getName()).orElse(null);
//     }

//     private BacsiChuyenmon getLoggedInDoctor() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//             return null;
//         }
//         return doctorService.findByEmail(auth.getName()).orElse(null);
//     }
//     @PostMapping("/doctor/lichtrunggian/them")
// public String themLichTrungGian(@RequestParam String emailBenhNhan,
//                                 @RequestParam String tenBenhNhan,
//                                 @RequestParam String tenDichVu,
//                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKham,
//                                 @RequestParam String chiTiet,
//                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichXetNghiem,
//                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichTiemThuoc,
//                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichTaiKham,
//                                 Principal principal) {

//     BacsiChuyenmon doctor = doctorService.findByEmail(principal.getName()).orElse(null);
//     if (doctor == null) return "redirect:/auth/login";

//     LichTrungGian lich = new LichTrungGian();
//     lich.setEmailBenhNhan(emailBenhNhan);
//     lich.setTenBenhNhan(tenBenhNhan);
//     lich.setTenDichVu(tenDichVu);
//     lich.setNgayKham(ngayKham);
//     lich.setChiTiet(chiTiet);
//     lich.setLichXetNghiem(lichXetNghiem);
//     lich.setLichTiemThuoc(lichTiemThuoc);
//     lich.setLichTaiKham(lichTaiKham);
//     lich.setEmailBacSi(doctor.getEmail());
//     lich.setTenBacSi(doctor.getFullName());

//     lichTrungGianRepository.save(lich);

//     return "redirect:/doctor/tao-lichtrunggian"; // Trả về trang xem danh sách
// }

// }

package com.hao.demo.controller;

import com.hao.demo.model.Customer;
import com.hao.demo.model.LichTrungGian;
import com.hao.demo.model.BacsiChuyenmon;
import com.hao.demo.model.KetquaKhambenh;
import com.hao.demo.repository.LichTrungGianRepository;
import com.hao.demo.repository.KetquaKhambenhRepository;
import com.hao.demo.service.CustomerService;
import com.hao.demo.service.DoctorService;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LichTrungGianController {

    @Autowired
    private LichTrungGianRepository lichTrungGianRepository;

    @Autowired
    private KetquaKhambenhRepository ketquaKhambenhRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/lichtrinh")
    public String lichDoctor(Model model) {
        BacsiChuyenmon bacsi = getLoggedInDoctor();
        if (bacsi == null) return "redirect:/auth/login";

        List<LichTrungGian> lichKhams = lichTrungGianRepository.findByEmailBacSi(bacsi.getEmail());

        // Lấy danh sách email bệnh nhân từ bảng kết quả khám
        List<String> emailList = ketquaKhambenhRepository
    .findByEmailBacSiIgnoreCase(bacsi.getEmail()).stream()
    .map(KetquaKhambenh::getEmailBenhNhan)
    .distinct()
    .collect(Collectors.toList());
System.out.println("=> Bác sĩ đang login: " + bacsi.getEmail());
emailList.forEach(email -> System.out.println("   - Bệnh nhân: " + email));


        model.addAttribute("role", "ROLE_DOCTOR");
        model.addAttribute("lichKhams", lichKhams);
        model.addAttribute("emailList", emailList);  // Truyền danh sách email
        model.addAttribute("customerName", bacsi.getFullName());

        return "chung/lichtrunggian";
    }

    private Customer getLoggedInCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return customerService.findByEmail(auth.getName()).orElse(null);
    }

    private BacsiChuyenmon getLoggedInDoctor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return doctorService.findByEmail(auth.getName()).orElse(null);
    }

    @PostMapping("/doctor/lichtrunggian/them")
    public String themLichTrungGian(@RequestParam String emailBenhNhan,
                                    @RequestParam String tenBenhNhan,
                                    @RequestParam String tenDichVu,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKham,
                                    @RequestParam String chiTiet,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichXetNghiem,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichTiemThuoc,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lichTaiKham,
                                    Principal principal) {

        BacsiChuyenmon doctor = doctorService.findByEmail(principal.getName()).orElse(null);
        if (doctor == null) return "redirect:/auth/login";

        LichTrungGian lich = new LichTrungGian();
        lich.setEmailBenhNhan(emailBenhNhan);
        lich.setTenBenhNhan(tenBenhNhan);
        lich.setTenDichVu(tenDichVu);
        lich.setNgayKham(ngayKham);
        lich.setChiTiet(chiTiet);
        lich.setLichXetNghiem(lichXetNghiem);
        lich.setLichTiemThuoc(lichTiemThuoc);
        lich.setLichTaiKham(lichTaiKham);
        lich.setEmailBacSi(doctor.getEmail());
        lich.setTenBacSi(doctor.getFullName());

        lichTrungGianRepository.save(lich);

        return "redirect:/doctor/lichtrinh";
    }

}
