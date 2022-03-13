package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Notification;
import com.gday.trackmygrocery.mapper.NotificationMapper;
import com.gday.trackmygrocery.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotificationByUserId(int id) {
        return notificationMapper.getNotificationByUserId(id);
    }
}