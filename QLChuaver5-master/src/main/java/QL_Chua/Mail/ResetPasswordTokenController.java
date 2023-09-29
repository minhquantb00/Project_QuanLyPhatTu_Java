package QL_Chua.Mail;

import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.PhatTuRepository;
import QL_Chua.Security.token.Token;
import QL_Chua.Security.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/resetpassword")
public class ResetPasswordTokenController {
    @Autowired
    private TokenRepository tokenrepo;
    @Autowired
    private PhatTuRepository phatturepo;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public String resetPassword(@RequestBody PasswordReset passwordReset) {
        Token token = tokenrepo.findByStoken(passwordReset.getToken()).get();
        if (token == null) {
            return "token khong dung";
        } else if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "ma xac thuc het han";
        } else {
            PhatTus phatTus = token.getPhatTu();
            phatTus.setPassword(encoder.encode(passwordReset.getPassword()));
            if (encoder.matches(passwordReset.getConfirmPassword(), phatTus.getPassword())) {
                phatturepo.save(phatTus);
                return "reset thanh cong";
            }
        }
        return "reset that bai";

    }

}
