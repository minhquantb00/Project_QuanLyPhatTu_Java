package QL_Chua.DTO;

import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
@Data
public class DonDangKyDTO {
    private Integer donDangKyId;
    private Integer trangThaiDon;
    private LocalDate ngayGuiDon;
    private LocalDate ngayXuLy;
    @JsonIgnoreProperties(value = "donDangKyPhatTu")
    PhatTuDTO phatTuId;
    @JsonIgnore
    @JsonIgnoreProperties(value = "donDangKyNguoiXuLy")
    PhatTuDTO nguoiXuLyId;
}
