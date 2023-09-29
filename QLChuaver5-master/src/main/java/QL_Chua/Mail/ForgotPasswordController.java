package QL_Chua.Mail;

import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.PhatTuRepository;
import QL_Chua.Security.token.Token;
import QL_Chua.Security.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/forgotpassword")
public class ForgotPasswordController {
    @Autowired
    private PhatTuRepository phatturepo;
    @Autowired
    private TokenRepository tokenrepo;
    @Autowired
    private SendEmail sendEmail;

    @PostMapping
    public String processPasswordForgot(@RequestBody PasswordForgot passwordForgot) {
        PhatTus phatTus = phatturepo.findByEmail(passwordForgot.getEmail()).get();
        if (phatTus == null) {
            return "khong tim thay dia chi email";
        }
        Token token = new Token();
        token.setPhatTu(phatTus);
        token.setStoken(UUID.randomUUID().toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        tokenrepo.save(token);
        if (token == null) {
            return "forgot-password";
        }

        Email email = new Email();
        email.setTo(phatTus.getEmail());
        email.setFrom("no-reply@gmail.com");
        email.setSubject("Facebook request");
        email.setMessage("Ma otp la:");


//        Map<String, Object> model = new HashMap<>();
//        model.put("token", token);
//        model.put("phatTu", phatTus);
//        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        model.put("resetUrl", url + "/reset-password?token=" + token.getStoken());
//        email.setModel(model);
        sendEmail.sendEmail(email, token);
        return "redict:/forgot-password";
    }
}
