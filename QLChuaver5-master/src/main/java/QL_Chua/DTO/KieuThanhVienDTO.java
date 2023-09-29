package QL_Chua.DTO;

import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
public class KieuThanhVienDTO {
    private Integer kieuThanhVienId;
    private String code;
    private String tenKieu;
    @JsonIgnoreProperties(value = "kieuThanhVien")
    List<PhatTuDTO> phatTu;
}
