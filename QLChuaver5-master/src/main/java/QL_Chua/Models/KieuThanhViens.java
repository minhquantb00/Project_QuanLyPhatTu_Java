package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "kieuthanhviens")
@Data
public class KieuThanhViens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kieuthanhvienid")
    private int kieuThanhVienId;
    @Column(name = "code")
    private String code;
    @Column(name = "tenkieu")
    private String tenKieu;
    @OneToMany(mappedBy = "kieuThanhVien")
    @JsonIgnoreProperties(value = "kieuThanhVien")
    List<PhatTus> phatTu;

}
