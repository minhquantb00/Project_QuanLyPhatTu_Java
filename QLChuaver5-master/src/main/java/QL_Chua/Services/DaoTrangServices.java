package QL_Chua.Services;

import QL_Chua.Models.DaoTrangs;
import QL_Chua.Models.DonDangKys;
import QL_Chua.Models.PhatTuDaoTrangs;
import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.DaoTrangRepository;
import QL_Chua.Repository.DonDangKyRepository;
import QL_Chua.Repository.PhatTuDaoTrangRepository;
import QL_Chua.Repository.PhatTuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class DaoTrangServices implements IDaoTrang{
    @Autowired
    private PhatTuRepository phatturepo;
    @Autowired
    private DaoTrangRepository daotrangrepo;
    @Autowired
    private DonDangKyRepository dondangkyrepo;
    @Autowired
    private PhatTuDaoTrangRepository phattudaotrangrepo;

    @Override
    public String themDaoTrang(DaoTrangs daoTrangs) {
        Optional<PhatTus> phatTus = phatturepo.findById(daoTrangs.getPhatTu().getPhatTuId());
        if(phatTus.isPresent()) {
            if (daoTrangs.getThoiGianToChuc().compareTo(LocalDateTime.now()) < 0) {
                daoTrangs.setDaKetThuc(true);
            } else {
                daoTrangs.setDaKetThuc(false);
            }
            daotrangrepo.save(daoTrangs);
            return "them dao trang thanh cong";
        }
        return "khong ton tai phat tu";
    }

    @Override
    public String suaDaoTrang(DaoTrangs daoTrangs) {
        Optional<DaoTrangs> daoTrangs1 = daotrangrepo.findById(daoTrangs.getDaoTrangId());
        if (daoTrangs1.isPresent()) {
            DaoTrangs daoTrang = daoTrangs1.get();
            if (daoTrangs.getNoiToChuc() != null) {
                daoTrang.setNoiToChuc(daoTrangs.getNoiToChuc());
            } else {
                daoTrang.setNoiToChuc(daoTrangs1.get().getNoiToChuc());
            }
            if (daoTrangs.getNoiDung() != null) {
                daoTrang.setNoiDung(daoTrangs.getNoiDung());
            } else  {
                daoTrang.setNoiDung(daoTrangs1.get().getNoiDung());
            }
            if(daoTrangs.getThoiGianToChuc() != null) {
                daoTrang.setThoiGianToChuc(daoTrangs.getThoiGianToChuc());
                if (daoTrang.getThoiGianToChuc().compareTo(LocalDateTime.now()) < 0) {
                    daoTrang.setDaKetThuc(true);
                } else {
                    daoTrang.setDaKetThuc(false);
                }
            } else {
                daoTrang.setThoiGianToChuc(daoTrangs1.get().getThoiGianToChuc());
                daoTrang.setDaKetThuc(daoTrangs1.get().getDaKetThuc());
            }
            if(daoTrangs.getPhatTu() != null) {
                daoTrang.setPhatTu(daoTrangs.getPhatTu());
            } else {
                return "chua nhap phat tu";
            }
            daotrangrepo.save(daoTrang);
            return "sua dao trang thanh cong";
        }
        return "dao trang chua ton tai";
    }

    @Override
    public Page<DaoTrangs> timKiemDaoTrang(String noiToChuc, Integer soThanhVien, Integer phatTu, LocalDateTime thoiGianToChuc, String noiDung, Integer page, Integer pageSize) {
        Page<DaoTrangs> pageDaoTrang = daotrangrepo.timKiemDaoTrang(noiToChuc, soThanhVien, phatTu, thoiGianToChuc, noiDung, PageRequest.of(page, pageSize));

        List<DaoTrangs> daoTrangsList = pageDaoTrang.getContent();

        return new PageImpl<>(daoTrangsList, PageRequest.of(page, pageSize), pageDaoTrang.getTotalPages());
    }

    @Override
    public Page<DaoTrangs> phanTrangDanhSach(Integer page, Integer pageSize) {
        Page<DaoTrangs> daoTrangsPage = daotrangrepo.findAll(PageRequest.of(page, pageSize));
        List<DaoTrangs> daoTrangsList = daoTrangsPage.getContent();
        return new PageImpl<>(daoTrangsList, PageRequest.of(page, pageSize), daoTrangsPage.getTotalPages());
    }

    @Override
    public String xoaDaoTrang(Integer daoTrangId) {
        Optional<DaoTrangs> daoTrangs = daotrangrepo.findById(daoTrangId);
        DaoTrangs daoTrang = daoTrangs.get();
        if (daoTrangs.isPresent()) {
            for (DonDangKys ddk : daoTrang.getDonDangKy()) {
                dondangkyrepo.delete(ddk);
            }
            for (PhatTuDaoTrangs ptdt : daoTrang.getPhatTuDaoTrang()) {
                phattudaotrangrepo.delete(ptdt);
            }
            daotrangrepo.delete(daoTrang);
            return "xoa dao trang thanh cong";
        }
        return "dao trang khong ton tai";
    }

    @Override
    public Page<DaoTrangs> phanTrangTheoNgayToChuc(LocalDateTime ngayToChuc, Integer page, Integer pageSize) {
        Page<DaoTrangs> daoTrangsPage = daotrangrepo.phanTrangTheoNgayToChuc(ngayToChuc, PageRequest.of(page, pageSize));
        List<DaoTrangs> daoTrangsList = daoTrangsPage.getContent();
        return new PageImpl<>(daoTrangsList, PageRequest.of(page, pageSize), daoTrangsPage.getTotalPages());
    }

}
