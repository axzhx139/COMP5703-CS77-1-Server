package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Potential;
import com.gday.trackmygrocery.service.PotentialService;
import com.gday.trackmygrocery.utils.LogUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("potential")
public class PotentialListController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();

    @Autowired
    private PotentialService potentialService;

    @GetMapping("/{id}")
    synchronized public List<Potential> getPotentialListByUserId(@PathVariable("id") int id) {
        logger.info("getPotentialListByUserId<<<(id: int): " + id);
        List<Potential> res = potentialService.getPotentialList(id);
        logger.info("getPotentialListByUserId>>>" + logUtils.printListAsLog(res));
        return res;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("delete the item from potential item list using potential id")
    synchronized public int deletePotentialItemByPotentialId(@PathVariable("id") int id) {
        logger.info("deletePotentialItemById<<<(id: int): " + id);
        int res = potentialService.deleteByPotentialId(id);
        logger.info("deletePotentialItemById>>>" + res);
        return res;
    }

    @GetMapping("/deleteAll/{id}")
    synchronized public int deleteAllPotentialByUserId(@PathVariable("id") int id) {
        logger.info("updatePotentialListByUserId<<<(id: int): " + id);
        int res = potentialService.deleteAllPotentialByUserId(id);
        logger.info("updatePotentialListByUserId>>>" + res);
        return res;
    }

    @GetMapping("/update/status/{status}/{id}")
    synchronized public int updatePotentialItemStatusById(@PathVariable("status") String status, @PathVariable("id") int id) {
        logger.info("updatePotentialItemStatusById<<<(status: String): " + status + "(id: int): " + id);
        int res = potentialService.updateStatusById(status,id);
        logger.info("updatePotentialItemStatusById>>>" + res);
        return res;
    }

    @GetMapping("/add/{itemId}/{userId}")
    @ApiOperation("add the item to the user's potential list")
    synchronized public int addItemToPotential(@PathVariable("itemId")int itemId, @PathVariable("userId")int userId) {
        logger.info("addItemToPotential<<<(itemId: int): " + itemId + "(userId: int): " + userId);
        int res = potentialService.addItem(itemId,userId);
        logger.info("addItemToPotential>>>" + res);
        return res;
    }

    @GetMapping("/remove/{itemId}/{userId}")
    @ApiOperation("remove the item from the potential list")
    synchronized public int removeItemFromPotential(@PathVariable("itemId")int itemId, @PathVariable("userId")int userId) {
        logger.info("removeItemFromPotential<<<(itemId: int): " + itemId + "(userId: int): " + userId);
        int res = potentialService.removeItem(itemId,userId);
        logger.info("removeItemFromPotential>>>" + res);
        return res;
    }


}
