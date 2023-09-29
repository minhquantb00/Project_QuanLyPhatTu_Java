package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "daotrangs")
@Data
public class DaoTrangs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daotrangid")
    private Integer daoTrangId;
    @Column(name = "noitochuc")
    private String noiToChuc;
    @Column(name = "sothanhvienthamgia")
    private Integer soThanhVienThamGia;
    @Column(name = "thoigiantochuc", columnDefinition = "datetime(6)")
    private LocalDateTime thoiGianToChuc;
    @Column(name = "noidung")
    private String noiDung;
    @Column(name = "daketthuc")
    private Boolean daKetThuc;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoitrutri")
    @JsonIgnoreProperties(value = "daoTrang")
    PhatTus phatTu;

    @OneToMany(mappedBy = "daoTrang")
    @JsonIgnoreProperties(value = "daoTrang")
    List<PhatTuDaoTrangs> phatTuDaoTrang;

    @OneToMany(mappedBy = "daoTrang")
    @JsonIgnoreProperties(value = "daoTrang")
    List<DonDangKys> donDangKy;
}
