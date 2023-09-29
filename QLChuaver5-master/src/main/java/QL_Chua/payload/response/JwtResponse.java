package QL_Chua.payload.response;

import QL_Chua.Models.PhatTus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private PhatTus phatTus;

    public JwtResponse(String accessToken, PhatTus phatTus) {
        this.token = accessToken;
        this.phatTus = phatTus;
    }


}
