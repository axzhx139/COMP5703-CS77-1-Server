package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.PotentialService;
import com.gday.trackmygrocery.utils.QiNiuUtils;
import com.gday.trackmygrocery.vo.ItemVo;
import com.gday.trackmygrocery.vo.params.RemindParam;
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

    @Autowired
    private QiNiuUtils qiNiuUtils;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PotentialService potentialService;

    @GetMapping("/user/{id}")
    @ApiOperation("Get items using user id !! added isPotential attribute")
    public List<ItemVo> getItemsByUserId(@PathVariable("id") int id){
        List<Item> itemByUser = itemService.getItemByUser(id);
        List<ItemVo> itemVos = new ArrayList<>();
        for(Item item : itemByUser){
            ItemVo itemVo = new ItemVo();
            BeanUtils.copyProperties(item,itemVo);
            if(potentialService.checkPotential(item)){
                itemVo.setPotential(true);
            }
            itemVos.add(itemVo);
        }
        return itemVos;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get items using item id")
    public Item getItemById(@PathVariable("id") int id){
        return itemService.getItemById(id);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("Delete item using item id")
    public int deleteItemById(@PathVariable("id") int id){
        return itemService.deleteItemById(id);
    }

    @PostMapping("/insert")
    @ApiOperation("Insert item, picture would be null")
    public int insertItem(@RequestBody Item item){
        return itemService.insertItem(item);
    }

    @PostMapping("/update")
    @ApiOperation("Update item except picture")
    public int updateItem(@RequestBody Item item){
        return itemService.updateItem(item);
    }
    
    @GetMapping("/update/status/{newStatus}/id/{itemId}")
    @ApiOperation("Update status using item id")
    public int updateStatus(@PathVariable String newStatus,@PathVariable int itemId){return itemService.updateStatus(newStatus,itemId);}

    @GetMapping("/status/{itemId}")
    @ApiOperation("Get item status using item id")
    public String getStatusByItemId(@PathVariable("itemId") int itemId){return itemService.getStatusByItemId(itemId);}

    @GetMapping("/update/all/{id}/{day}")
    @ApiOperation("Update all in stock items' remind date to {day} days before expire date using user id")
    public int updateAllRemindDate(@PathVariable("id")int id, @PathVariable("day") int day){
        return itemService.updateAllRemindDateByUserId(id,day);
    }

    @PostMapping("/remind")
    @ApiOperation("Get all items on the given remind date")
    public List<Item> getItemByRemindDate(@RequestBody RemindParam remindParam){
        int id = remindParam.getUserId();
        Date date = remindParam.getRemindDate();
        return itemService.getItemByRemindDate(id,date);
    }

    @PostMapping("/update/picture")
    @ApiOperation("Update item's picture using item id")
    public int updatePictureById(@RequestParam("id") int id, @RequestParam("picture") MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiNiuUtils.upload(file, filename);
        if(upload && itemService.uploadPic(id, QiNiuUtils.url + filename)==1){
            return 1;
        }
        return -1;
    }

    @GetMapping(value = "/picture/{id}")
    @ApiOperation("Get item's picture using item id")
    public String getPictureById(@PathVariable("id") int id){
        return itemService.getPictureById(id);
    }

    @GetMapping("/potential/{id}")
    public List<Item> getPotentialList(@PathVariable("id") int id){
        //System.out.println("不喝牛奶3");
        return itemService.getPotentialList(id);
    }


}

