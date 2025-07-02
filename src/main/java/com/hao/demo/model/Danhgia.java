package com.hao.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "danh_gia")
public class Danhgia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenBacSi;
    private String dichVu;
    private int mucDoHaiLong;
    private String nhanXet;
    private String emailBenhNhan;

    @Column(nullable = false)
    private boolean daXem = false;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenBacSi() { return tenBacSi; }
    public void setTenBacSi(String tenBacSi) { this.tenBacSi = tenBacSi; }

    public String getDichVu() { return dichVu; }
    public void setDichVu(String dichVu) { this.dichVu = dichVu; }

    public int getMucDoHaiLong() { return mucDoHaiLong; }
    public void setMucDoHaiLong(int mucDoHaiLong) { this.mucDoHaiLong = mucDoHaiLong; }

    public String getNhanXet() { return nhanXet; }
    public void setNhanXet(String nhanXet) { this.nhanXet = nhanXet; }

    public String getEmailBenhNhan() { return emailBenhNhan; }
    public void setEmailBenhNhan(String emailBenhNhan) { this.emailBenhNhan = emailBenhNhan; }

    public boolean isDaXem() { return daXem; }
    public void setDaXem(boolean daXem) { this.daXem = daXem; }
}