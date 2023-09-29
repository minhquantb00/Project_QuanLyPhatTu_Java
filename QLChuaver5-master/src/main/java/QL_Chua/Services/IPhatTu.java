package QL_Chua.Services;

import QL_Chua.DTO.DoiMatKhauDTO;
import QL_Chua.DTO.PhatTuDTO;
import QL_Chua.Models.PhatTus;
import QL_Chua.payload.request.DoiMatKhauPTReq;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPhatTu {
    public String themPhatTu(PhatTus phatTus);
    public List<PhatTuDTO> timKiemDanhSachPhatTuTheoTen(String name, int page, int size);
    public List<PhatTuDTO> timKiemDanhSachPhatTuTheoNamSinh(int namSinh, int page, int size);
    List<PhatTuDTO> timKiemTheoPhapDanh(String phapDanh, int page, int size);
    List<PhatTuDTO> timkiemDanhSach(String name, String phapDanh, Integer gioiTinh,
                                  Boolean daHoanTuc, int page, int size);

    String setPhatTus(PhatTus phatTus);
    String doiMatKhau(DoiMatKhauPTReq dmk);
}
