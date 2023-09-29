package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chuas")
@Data
public class Chuas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chuaid")
    private Integer chuaId;
    @Column(name = "tenchua")
    private String tenChua;
    @Column(name = "ngaythanhlap", columnDefinition = "datetime(6)")
    private LocalDateTime ngayThanhLap;
    @Column(name = "diachi")
    private String diaChi;
    @Column(name = "trutri")
    private Integer truTri;
    @Column(name = "capnhap", columnDefinition = "datetime(6)")
    private LocalDateTime capNhap;
    @OneToMany(mappedBy = "chua")
    @JsonIgnoreProperties(value = "chua")
    List<PhatTus> phatTu;
}
