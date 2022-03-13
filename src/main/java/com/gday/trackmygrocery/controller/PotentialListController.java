package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Potential;
import com.gday.trackmygrocery.service.PotentialService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("potential")
public class PotentialListController {

    @Autowired
    private PotentialService potentialService;



    @GetMapping("/{id}")
    public List<Potential> getPotentialListByUserId(@PathVariable("id") int id){return potentialService.getPotentialList(id); }

    @GetMapping("/delete/{id}")
    @ApiOperation("delete the item from potential item list using potential id")
    public int deletePotentialItemById(@PathVariable("id") int id){return potentialService.deleteById(id);}

    @GetMapping("/update/{id}")
    public int updatePotentialListByUserId(@PathVariable("id") int id){return potentialService.updateListByUserId(id);}

    @GetMapping("/update/status/{status}/{id}")
    public int updatePotentialItemStatusById(@PathVariable("status") String status, @PathVariable("id") int id){
        return potentialService.updateStatusById(status,id);
    }

    @GetMapping("/add/{itemId}/{userId}")
    @ApiOperation("add the item to the user's potential list")
    public int addItemToPotential(@PathVariable("itemId")int itemId, @PathVariable("userId")int userId){
        return potentialService.addItem(itemId,userId);
    }

    @GetMapping("/remove/{itemId}/{userId}")
    @ApiOperation("remove the item from the potential list")
    public int removeItemFromPotential(@PathVariable("itemId")int itemId, @PathVariable("userId")int userId){
        return potentialService.removeItem(itemId,userId);
    }


}
