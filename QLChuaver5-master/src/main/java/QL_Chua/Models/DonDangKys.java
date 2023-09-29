package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "dondangkys")
@Data
public class DonDangKys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dondangkyid")
    private Integer donDangKyId;
    @Column(name = "trangthaidon")
    private Integer trangThaiDon;
    @Column(name = "bgayguidon", columnDefinition = "datetime(6)")
    private LocalDateTime ngayGuiDon;
    @Column(name = "ngayxuly", columnDefinition = "datetime(6)")
    private LocalDateTime ngayXuLy;
    @Column(name = "nguoixuly")
    private Integer nguoiXuLy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phattuid")
    @JsonIgnoreProperties(value = "donDangKyPhatTu")
    PhatTus phatTuId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "daotrangid")
    @JsonIgnoreProperties(value = "donDangKy")
    DaoTrangs daoTrang;
}
