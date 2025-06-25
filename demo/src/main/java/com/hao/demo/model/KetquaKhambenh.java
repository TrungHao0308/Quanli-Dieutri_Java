package com.hao.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "ket_qua_kham")
public class KetquaKhambenh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailBenhNhan;
    private String tenBenhNhan;
    private LocalDate ngayKham;
    private String dichVu;
    private String chiTiet;
    private String emailBacSi;
    private String tenBacSi;
    private String ketQua; // ví dụ: "Siêu âm cho thấy tử cung bình thường..."

    // Getters and Setters
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
    public LocalDate getNgayKham() {
        return ngayKham;
    }
    public void setNgayKham(LocalDate ngayKham) {
        this.ngayKham = ngayKham;
    }
    public String getDichVu() {
        return dichVu;
    }
    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }
    public String getChiTiet() {
        return chiTiet;
    }
    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
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
    public String getKetQua() {
        return ketQua;
    }
    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }
    @Override
    public String toString() {
        return "KetquaKhambenh{" +
                "id=" + id +
                ", emailBenhNhan='" + emailBenhNhan + '\'' +
                ", tenBenhNhan='" + tenBenhNhan + '\'' +
                ", ngayKham=" + ngayKham +
                ", dichVu='" + dichVu + '\'' +
                ", chiTiet='" + chiTiet + '\'' +
                ", emailBacSi='" + emailBacSi + '\'' +
                ", tenBacSi='" + tenBacSi + '\'' +
                ", ketQua='" + ketQua + '\'' +
                '}';
    }

}
