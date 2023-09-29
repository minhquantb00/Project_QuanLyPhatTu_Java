package QL_Chua.Services;

import QL_Chua.Models.DaoTrangs;
import QL_Chua.Models.DonDangKys;
import QL_Chua.Models.PhatTuDaoTrangs;
import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.DaoTrangRepository;
import QL_Chua.Repository.DonDangKyRepository;
import QL_Chua.Repository.PhatTuDaoTrangRepository;
import QL_Chua.Repository.PhatTuRepository;
import QL_Chua.payload.request.DonDangKyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonDangKyServices implements IDonDangKy {
    @Autowired
    private PhatTuRepository phatturepo;
    @Autowired
    private DonDangKyRepository donDkRepo;
    @Autowired
    private DaoTrangRepository daotrangrepo;
    @Autowired
    private PhatTuDaoTrangRepository phatTuDaoTrangrepo;

    @Override
    public String themDonDangKy(DonDangKys donDangKys) {
        Optional<PhatTus> phatTus = phatturepo.findById(donDangKys.getPhatTuId().getPhatTuId());
        Optional<DaoTrangs> daoTrangs = daotrangrepo.findById(donDangKys.getDaoTrang().getDaoTrangId());
        if (phatTus.isPresent() && daoTrangs.isPresent() && !daoTrangs.get().getDaKetThuc()) {
            DonDangKys ddk = new DonDangKys();
            ddk.setPhatTuId(donDangKys.getPhatTuId());
            ddk.setTrangThaiDon(0);
            ddk.setNgayGuiDon(LocalDateTime.now());
            ddk.setDaoTrang(donDangKys.getDaoTrang());
            List<DonDangKys> donDangKysList = donDkRepo.timKiemDonTheoPhatTu(donDangKys.getPhatTuId().getPhatTuId());
            if (donDangKysList.size() != 0) {
                for (DonDangKys donDangKys1 : donDangKysList) {
                    if (donDangKys1.getNgayGuiDon().equals(ddk.getNgayGuiDon())) {
                        return "bi trung ngay gui don";
                    }
                }
            }
            donDkRepo.save(ddk);
            return "them don dang ky thanh cong";
        }
        return "chua ton tai phat tu";
    }

    @Override
    public String duyetDonDangKy(DonDangKyRequest dondky) {
        for (Integer integer : dondky.getListDon()) {
            DonDangKys donDangKys = donDkRepo.findById(integer).get();
            if (donDangKys == null) {
                return "don dang ky chua ton tai";
            }
            donDangKys.setNgayXuLy(LocalDateTime.now());
            donDangKys.setTrangThaiDon(1);
            donDangKys.setNguoiXuLy(dondky.getNguoiXuLyId());
            donDkRepo.save(donDangKys);
            PhatTuDaoTrangs phatTuDaoTrangs = new PhatTuDaoTrangs();
            phatTuDaoTrangs.setDaoTrang(donDangKys.getDaoTrang());
            phatTuDaoTrangs.setPhatTu(donDangKys.getPhatTuId());
            phatTuDaoTrangs.setDaThamGia(true);
            phatTuDaoTrangs.setLyDoKhongThamGia("khong");
            phatTuDaoTrangrepo.save(phatTuDaoTrangs);
        }
        return "duyet thanh cong";
    }
}
