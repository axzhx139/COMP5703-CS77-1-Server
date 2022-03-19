package com.gday.trackmygrocery.vo;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;
import java.util.List;
@Data
public class Mail {

    private String[] to;    /*收件人列表*/
    private String subject; /*邮件主题*/
    private String text;    /*邮件内容*/
    private List<FileSystemResource> file;    /*附件*/

    private String contentId;

    public static Mail getMail() {
        return new Mail();
    }

}
