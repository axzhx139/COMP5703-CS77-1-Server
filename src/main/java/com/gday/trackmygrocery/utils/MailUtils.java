package com.gday.trackmygrocery.utils;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;
import java.util.List;
import java.util.Random;

@Data
public class MailUtils {

    private String[] to;    /*收件人列表*/
    private String subject; /*邮件主题*/
    private String text;    /*邮件内容*/
    private List<FileSystemResource> file;    /*附件*/

    private String contentId;

    public static MailUtils getVerificationMail(String email, String code) {
        return new MailUtils(email, code);
    }



    public MailUtils(String email, String code) {
        this.to = new String[] {email};
        this.subject = "email verification";
        this.text = "<h1>code: " + code + "</h1>";
    }

    public static String generateVerificationCode() {
        //生成6位验证码
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(r.nextInt(9));
        }
        return sb.toString();
    }

}
