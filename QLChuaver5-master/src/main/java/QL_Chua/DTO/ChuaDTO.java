package QL_Chua.DTO;

import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class ChuaDTO {
    private Integer chuaId;
    private String tenChua;
    private LocalDateTime ngayThanhLap;
    private String diaChi;
    private Integer truTri;
    private LocalDateTime capNhap;

    @JsonIgnoreProperties(value = "chua")
    List<PhatTuDTO> phatTu;
}
