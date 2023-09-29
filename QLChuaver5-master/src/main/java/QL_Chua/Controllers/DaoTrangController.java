package QL_Chua.Controllers;

import QL_Chua.Models.DaoTrangs;
import QL_Chua.Services.IDaoTrang;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/api")
public class DaoTrangController {
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); }

    }).create();

    @Autowired
    private IDaoTrang iDaoTrang;

    @PostMapping(value = "themdaotrang")
    public String themDaoTrang(@RequestBody String daoTrang) {
        DaoTrangs daoTrangs = gson.fromJson(daoTrang, DaoTrangs.class);
        return iDaoTrang.themDaoTrang(daoTrangs);
    }

    @PutMapping(value = "suadaotrang")
    public String suaDaoTrang(@RequestBody String daoTrang) {
        DaoTrangs daoTrangs = gson.fromJson(daoTrang, DaoTrangs.class);
        return iDaoTrang.suaDaoTrang(daoTrangs);
    }

    @GetMapping(value = "timkiemdaotrang")
    public Page<DaoTrangs> timKiemDanhSachDaoTrang(@RequestParam String noiToChuc,
                                                   @RequestParam Integer soThanhVien,
                                                   @RequestParam Integer phatTu,
                                                   @RequestParam LocalDateTime thoiGianToChuc,
                                                   @RequestParam String noiDung,
                                                   @RequestParam Integer page,
                                                   @RequestParam Integer pageSize) {
        return iDaoTrang.timKiemDaoTrang(noiToChuc, soThanhVien, phatTu, thoiGianToChuc, noiDung, page, pageSize);
    }

    @GetMapping(value = "phantrangdanhsach")
//    @PreAuthorize("hasRole('TT')")
    public Page<DaoTrangs> phanTrangDanhSach(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return iDaoTrang.phanTrangDanhSach(page, pageSize);
    }

    @DeleteMapping(value = "xoadaotrang")
    public String xoaDaoTrang(@RequestParam Integer daoTrangId) {
        return iDaoTrang.xoaDaoTrang(daoTrangId);
    }

    @GetMapping(value = "phantrangtheongaytochuc")
    public Page<DaoTrangs> phanTheoNgayToChuc(@RequestParam LocalDateTime ngayToChuc,
                                              @RequestParam Integer page,
                                              @RequestParam Integer pageSize) {
        return iDaoTrang.phanTrangTheoNgayToChuc(ngayToChuc, page, pageSize);
    }
}
