package QL_Chua.Controllers;

import QL_Chua.Models.DonDangKys;
import QL_Chua.Services.DonDangKyServices;
import QL_Chua.payload.request.DonDangKyRequest;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class DonDangKyController {
    @Autowired
    private DonDangKyServices donDangKyServices;
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); }

    }).create();
    @PostMapping(value = "themdondangky")
    public String themDonDangKy(@RequestBody String donDangKy) {
        DonDangKys donDangKys = gson.fromJson(donDangKy, DonDangKys.class);
        return donDangKyServices.themDonDangKy(donDangKys);
    }

    @PutMapping(value = "duyetdon")
    public String duyetDonDangKy(@RequestBody String duyetDon) {
        DonDangKyRequest donDangKyRequest = gson.fromJson(duyetDon, DonDangKyRequest.class);
        return donDangKyServices.duyetDonDangKy(donDangKyRequest);
    }
}
