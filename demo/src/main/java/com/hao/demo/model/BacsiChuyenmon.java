package com.hao.demo.model;
import com.hao.demo.model.Customer;
import jakarta.persistence.*;



@Entity
@Table(name = "bac_si_chuyen_mon")
public class BacsiChuyenmon {
    @OneToOne
@JoinColumn(name = "customer_id")
private Customer customer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String ten;

    private String chuyenMon;

    private String caLam;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }
    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public String getCaLam() {
        return caLam;
    }
    public void setCaLam(String caLam) {
        this.caLam = caLam;
    }
    public Customer getCustomer() {
    return customer;
}

public void setCustomer(Customer customer) {
    this.customer = customer;
}

    @Override
    public String toString() {
        return "BacsiChuyenmon{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", ten='" + ten + '\'' +
                ", chuyenMon='" + chuyenMon + '\'' +
                ", caLam='" + caLam + '\'' +
                '}';
    }
    public String getFullName() {
    return this.ten;
}
}
