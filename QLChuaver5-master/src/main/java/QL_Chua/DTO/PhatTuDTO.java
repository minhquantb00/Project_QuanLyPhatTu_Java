package QL_Chua.DTO;

import QL_Chua.Models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class PhatTuDTO {
    private Integer phatTuId;
    private String ho;
    private String tenDem;
    private String ten;
    private String phapDanh;
    private byte[] anhChup;
    private String soDienThoai;
    private String email;
    private LocalDateTime ngaySinh;
    private LocalDateTime ngayXuatGia;
    private byte daHoanTuc;
    private LocalDateTime ngayHoanTuc;
    private Integer gioiTinh;
    private LocalDateTime ngayCapNhap;
//    @JsonIgnore
    @JsonIgnoreProperties(value = "phatTu")
    ChuaDTO chua;
//    @JsonIgnore
    @JsonIgnoreProperties(value = "phatTu")
    KieuThanhVienDTO kieuThanhVien;
    @JsonIgnoreProperties(value = "phatTu")
    List<DaoTrangDTO> daoTrang;
    @JsonIgnoreProperties(value = "phatTu")
    List<PhatTuDaoTrangDTO> phatTuDaoTrang;
    @JsonIgnoreProperties(value = "phatTuId")
    List<DonDangKyDTO> donDangKyPhatTu;
    @JsonIgnoreProperties(value = "nguoiXuLyId")
    List<DonDangKyDTO> donDangKyNguoiXuLy;

}
