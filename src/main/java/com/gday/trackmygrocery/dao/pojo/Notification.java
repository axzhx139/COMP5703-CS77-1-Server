package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Notification {
    private Integer nId;
    private Integer uId;
    private String status;
    private String title;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date time;
    private String content;
}