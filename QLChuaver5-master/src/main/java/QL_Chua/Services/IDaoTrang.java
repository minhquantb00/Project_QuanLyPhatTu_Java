package QL_Chua.Services;

import QL_Chua.Models.DaoTrangs;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IDaoTrang {
    public String themDaoTrang(DaoTrangs daoTrangs);
    public String suaDaoTrang(DaoTrangs daoTrangs);
    public Page<DaoTrangs> timKiemDaoTrang(String noiToChuc, Integer soThanhVien, Integer phatTu,
                                           LocalDateTime thoiGianToChuc, String noiDung, Integer page, Integer pageSize);

    Page<DaoTrangs> phanTrangDanhSach(Integer page, Integer pageSize);

    String xoaDaoTrang(Integer daoTrangId);

    Page<DaoTrangs> phanTrangTheoNgayToChuc(LocalDateTime ngayToChuc, Integer page, Integer pageSize);
}
