package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Notification;
import com.gday.trackmygrocery.service.NotificationService;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    final Logger logger=LoggerFactory.getLogger(getClass());
    final LogUtils logUtils= LogUtils.getInstance();

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/user/{id}")
    public List<Notification> getNotificationByUserId(@PathVariable("id") int id){
        logger.info("getNotificationByUserId<<<(id :int): "+id);
        List<Notification>res=notificationService.getNotificationByUserId(id);
        logger.info("getNotificationByUserId>>>"+logUtils.printListAsLog(res));
        return res;
    }

}