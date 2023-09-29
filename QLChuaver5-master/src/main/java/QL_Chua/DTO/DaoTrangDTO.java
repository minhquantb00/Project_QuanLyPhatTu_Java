package QL_Chua.DTO;

import QL_Chua.Models.PhatTuDaoTrangs;
import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class DaoTrangDTO {
    private Integer daoTrangId;
    private String noiToChuc;
    private Integer soThanhVienThamGia;
    private LocalDateTime thoiGianToChuc;
    private String noiDung;
    private Boolean daKetThuc;
    @JsonIgnore
    @JsonIgnoreProperties(value = "daoTrang")
    PhatTuDTO phatTu;
    @JsonIgnoreProperties(value = "daoTrang")
    List<PhatTuDaoTrangDTO> phatTuDaoTrang;
}
