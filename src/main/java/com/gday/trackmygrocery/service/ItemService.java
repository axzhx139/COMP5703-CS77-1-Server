package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.Item;
import org.springframework.web.multipart.MultipartFile;

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

    int updatePicture(int id, MultipartFile multipartFile);

    String getPictureById(int id);

    List<Item> getInStockItemById(int id);

    List<Item> getPotentialList(int id);

    int uploadPic(int id, String s);
}
