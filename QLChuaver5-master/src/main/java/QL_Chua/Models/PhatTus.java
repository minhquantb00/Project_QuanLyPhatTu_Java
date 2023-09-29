package QL_Chua.Models;

import QL_Chua.Security.token.Token;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "phattu")
@Data
public class PhatTus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phattuid")
    private Integer phatTuId;
    @Column(name = "ho")
    private String ho;
    @Column(name = "tendem")
    private String tenDem;
    @Column(name = "ten")
    private String ten;
    @Column(name = "phapdanh")
    private String phapDanh;
    @Column(name = "anhchup")
    private byte[] anhChup;
    @Column(name = "sodienthoai")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "so dien thoai khong hop le")
    private String soDienThoai;
    @Column(name = "email")
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]" + "+\\.)+[\\w-]{2,4}$", message = "email nhap khong hop le")
    private String email;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "ngaysinh", columnDefinition = "datetime(6)")
    private LocalDateTime ngaySinh;
    @Column(name = "ngayxuatgia", columnDefinition = "datetime(6)")
    private LocalDateTime ngayXuatGia;
    @Column(name = "dahoantuc")
    private Boolean daHoanTuc;
    @Column(name = "ngayhoantuc", columnDefinition = "datetime(6)")
    private LocalDateTime ngayHoanTuc;
    @Column(name = "gioitinh")
    private Integer gioiTinh;
    @Column(name = "ngaycapnhap", columnDefinition = "datetime(6)")
    private LocalDateTime ngayCapNhap;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chuaid")
    @JsonIgnoreProperties(value = "phatTu")
    Chuas chua;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kieuthanhvienid")
    @JsonIgnoreProperties(value = "phatTu")
    KieuThanhViens kieuThanhVien;

    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = "phatTu")
    List<DaoTrangs> daoTrang;

    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = "phatTu")
    List<PhatTuDaoTrangs> phatTuDaoTrang;

    @OneToMany(mappedBy = "phatTuId")
    @JsonIgnoreProperties(value = "phatTuId")
    List<DonDangKys> donDangKyPhatTu;

    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = "phatTu")
    List<Token> token;

    @OneToMany(mappedBy = "phatTu")
    @JsonIgnoreProperties(value = "phatTu")
    List<OtpEmail> otpEmail;
}