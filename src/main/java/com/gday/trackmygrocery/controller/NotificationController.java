package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Notification;
import com.gday.trackmygrocery.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/user/{id}")
    public List<Notification> getNotificationByUserId(@PathVariable("id") int id){return notificationService.getNotificationByUserId(id); }

}