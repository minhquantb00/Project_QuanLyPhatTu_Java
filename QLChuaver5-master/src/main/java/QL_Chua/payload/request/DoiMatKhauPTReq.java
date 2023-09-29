package QL_Chua.payload.request;

import lombok.Data;

@Data
public class DoiMatKhauPTReq {
    private String email;
    private String oldPassword;
    private String newPassword;
}
