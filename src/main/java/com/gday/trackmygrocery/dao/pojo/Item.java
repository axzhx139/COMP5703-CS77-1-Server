package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
public class Item implements Serializable {

    private Integer itemId;
    private String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date addDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date conDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date expDate;
    private String addMethod;
    private String detail;
    private String status;
    private String category;
    private String otherDetail;

    private String picture;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date remindTime;

    private Integer uId;
}
