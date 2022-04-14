package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ItemNotification implements Serializable {
    private String name;
    private Integer itemId;

    private String addDate;
    private Integer expireDays;
    private Boolean unread;

    public ItemNotification (Item item) {
        this.name = item.getName();
        this.itemId = item.getItemId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        this.addDate = dateFormat.format(item.getAddDate());
        this.unread = item.getUnread();
        Date date = new Date();
        this.expireDays = (int) ((item.getExpDate().getTime() - date.getTime()) / (1000*3600*24));
    }
}
