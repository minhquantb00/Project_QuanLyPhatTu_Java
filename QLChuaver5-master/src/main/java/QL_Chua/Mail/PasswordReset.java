package QL_Chua.Mail;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordReset {
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String token;
}
