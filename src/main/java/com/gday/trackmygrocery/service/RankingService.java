package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.RankingObject;
import com.gday.trackmygrocery.dao.pojo.User;

import java.util.List;

public interface RankingService {
    List<User> getTopTenUsers(String address);

    RankingObject getPerviousOne(int id, String address);

    void setUserRanking(String address);
}
