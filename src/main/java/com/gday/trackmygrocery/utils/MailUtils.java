package com.gday.trackmygrocery.utils;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;
import java.util.List;
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
}
