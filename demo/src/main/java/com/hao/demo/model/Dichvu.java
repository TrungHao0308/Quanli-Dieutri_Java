package com.hao.demo.model;

import jakarta.persistence.*;
//
@Entity
@Table(name = "dich_vu")
public class Dichvu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenDichVu;
    private double giaKham;
    private String moTa;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTenDichVu() {
        return tenDichVu;
    }
    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
    public Double getGiaKham() {
        return giaKham;
    }
    public void setGiaKham(double giaKham) {
        this.giaKham = giaKham;
    }
    public String getMoTa() {
        return moTa;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    @Override
    public String toString() {
        return "Dichvu{" +
                "id=" + id +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", giaKham=" + giaKham +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
