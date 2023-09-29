package QL_Chua.payload.request;

import QL_Chua.Models.Chuas;
import QL_Chua.Models.KieuThanhViens;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
@Data
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String ho;
    private String tenDem;
    private String ten;
    private String phapDanh;
    private byte[] anhChup;
    private String soDienThoai;
    private Integer gioiTinh;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private KieuThanhViens kieuThanhVien;
    private Chuas chua;
}
