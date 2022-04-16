package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.RankingObject;
import com.gday.trackmygrocery.dao.pojo.User;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface RankingService {
    List<User> getTopTenUsers(String address);

    RankingObject getPreviousOne(int id, String address);

    @Scheduled(cron ="0 0 0 * * ?")
    void setUserRanking();
}
