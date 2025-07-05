package com.hao.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.CreationTimestamp;
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

    private String tenChong;
    private String ketQuaChong;

    private String tenVo;
    private String ketQuaVo;

    private String chiTietChong;
    private String chiTietVo;


@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmailBenhNhan() { return emailBenhNhan; }
    public void setEmailBenhNhan(String emailBenhNhan) { this.emailBenhNhan = emailBenhNhan; }

    public String getTenBenhNhan() { return tenBenhNhan; }
    public void setTenBenhNhan(String tenBenhNhan) { this.tenBenhNhan = tenBenhNhan; }

    public LocalDate getNgayKham() { return ngayKham; }
    public void setNgayKham(LocalDate ngayKham) { this.ngayKham = ngayKham; }

    public String getDichVu() { return dichVu; }
    public void setDichVu(String dichVu) { this.dichVu = dichVu; }

    public String getChiTiet() { return chiTiet; }
    public void setChiTiet(String chiTiet) { this.chiTiet = chiTiet; }

    public String getEmailBacSi() { return emailBacSi; }
    public void setEmailBacSi(String emailBacSi) { this.emailBacSi = emailBacSi; }

    public String getTenBacSi() { return tenBacSi; }
    public void setTenBacSi(String tenBacSi) { this.tenBacSi = tenBacSi; }

    public String getTenChong() { return tenChong; }
    public void setTenChong(String tenChong) { this.tenChong = tenChong; }

    public String getKetQuaChong() { return ketQuaChong; }
    public void setKetQuaChong(String ketQuaChong) { this.ketQuaChong = ketQuaChong; }

    public String getTenVo() { return tenVo; }
    public void setTenVo(String tenVo) { this.tenVo = tenVo; }

    public String getKetQuaVo() { return ketQuaVo; }
    public void setKetQuaVo(String ketQuaVo) { this.ketQuaVo = ketQuaVo; }
    
    public String getChiTietChong() { return chiTietChong; }
public void setChiTietChong(String chiTietChong) { this.chiTietChong = chiTietChong; }

public String getChiTietVo() { return chiTietVo; }
public void setChiTietVo(String chiTietVo) { this.chiTietVo = chiTietVo; }

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
            ", tenChong='" + tenChong + '\'' +
            ", ketQuaChong='" + ketQuaChong + '\'' +
            ", chiTietChong='" + chiTietChong + '\'' +
            ", tenVo='" + tenVo + '\'' +
            ", ketQuaVo='" + ketQuaVo + '\'' +
            ", chiTietVo='" + chiTietVo + '\'' +
            '}';
}

}
