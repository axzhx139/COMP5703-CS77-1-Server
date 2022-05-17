package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.TrackmygroceryApplication;
import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.utils.PictureUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrackmygroceryApplication.class)
@WebAppConfiguration
class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private ItemMapper itemMapper;

    private PictureUtils pictureUtils=PictureUtils.getInstance();


    @Test
    void getItemByUser() {
        assertEquals(itemMapper.selectItemByUserId(117), itemService.getItemByUser(117));
    }

    @Test
    void getItemById() {
        assertEquals(itemMapper.selectItemById(310), itemService.getItemById(310));
    }

    @Test
    void deleteItemById() {
        assertEquals(0, itemService.deleteItemById(33));
    }

    @Test
    void insertItem() {
        Item item = new Item();
        itemService.insertItem(item);
    }

    @Test
    void updateItem() {
        Item item = new Item();
        item.setItemId(210);
        assertEquals(-1, itemService.insertItem(item));
    }

    @Test
    void updateStatus() {
        assertEquals(-2, itemService.updateStatus("consumed",1));
    }

    @Test
    void getStatusByItemId() {
        assertEquals(null, itemService.getStatusByItemId(210));
    }

    @Test
    void updateAllRemindDateByUserId() {
        assertEquals(1, itemService.updateAllRemindDateByUserId(1,2));
    }

    @Test
    void getItemByRemindDate() {
        itemService.getItemByRemindDate(1, new Date());
    }

    @Test
    void getPictureById() {
        assertEquals(itemMapper.getPictureById(304), itemService.getPictureById(304));
    }

    @Test
    void getInStockItemById() {
        assertEquals(1, itemService.getInStockItemById(1).size());

    }

//    @Test
//    void getPotentialList() {
//        assertEquals(1, itemService.getPotentialList(1).size());
//    }


}