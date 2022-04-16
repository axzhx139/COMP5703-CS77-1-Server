package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.Item;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;
import java.util.List;

public interface ItemService {
    List<Item> getItemByUser(int id);

    List<Item> getItemByUserAndType(int id, int sortType);

    Item getItemById(int id);

    int deleteItemById(int id);

    int insertItem(Item item);

    int updateItem(Item item);

    int updateStatus(String newStatus, int itemId);

    String getStatusByItemId(int itemId);

    int updateAllRemindDateByUserId(int id, int day);

    List<Item> getItemByRemindDate(int id, Date date);

    String getPictureById(int id);

    List<Item> getInStockItemById(int id);

    List<Item> getPotentialList(int id);

    int updatePictureUrlToDatabase(int id, String s);

    @Scheduled(cron ="0 0 0 * * ?")
    void expireItem();

    @Scheduled(cron ="0 0 0 * * ?")
    void readNotification();
}
