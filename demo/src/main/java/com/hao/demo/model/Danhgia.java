package com.hao.demo.model;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "danh_gia")
public class Danhgia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailBacSi;
    private String tenBacSi;
    private String tenKhachHang;
    private String dichVu;
    private int mucDoHaiLong;
    private String nhanXet;
    private LocalDate ngayDanhGia;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getDichVu() {
        return dichVu;
    }
    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    public int getMucDoHaiLong() {
        return mucDoHaiLong;
    }
    public void setMucDoHaiLong(int mucDoHaiLong) {
        this.mucDoHaiLong = mucDoHaiLong;
    }

    public String getNhanXet() {
        return nhanXet;
    }
    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public LocalDate getNgayDanhGia() {
        return ngayDanhGia;
    }
    public void setNgayDanhGia(LocalDate ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }

    @Override
    public String toString() {
        return "Danhgia{" +
                "id=" + id +
                ", emailBacSi='" + emailBacSi + '\'' +
                ", tenBacSi='" + tenBacSi + '\'' +
                ", dichVu='" + dichVu + '\'' +
                ", mucDoHaiLong=" + mucDoHaiLong +
                ", nhanXet='" + nhanXet + '\'' +
                ", tenKhachHang='" + tenKhachHang + '\'' +
                ", ngayDanhGia=" + ngayDanhGia +
                '}';
    }
}
