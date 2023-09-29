package QL_Chua.Mail;

import lombok.Data;

import java.util.Map;
import java.util.Random;
@Data
public class Email {
    private String from;
    private String to;
    private String cc;
    private String subject;
    private String message;
    private Map<String, Object> model;

//    public String generateRandomString(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder sb = new StringBuilder(length);
//
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(characters.length());
//            char randomChar = characters.charAt(index);
//            sb.append(randomChar);
//        }
//
//        return sb.toString();
//    }
}
