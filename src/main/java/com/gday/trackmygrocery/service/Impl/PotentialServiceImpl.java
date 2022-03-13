package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Potential;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.mapper.PotentialMapper;
import com.gday.trackmygrocery.service.PotentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotentialServiceImpl implements PotentialService {

    @Autowired
    private PotentialMapper potentialMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Potential> getPotentialList(int id) {
        return potentialMapper.selectListById(id);
    }

    @Override
    public int deleteById(int id) {
        return potentialMapper.deleteById(id);
    }

    @Override
    public int updateListByUserId(int id) {
        potentialMapper.deleteByUserId(id);
        List<Item> items = itemMapper.selectPotentialList(id);
        for(Item item : items){
            potentialMapper.insertItemAsPotential(item);
        }
        return 1;
    }

    @Override
    public int updateStatusById(String status, int id) {
        return potentialMapper.updateStatusById(status,id);
    }

    @Override
    public int addItem(int itemId, int userId) {
        Item item = itemMapper.selectItemById(itemId);
        potentialMapper.insertItemAsPotential(item);
        return 1;
    }

    @Override
    public int removeItem(int itemId, int userId) {
        return potentialMapper.removePotentialItem(itemId, userId);
    }

    @Override
    public boolean checkPotential(Item item) {
        Potential potential = potentialMapper.selectPotential(item);
        return null != potential;
    }


}
