package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.service.ItemService;
import com.mysql.cj.jdbc.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> getItemByUser(int id) {
        return itemMapper.selectItemByUserId(id);
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
            itemMapper.insertItem(item);
            return item.getItemId();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public int updateItem(Item item) {
        try{
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
    public int updatePicture(int id, MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] bytes = multipartFile.getBytes();
            return itemMapper.updatePicture(id,bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
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
    public int uploadPic(int id, String s) {
        return itemMapper.uploadPic(id,s);
    }
}
