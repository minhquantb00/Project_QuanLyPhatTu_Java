package QL_Chua.DTO;

import QL_Chua.Models.DaoTrangs;
import QL_Chua.Models.PhatTus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PhatTuDaoTrangDTO {
    private Integer phatTuDaoTrangId;
    private Boolean daThamGia;
    private String lyDoKhongThamGia;
    @JsonIgnore
    @JsonIgnoreProperties(value = "phatTuDaoTrang")
    DaoTrangDTO daoTrang;
    @JsonIgnoreProperties(value = "phatTuDaoTrang")
    PhatTuDTO phatTu;
}
