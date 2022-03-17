package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.PotentialService;
import com.gday.trackmygrocery.utils.QiNiuUtils;
import com.gday.trackmygrocery.vo.ItemVo;
import com.gday.trackmygrocery.vo.params.RemindParam;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("item")
public class ItemController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();

    @Autowired
    private QiNiuUtils qiNiuUtils;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PotentialService potentialService;

    @GetMapping("/user/{id}")
    @ApiOperation("Get items using user id !! added isPotential attribute")
    public List<ItemVo> getItemsByUserId(@PathVariable("id") int id) {
        logger.info("getItemsByUserId<<<(id: int): " + id);
        List<Item> itemByUser = itemService.getItemByUser(id);
        List<ItemVo> itemVos = new ArrayList<>();
        for (Item item : itemByUser) {
            ItemVo itemVo = new ItemVo();
            BeanUtils.copyProperties(item, itemVo);
            if (potentialService.checkPotential(item)) {
                itemVo.setPotential(true);
            }
            itemVos.add(itemVo);
        }
        logger.info("getItemsByUserId>>>" + logUtils.printListAsLog(itemVos));
        return itemVos;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get items using item id")
    public Item getItemById(@PathVariable("id") int id) {
        logger.info("getItemById<<<(id: int): " + id);
        Item res = itemService.getItemById(id);
        logger.info("getItemById>>>" + logUtils.printObjAsLog(res));
        return res;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("Delete item using item id")
    public int deleteItemById(@PathVariable("id") int id) {
        logger.info("deleteItemById<<<(id: int): " + id);
        int res = itemService.deleteItemById(id);
        logger.info("deleteItemById>>>" + id);
        return res;
    }

    @PostMapping("/insert")
    @ApiOperation("Insert item, picture would be null")
    public int insertItem(@RequestBody Item item) {
        logger.info("insertItem<<<(item: Item): " + logUtils.printObjAsLog(item));
        int res = itemService.insertItem(item);
        logger.info("insertItem>>>" + res);
        return res;
    }

    @PostMapping("/update")
    @ApiOperation("Update item except picture")
    public int updateItem(@RequestBody Item item) {
        logger.info("updateItem<<<(item: Item): " + logUtils.printObjAsLog(item));
        int res = itemService.updateItem(item);
        logger.info("updateItem>>>" + res);
        return res;
    }

    @GetMapping("/update/status/{newStatus}/id/{itemId}")
    @ApiOperation("Update status using item id")
    public int updateStatus(@PathVariable String newStatus, @PathVariable int itemId) {
        logger.info("updateStatus<<<(newStatus: String): " + newStatus + "(itemId: int): " + itemId);
        int res = itemService.updateStatus(newStatus, itemId);
        logger.info("updateStatus>>>" + res);
        return res;
    }

    @GetMapping("/status/{itemId}")
    @ApiOperation("Get item status using item id")
    public String getStatusByItemId(@PathVariable("itemId") int itemId) {
        logger.info("getStatusByItemId<<<(itemId: int): " + itemId);
        String res = itemService.getStatusByItemId(itemId);
        logger.info("getStatusByItemId>>>" + res);
        return res;
    }

    @GetMapping("/update/all/{id}/{day}")
    @ApiOperation("Update all in stock items' remind date to {day} days before expire date using user id")
    public int updateAllRemindDate(@PathVariable("id") int id, @PathVariable("day") int day) {
        logger.info("updateAllRemindDate<<<(id: int): " + id + "(day: int): " + day);
        int res = itemService.updateAllRemindDateByUserId(id, day);
        logger.info("updateAllRemindDate>>>" + res);
        return res;
    }

    @PostMapping("/remind")
    @ApiOperation("Get all items on the given remind date")
    public List<Item> getItemByRemindDate(@RequestBody RemindParam remindParam) {
        logger.info("getItemByRemindDate<<<(remindParam: RemindParam): " + logUtils.printObjAsLog(remindParam));
        int id = remindParam.getUserId();
        Date date = remindParam.getRemindDate();
        List<Item> res = itemService.getItemByRemindDate(id, date);
        logger.info("getItemByRemindDate>>>" + logUtils.printListAsLog(res));
        return res;
    }

    @PostMapping("/update/picture")
    @ApiOperation("Update item's picture using item id")
    public int updatePictureById(@RequestParam("id") int id, @RequestParam("picture") MultipartFile file) {
        logger.info("updatePictureById<<<(id: int): " + id + "(file: MultipartFile): " + logUtils.printObjAsLog(file));
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiNiuUtils.upload(file, filename);
        if (upload && itemService.uploadPic(id, QiNiuUtils.url + filename) == 1) {
            logger.info("updatePictureById>>>" + 1);
            return 1;
        }
        logger.info("updatePictureById>>>" + -1);
        return -1;
    }

    @GetMapping(value = "/picture/{id}")
    @ApiOperation("Get item's picture using item id")
    public String getPictureById(@PathVariable("id") int id) {
        logger.info("getPictureById<<<(id: int): " + id);
        String res = itemService.getPictureById(id);
        logger.info("getPictureById>>>" + res);
        return res;
    }

    @GetMapping("/potential/{id}")
    public List<Item> getPotentialList(@PathVariable("id") int id) {
        logger.info("getPotentialList<<<(id: int): " + id);
        //System.out.println("不喝牛奶3");
        List<Item> res = itemService.getPotentialList(id);
        logger.info("getPotentialList>>>" + logUtils.printListAsLog(res));
        return res;
    }


}

