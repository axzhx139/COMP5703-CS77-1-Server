package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Potential;

import java.util.List;

public interface PotentialService {

    List<Potential> getPotentialList(int id);

    int deleteById(int id);

    int updateListByUserId(int id);

    int updateStatusById(String status, int id);

    int addItem(int itemId, int userId);

    int removeItem(int itemId, int userId);

    boolean checkPotential(Item item);
}
