package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.ItemNotification;
import com.gday.trackmygrocery.dao.pojo.ItemNotificationResult;
import com.gday.trackmygrocery.dao.pojo.Notification;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.mapper.NotificationMapper;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private ItemMapper itemMapper;

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Notification> getNotificationByUserId(int id) {
        return notificationMapper.getNotificationByUserId(id);
    }

    @Override
    public ItemNotificationResult getNotificationByUser(int id) {
        ItemNotificationResult itemNotificationResult = new ItemNotificationResult();
        List<Item> items = itemMapper.selectItemByUserId(id);
        List<ItemNotification> itemNotificationList = new ArrayList<>();
        int unread = 0;
        int read = 0;
        for (Item item : items) {
            ItemNotification itemNotification = new ItemNotification(item);
            itemNotificationList.add(itemNotification);
            if (item.getUnread()) {
                unread++;
            } else {
                read++;
            }
        }
        if (unread + read != items.size()) {
            logger.error("getNotificationByUser---Some Item do not have valid unread field, please check!");
            return null;
        }
        itemNotificationResult.setReadedNum(read);
        itemNotificationResult.setUnreadNum(unread);
        itemNotificationResult.setItemNotificationList(itemNotificationList);
        return itemNotificationResult;
    }

    @Override
    public Integer setNotificationStatus(int itemId) {
        Boolean unread = itemMapper.checkNotificationStatus(itemId);
        if (unread == null) {
            return 0;   // not exist
        }
        if (!unread) {
            return -1;  // already readed
        } else {
            return itemMapper.setNotificationStatus(itemId);
        }
    }

}