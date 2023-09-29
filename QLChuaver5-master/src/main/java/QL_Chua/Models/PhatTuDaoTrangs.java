package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phattudaotrangs")
@Data
public class PhatTuDaoTrangs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phatudaotrangid")
    private Integer phatTuDaoTrangId;
    @Column(name = "dathamgia")
    private Boolean daThamGia;
    @Column(name = "lydokhongthamgia")
    private String lyDoKhongThamGia;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "daotrangid")
    @JsonIgnoreProperties(value = "phatTuDaoTrang")
    DaoTrangs daoTrang;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phattuid")
    @JsonIgnoreProperties(value = "phatTuDaoTrang")
    PhatTus phatTu;
}
