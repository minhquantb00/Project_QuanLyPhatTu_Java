package QL_Chua.Mail;

import QL_Chua.Security.token.Token;
import com.google.gson.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class SendEmail {
    private final JavaMailSender javaMailSender;
//    private final SpringTemplateEngine springTemplateEngine;
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

    }).create();

    public SendEmail(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.javaMailSender = javaMailSender;
//        this.springTemplateEngine = springTemplateEngine;
    }

    public String sendEmail(Email email, Token token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//        Context context = new Context();
        try {
//            context.setVariables(email.getModel());
//            String html = springTemplateEngine.process("email/email-temple", context);
            mimeMessageHelper.setFrom(email.getFrom());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setText(email.getMessage() + token.getStoken());
            mimeMessageHelper.setSubject(email.getSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending email";
        }
        javaMailSender.send(mimeMessage);
        return "sending email success";
    }
}
