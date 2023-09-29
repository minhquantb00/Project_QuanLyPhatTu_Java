package QL_Chua.Controllers;

import QL_Chua.DTO.DoiMatKhauDTO;
import QL_Chua.DTO.PhatTuDTO;
import QL_Chua.Models.PhatTus;
import QL_Chua.Services.IPhatTu;
import QL_Chua.payload.request.DoiMatKhauPTReq;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PhatTuController {
    @Autowired
    private IPhatTu iPhatTu;

    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); }

    }).create();
    @GetMapping(value = "timkiemtheotenphattu")
//    @PreAuthorize("hasRole('PT') or hasRole('TT')")
    public List<PhatTuDTO> timKiemTheoTenPhatTu(@RequestParam String name,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return iPhatTu.timKiemDanhSachPhatTuTheoTen(name, page, size);
    }

    @GetMapping(value = "timkiemdanhsachtheonamsinh")
    public List<PhatTuDTO> timKiemTheoNamSinh(    @RequestParam int namSinh,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return iPhatTu.timKiemDanhSachPhatTuTheoNamSinh(namSinh, page, size);
    }
    @GetMapping(value = "timkiemdanhsachtheophapdanh")
    public List<PhatTuDTO> timKiemTheoPhapDanh(   @RequestParam String phapDanh,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return iPhatTu.timKiemTheoPhapDanh(phapDanh, page, size);
    }

    @GetMapping(value = "timkiemphattu")
    public List<PhatTuDTO> timKiemPhatTu( @RequestParam(required=false) String name,
                                        @RequestParam(required=false) String phapDanh,
                                          @RequestParam(required=false) Integer gioiTinh,
                                        @RequestParam(required=false) Boolean daHoanTuc,
                                        @RequestParam int page,
                                        @RequestParam int size
    ) {
        return iPhatTu.timkiemDanhSach(name, phapDanh, gioiTinh, daHoanTuc, page, size);
    }


//    sua thong tin phat tu
    @PutMapping(value = "suathongtinphattu")
    public String suaThongTin(@RequestBody String phatTu) {
        PhatTus phatTus = gson.fromJson(phatTu, PhatTus.class);
        return iPhatTu.setPhatTus(phatTus);
    }

    @PutMapping(value = "doinmatkhau")
    public String doiPass(@RequestBody String doiMatKhauPTReq) {
        DoiMatKhauPTReq doiMatKhauPTReq1 =gson.fromJson(doiMatKhauPTReq, DoiMatKhauPTReq.class);
        return iPhatTu.doiMatKhau(doiMatKhauPTReq1);
    }

}
