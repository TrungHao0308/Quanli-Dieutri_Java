package com.hao.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lich_trung_gian")
public class LichTrungGian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailBenhNhan;
    private String tenBenhNhan;

    private String emailBacSi;
    private String tenBacSi;

    private String tenDichVu;

    private LocalDate ngayKham;
    private String chiTiet;

    private LocalDate lichXetNghiem;
    private LocalDate lichTiemThuoc;
    private LocalDate lichTaiKham;

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailBenhNhan() {
        return emailBenhNhan;
    }

    public void setEmailBenhNhan(String emailBenhNhan) {
        this.emailBenhNhan = emailBenhNhan;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan;
    }

    public void setTenBenhNhan(String tenBenhNhan) {
        this.tenBenhNhan = tenBenhNhan;
    }

    public String getEmailBacSi() {
        return emailBacSi;
    }

    public void setEmailBacSi(String emailBacSi) {
        this.emailBacSi = emailBacSi;
    }

    public String getTenBacSi() {
        return tenBacSi;
    }

    public void setTenBacSi(String tenBacSi) {
        this.tenBacSi = tenBacSi;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public LocalDate getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(LocalDate ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public LocalDate getLichXetNghiem() {
        return lichXetNghiem;
    }

    public void setLichXetNghiem(LocalDate lichXetNghiem) {
        this.lichXetNghiem = lichXetNghiem;
    }

    public LocalDate getLichTiemThuoc() {
        return lichTiemThuoc;
    }

    public void setLichTiemThuoc(LocalDate lichTiemThuoc) {
        this.lichTiemThuoc = lichTiemThuoc;
    }

    public LocalDate getLichTaiKham() {
        return lichTaiKham;
    }

    public void setLichTaiKham(LocalDate lichTaiKham) {
        this.lichTaiKham = lichTaiKham;
    }
}
