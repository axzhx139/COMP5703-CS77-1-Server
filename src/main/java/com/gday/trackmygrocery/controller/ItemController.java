package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.PotentialService;
import com.gday.trackmygrocery.utils.PictureUtils;
import com.gday.trackmygrocery.utils.QiNiuUtils;
import com.gday.trackmygrocery.vo.ItemVo;
import com.gday.trackmygrocery.vo.params.RemindParam;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();
    final PictureUtils pictureUtils = PictureUtils.getInstance();

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

    //sortType  1:过期时间, 2:类别, 3:名称, 4:默认,
    //history   -1:all, -2:expire, -3:consume
    @GetMapping("/user/{id}/{sortType}")
    @ApiOperation("Get items using user id !! added isPotential attribute")
    public List<ItemVo> getItemsByUserIdAndType(@PathVariable("id") int id, @PathVariable("sortType") int sortType) {
        logger.info("getItemsByUserIdAndType<<<(id: int): " + id + "(sortType: int): " + sortType);
        List<Item> itemByUser = itemService.getItemByUserAndType(id, sortType);
        List<ItemVo> itemVos = new ArrayList<>();
        for (Item item : itemByUser) {
            ItemVo itemVo = new ItemVo();
            BeanUtils.copyProperties(item, itemVo);
            if (potentialService.checkPotential(item)) {
                itemVo.setPotential(true);
            }
            itemVos.add(itemVo);
        }
        logger.info("getItemsByUserIdAndType>>>" + logUtils.printListAsLog(itemVos));
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
        logger.info("deleteItemById>>>" + res);
        return res;
    }

    @PostMapping("/insert")
    @ApiOperation("Insert item, picture would be null")
    public int insertItem(@RequestBody Item item) {
        logger.info("insertItem<<<(item: Item): " + logUtils.printObjAsLog(item));
        int res = itemService.insertItem(item);
        logger.info("insertItem>>>" + res);
        return res;//-1:Exception, -2:Adding expired item
    }

    @PostMapping("/update")
    @ApiOperation("Update item except picture")
    public int updateItem(@RequestBody Item item) {
        logger.info("updateItem<<<(item: Item): " + logUtils.printObjAsLog(item));
        logger.info("updateItem---\n" +
                "    数据插入成功：1\n" +
                "    数据插入失败：-1\n");
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
    public int updatePictureById(@RequestParam("item_id") int item_id, @RequestParam("picture") MultipartFile file) {
        logger.info("updatePictureById<<<(item_id: int): " + item_id + "(picture: MultipartFile): " + logUtils.printObjAsLog(file));
        String url = pictureUtils.updatePictureToServer("item", item_id, file);
        logger.info("updatePictureById---\n" +
                "    数据插入成功：1\n" +
                "    数据插入失败：-1\n");
        if (url != null) {
            int res = itemService.updatePictureUrlToDatabase(item_id, url);
            if (res == 1) {
                logger.info("updatePictureById>>>" + res);
                return 1;
            }
        }
        logger.info("updatePictureById>>>-1");
        return -1;
    }

    @ResponseBody
    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ApiOperation("Get item's picture using item id")
    public byte[] getPictureById(@PathVariable("id") int id) {
        logger.info("getPictureById<<<(id: int): " + id);
        String res = itemService.getPictureById(id);
        byte[] bytes;
        bytes = pictureUtils.getPictureFromServer("item",res);

        if (bytes != null) {
            logger.info("getPictureById>>>" + logUtils.printObjAsLog(bytes));
            return bytes;
        }
        logger.info("getPictureById>>>" + res);
        return null;
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

