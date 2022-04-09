package com.gday.trackmygrocery.dao.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ItemNotificationResult implements Serializable {
    private Integer unreadNum;
    private Integer readedNum;
    private List<ItemNotification> itemNotificationList;
}
