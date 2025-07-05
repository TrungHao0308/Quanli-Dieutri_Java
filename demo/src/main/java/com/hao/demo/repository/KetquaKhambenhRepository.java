    package com.hao.demo.repository;

    import com.hao.demo.model.KetquaKhambenh;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.time.LocalDate;
    import java.util.List;

    public interface KetquaKhambenhRepository extends JpaRepository<KetquaKhambenh, Long> {
        List<KetquaKhambenh> findByEmailBenhNhanOrderByNgayKhamDesc(String emailBenhNhan);
        List<KetquaKhambenh> findByEmailBacSiIgnoreCase(String emailBacSi);
        List<KetquaKhambenh> findByEmailBenhNhan(String emailBenhNhan); 
        // ✅ Sửa tên method đúng với tên biến trong entity
        List<KetquaKhambenh> findByDichVuAndNgayKhamBetween(String dichVu, LocalDate start, LocalDate end);
    }
