package com.hao.demo.dto;

import java.time.LocalDate;

public class LichTrungGianDTO {
    private Long id;
    private String tenDichVu;
    private String tenBacSi;
    private LocalDate ngayKham;

    public LichTrungGianDTO(Long id, String tenDichVu, String tenBacSi, LocalDate ngayKham) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.tenBacSi = tenBacSi;
        this.ngayKham = ngayKham;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }

    public String getTenBacSi() { return tenBacSi; }
    public void setTenBacSi(String tenBacSi) { this.tenBacSi = tenBacSi; }

    public LocalDate getNgayKham() { return ngayKham; }
    public void setNgayKham(LocalDate ngayKham) { this.ngayKham = ngayKham; }
}
