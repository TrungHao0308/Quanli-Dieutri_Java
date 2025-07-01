package com.hao.demo.model;

import jakarta.persistence.*;
@Entity
@Table(name = "bao_cao")
public class Baocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenDichVu;
    private int soLuong;
    private double doanhThu;
        private String thoiGian;
    // getters and setters
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
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public double getDoanhThu() {
        return doanhThu;
    }
    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }


public String getThoiGian() {
    return thoiGian;
}

public void setThoiGian(String thoiGian) {
    this.thoiGian = thoiGian;
}
    @Override
public String toString() {
    return "Baocao{" +
            "id=" + id +
            ", tenDichVu='" + tenDichVu + '\'' +
            ", soLuong=" + soLuong +
            ", doanhThu=" + doanhThu +
            ", thoiGian='" + thoiGian + '\'' +
            '}';
}

}
