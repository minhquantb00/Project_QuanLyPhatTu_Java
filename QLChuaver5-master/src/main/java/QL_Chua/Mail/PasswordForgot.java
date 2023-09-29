package QL_Chua.Mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordForgot {
    @Email
    @NotEmpty
    private String email;
}
