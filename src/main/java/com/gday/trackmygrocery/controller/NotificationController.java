package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.ItemNotificationResult;
import com.gday.trackmygrocery.dao.pojo.Notification;
import com.gday.trackmygrocery.service.NotificationService;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    final Logger logger=LoggerFactory.getLogger(getClass());
    final LogUtils logUtils= LogUtils.getInstance();

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/user/{id}")
    public List<Notification> getNotificationByUserId(@PathVariable("id") int id) {
        logger.info("getNotificationByUserId<<<(id :int): "+id);
        List<Notification>res=notificationService.getNotificationByUserId(id);
        logger.info("getNotificationByUserId>>>"+logUtils.printListAsLog(res));
        return res;
    }

    @GetMapping("/get/{id}")
    public ItemNotificationResult getNotificationByUser(@PathVariable("id") int id) {
        logger.info("getNotificationByUser<<<(id :int): "+id);
        ItemNotificationResult res = notificationService.getNotificationByUser(id);
        logger.info("getNotificationByUser>>>"+logUtils.printObjAsLog(res));
        return res;
    }

    // -1:already readed, 0: itemId not exist, 1: success
    @PostMapping("/post/{itemId}")
    public Integer setNotificationStatus(@PathVariable("itemId") int itemId) {
        logger.info("setNotificationStatus<<<(itemId :int): " + itemId);
        Integer res = notificationService.setNotificationStatus(itemId);
        logger.info("setNotificationStatus>>>"+(res));
        return res;
    }



}