package QL_Chua.Services;

import QL_Chua.DTO.DoiMatKhauDTO;
import QL_Chua.DTO.PhatTuDTO;
import QL_Chua.Models.Chuas;
import QL_Chua.Models.KieuThanhViens;
import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.ChuaRepository;
import QL_Chua.Repository.KieuThanhVienRepository;
import QL_Chua.Repository.PhatTuRepository;
import QL_Chua.Validator.Validate;
import QL_Chua.payload.request.DoiMatKhauPTReq;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhatTuServices implements IPhatTu {
    @Autowired
    private PhatTuRepository phatturepo;
    @Autowired
    private ChuaRepository chuarepo;
    @Autowired
    private KieuThanhVienRepository kieuthanhvienrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public String themPhatTu(PhatTus phatTus) {
        Optional<Chuas> chuas = chuarepo.findById(phatTus.getChua().getChuaId());
        Optional<KieuThanhViens> kieuThanhViens = kieuthanhvienrepo.findById(phatTus.getKieuThanhVien().getKieuThanhVienId());
        if(chuas.isEmpty() || kieuThanhViens.isEmpty()) return  "them that bai";
        if(Validate.validator(phatTus) == null  && !phatturepo.existsPhatTusByEmail(phatTus.getEmail())
                                                && !phatturepo.existsPhatTusBySoDienThoai(phatTus.getSoDienThoai())) {
            phatturepo.save(phatTus);
            return "them thanh cong";
        }
        return "them that bai";
    }


    @Override
    public List<PhatTuDTO> timKiemDanhSachPhatTuTheoTen(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
       return phatturepo.timKiemTheoTen(name, pageable).stream().map(phattu -> modelMapper.map(phattu, PhatTuDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PhatTuDTO> timKiemDanhSachPhatTuTheoNamSinh(int namSinh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return phatturepo.timKiemTheoNamSinh(namSinh, pageable).stream().map(phattu -> modelMapper.map(phattu, PhatTuDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PhatTuDTO> timKiemTheoPhapDanh(String phapDanh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return phatturepo.timKiemTheoPhapDanh(phapDanh, pageable).stream().map(phattu -> modelMapper.map(phattu, PhatTuDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PhatTuDTO> timkiemDanhSach(String name, String phapDanh, Integer gioiTinh,
                                         Boolean daHoanTuc, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return phatturepo.timKiemPhatTu(name, phapDanh, gioiTinh, daHoanTuc, pageable).stream().map(phattu -> modelMapper.map(phattu, PhatTuDTO.class)).collect(Collectors.toList());
    }

    @Override
    public String setPhatTus(PhatTus phatTus) {
        Optional<PhatTus> phatTus1 = phatturepo.findById(phatTus.getPhatTuId());
        if(phatTus1.isEmpty()) return "khong tim thay id phat tu";
        PhatTus phatTu = phatTus1.get();
        phatTu.setHo(phatTus.getHo());
        phatTu.setTenDem(phatTus.getTenDem());
        phatTu.setTen(phatTus.getTen());
        phatTu.setPhapDanh(phatTus.getPhapDanh());
        phatTu.setAnhChup(phatTus.getAnhChup());
        phatTu.setSoDienThoai(phatTus.getSoDienThoai());
        phatTu.setNgaySinh(phatTus.getNgaySinh());
        phatTu.setNgayXuatGia(phatTus.getNgayXuatGia());
        phatTu.setDaHoanTuc(phatTus.getDaHoanTuc());
        phatTu.setNgayHoanTuc(phatTus.getNgayHoanTuc());
        phatTu.setGioiTinh(phatTus.getGioiTinh());
        phatTu.setNgayCapNhap(phatTus.getNgayCapNhap());
        if(phatTus.getChua() == null) {
            phatTu.setChua(phatTus1.get().getChua());
        } else {
            if (chuarepo.findById(phatTus.getChua().getChuaId()).isEmpty()) {
                return "chua khong ton tai";
            }
            phatTu.setChua(phatTus.getChua());
        }
        if(phatTus.getKieuThanhVien() == null) {
            phatTu.setKieuThanhVien(phatTus1.get().getKieuThanhVien());
        } else {
            if (phatturepo.findById(phatTus.getKieuThanhVien().getKieuThanhVienId()).isEmpty()) {
                return "kieu thanh vien khong ton tai";
            }
            phatTu.setKieuThanhVien(phatTus.getKieuThanhVien());
        }
        if(Validate.validator(phatTu) == null) {
            phatturepo.save(phatTu);
            return "cap nhap thanh cong";
        }
        return "cap nhap that bai";
    }

    @Override
    public String doiMatKhau(DoiMatKhauPTReq dmk) {
        PhatTus phatTus = phatturepo.findByEmail(dmk.getEmail()).get();
        if (phatTus == null) {
            return "email chua ton tai";
        }
        if (!encoder.matches(dmk.getOldPassword(), phatTus.getPassword())) {
            return "mat khau nhap khong dung";
        }
        phatTus.setPassword(encoder.encode(dmk.getNewPassword()));
        phatturepo.save(phatTus);
        return "doi mat khau thanh cong";
    }


}
