package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.utils.PictureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Item> getItemByUser(int id) {
        return itemMapper.selectItemByUserId(id);
    }

    //sortType  1:过期时间, 2:类别, 3:名称, 4:默认,
    //history   -1:all, -2:expire, -3:consume
    @Override
    public List<Item> getItemByUserAndType(int id, int sortType) {
        List<Item> res;
        if (sortType > 0) {
            res = itemMapper.selectInStockItemByUserId(id);
            switch (sortType) {
                case 1:
                    return res.stream().sorted(Comparator.comparing(Item::getExpDate)).collect(Collectors.toList());
                case 2:
                    return res.stream().sorted(Comparator.comparing(Item::getCategory)).collect(Collectors.toList());
                case 3:
                    return res.stream().sorted(Comparator.comparing(Item::getName)).collect(Collectors.toList());
                case 4:
                    return res;
                default:
                    return null;
            }
        } else {
            switch (sortType) {
                case -1:
                    System.out.println(id);
                    return itemMapper.selectHistoryItemByUserId(id).stream().sorted(Comparator.comparing(Item::getAddDate).reversed()).collect(Collectors.toList());
                case -2:
                    return itemMapper.selectExpiredItemByUserId(id).stream().sorted(Comparator.comparing(Item::getExpDate).reversed()).collect(Collectors.toList());
                case -3:
                    return itemMapper.selectConsumedItemByUserId(id).stream().sorted(Comparator.comparing(Item::getConDate).reversed()).collect(Collectors.toList());
                default:
                    return null;
            }
        }
    }

    @Override
    public Item getItemById(int id) {
        return itemMapper.selectItemById(id);
    }

    @Override
    public int deleteItemById(int id) {
        return itemMapper.deleteItemById(id);
    }

    @Override
    public int insertItem(Item item) {
        try{
            Date systemDate = new Date();
            if (item.getExpDate().before(systemDate)) {
                //添加的是过期商品
                //itemMapper.insertExpiredItem(item);
                return -2;
            } else {
                item.setUnread(true);
                itemMapper.insertItem(item);
            }
            return item.getItemId();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public int updateItem(Item item) {
        try{
            //如果update发现提醒日期改变，重置unread
            Date oldRemindTime = itemMapper.selectRemindTimeByItemId(item.getItemId());
            if (oldRemindTime != null && !oldRemindTime.equals(item.getRemindTime())) {
                item.setUnread(true);
                logger.info("updateItem---Item's remind time has been reset, So change the unread status to TRUE.");
            }
            return itemMapper.updateItem(item);
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public int updateStatus(String newStatus, int itemId) {
        try{
            return itemMapper.updateStatus(newStatus,itemId);
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public String getStatusByItemId(int itemId) {
        try{
            return itemMapper.selectStatusByItemId(itemId);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public int updateAllRemindDateByUserId(int id, int day) {
        List<Item> items = itemMapper.selectItemByUserId(id);
        if(items.size()==0) return -1;
        for(Item item : items){
            if(item.getStatus().equals("instock")){
                Date date = item.getExpDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, -day); // 负数为提前几天，正数为推迟几天
                Date newDate =calendar.getTime();
                itemMapper.updateRemindDate(item.getItemId(), newDate);
            }
        }
        return 1;
    }

    @Override
    public List<Item> getItemByRemindDate(int id, Date date) {
        List<Item> resultItems = new ArrayList<>();
        List<Item> items = itemMapper.selectItemByRemindDate(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for(Item item : items){
            Date itemDate = item.getRemindTime();
//            System.out.println(df.format(itemDate) + df.format(date));
            if(df.format(itemDate).equals(df.format(date))) {
                resultItems.add(item);
            }
        }
        return resultItems;
    }



    @Override
    public String getPictureById(int id) {
        return itemMapper.getPictureById(id);
    }

    @Override
    public List<Item> getInStockItemById(int id) {
        return itemMapper.selectInStockItemById(id);
    }

    @Override
    public List<Item> getPotentialList(int id) {
        return itemMapper.selectPotentialList(id);
    }

    @Override
    public int updatePictureUrlToDatabase(int id, String url) {
        return itemMapper.storePictureUrlByItemId(id,url);
    }

    @Override
    @Scheduled(cron ="0 0 0 * * ?")
    public void expireItem() {
        Date date = new Date();
        int res = itemMapper.expireItem(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        logger.info("expireItem---Today is " + dateFormat.format(date) + ". Server set the expire food expired!\n" +
                "expireItem---There are " + res + " items exipred today!");
    }

    @Override
    @Scheduled(cron ="0 0 0 * * ?")
    public void readNotification() {
        Date date = new Date();
        int res = itemMapper.readNotification(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        logger.info("readNotification---Today is " + dateFormat.format(date) + ". Server set the food notifications to read!\n" +
                "readNotification---There are " + res + " notifications readed today!");
    }

}
