package com.hao.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "phan_cong_lich_kham")
public class PhancongLichkham {
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
    private String lichKham; // ví dụ: "10:00 - Phòng 3"
@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

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
    public String getLichKham() {
        return lichKham;
    }
    public void setLichKham(String lichKham) {
        this.lichKham = lichKham;
    }   
    @Override
    public String toString() {
        return "PhancongLichkham{" +
                "id=" + id +
                ", emailBenhNhan='" + emailBenhNhan + '\'' +
                ", tenBenhNhan='" + tenBenhNhan + '\'' +
                ", ngayKham=" + ngayKham +
                ", dichVu='" + dichVu + '\'' +
                ", chiTiet='" + chiTiet + '\'' +
                ", emailBacSi='" + emailBacSi + '\'' +
                ", tenBacSi='" + tenBacSi + '\'' +
                ", lichKham='" + lichKham + '\'' +
                '}';
    }
}
