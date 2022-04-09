package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItemNotification implements Serializable {
    private String name;
    private Integer itemId;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date addDate;
    private Integer expireDays;
    private Boolean unread;

    public ItemNotification (Item item) {
        this.name = item.getName();
        this.itemId = item.getItemId();
        this.addDate = item.getAddDate();
        this.unread = item.getUnread();
        Date date = new Date();
        this.expireDays = (int) ((item.getExpDate().getTime() - date.getTime()) / (1000*3600*24));
    }
}
