package com.hao.demo.controller;

import com.hao.demo.model.Customer;
import com.hao.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CustomerService customerService;

    @Autowired
    public AdminController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Hiển thị trang quản lý tài khoản
    @GetMapping("admin")
    public String showAdminPage(Model model) {
        return loadAdminPage(model, "admin/admin");
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

    private String loadAdminPage(Model model, String viewName) {
        Customer customer = getLoggedInCustomer();
        if (customer == null) return "redirect:/auth/login";

        model.addAttribute("customer", customer);
        model.addAttribute("customerName", customer.getFullName());

        return viewName;
    }
}
//     // Lấy danh sách tài khoản (hỗ trợ AJAX)
//     @GetMapping(produces = "application/json")
//     @ResponseBody
//     public List<Customer> getCustomers(@RequestParam(required = false) String search,
//                                        @RequestParam(required = false) String role) {
//         List<Customer> customers = customerService.getAllCustomers();
//         if (search != null && !search.isEmpty()) {
//             String searchLower = search.toLowerCase();
//             customers = customers.stream()
//                     .filter(customer -> customer.getUsername().toLowerCase().contains(searchLower) ||
//                                         customer.getFullName().toLowerCase().contains(searchLower) ||
//                                         customer.getEmail().toLowerCase().contains(searchLower))
//                     .toList();
//         }
//         if (role != null && !role.isEmpty()) {
//             customers = customers.stream()
//                     .filter(customer -> customer.getRole().equals(role))
//                     .toList();
//         }
//         return customers;
//     }

//     // Lấy thông tin tài khoản theo ID
//     @GetMapping("/{id}")
//     @ResponseBody
//     public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
//         return customerService.findById(id)
//                 .map(ResponseEntity::ok)
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // Lưu tài khoản (thêm hoặc sửa)
//     @PostMapping("/save")
//     @ResponseBody
//     public void saveCustomer(@RequestBody Customer customer) {
//         if (customer.getId() == null) {
//             customerService.addCustomer(customer);
//         } else {
//             customerService.updateCustomer(customer);
//         }
//     }

//     // Xóa tài khoản
//     @PostMapping("/delete/{id}")
//     @ResponseBody
//     public void deleteCustomer(@PathVariable Long id) {
//         customerService.deleteCustomer(id);
//     }

//     // Helper method để kiểm tra đăng nhập và load trang
//     private String loadAdminPage(Model model, String viewName) {
//         Customer customer = getLoggedInCustomer();
//         if (customer == null) {
//             return "redirect:/auth/login";
//         }

//         model.addAttribute("customer", customer);
//         model.addAttribute("customerName", customer.getFullName());
//         model.addAttribute("customers", customerService.getAllCustomers());

//         return viewName;
//     }

//     // Lấy thông tin người dùng đã đăng nhập
//     private Customer getLoggedInCustomer() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication == null || !authentication.isAuthenticated() ||
//                 authentication.getPrincipal().equals("anonymousUser")) {
//             return null;
//         }

//         String email = authentication.getName();
//         return customerService.findByEmail(email).orElse(null);
//     }
// }