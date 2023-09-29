package QL_Chua.payload.request;

import QL_Chua.Models.DonDangKys;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class DonDangKyRequest {
    private List<Integer> listDon;
    private Integer nguoiXuLyId;
}
