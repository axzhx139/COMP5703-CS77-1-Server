package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.ItemNotificationResult;
import com.gday.trackmygrocery.dao.pojo.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationByUserId(int id);
    ItemNotificationResult getNotificationByUser(int id);
    Integer setNotificationStatus(int itemId);
}