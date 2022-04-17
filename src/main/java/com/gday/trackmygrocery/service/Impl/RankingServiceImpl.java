//
//  RankingMapper.java
//  COMP5703-CS77-1-Server
//
//  Created by hexin zhang on 2022/4/15.
//

package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.RankingObject;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.dao.pojo.UserRanking;
import com.gday.trackmygrocery.mapper.RankingMapper;
import com.gday.trackmygrocery.service.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RankingServiceImpl implements RankingService {

    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RankingMapper rankingMapper;

    @Override
    public List<User> getTopTenUsers(String address) {
        List<User> topTenUsers = null;
        if (address != null && !address.equals("null")) {
            topTenUsers = rankingMapper.getTopTenUsersWithAddress(address);
        } else {
            topTenUsers = rankingMapper.getTopTenUsers();
        }
        return topTenUsers;
    }

    @Override
    public RankingObject getPreviousOne(int id, String address) {
        List<UserRanking> userRankings = null;
        if (address == null || address.equals("null")) {
            userRankings = rankingMapper.getUserRankings();
        } else {
            userRankings = rankingMapper.getUserRankingsWithAddress(address);
        }
        int userRankingDays = rankingMapper.getUserRankingDays(id);
        int userRanking = getRanking(userRankingDays, userRankings);
        RankingObject rankingObject = new RankingObject();
        rankingObject.setUserID(id);
        rankingObject.setUserRankingDays(userRankingDays);
        rankingObject.setUserRanking(userRanking);
        if (userRanking == 1) {
            rankingObject.setPreviousRanking(-1);
            rankingObject.setPreviousRankingDays(-1);
            rankingObject.setPreviousUserID(-1);
        } else {
            int previousRankingDays = userRankings.get(userRanking - 2).getUserRankingDays();
            int previousUserID = userRankings.get(userRanking - 2).getUserID();
            int previousRanking = getRanking(previousRankingDays, userRankings);
            rankingObject.setPreviousRanking(previousRanking);
            rankingObject.setPreviousRankingDays(previousRankingDays);
            rankingObject.setPreviousUserID(previousUserID);
        }
        return rankingObject;
    }

    private int getRanking(int userRankingDays, List<UserRanking> userRankings) {
        int ranking = 1;
        for(int i = 0; i < userRankings.size(); i++) {
            int days = userRankings.get(i).getUserRankingDays();
            if (days > userRankingDays) {
                ranking++;
            } else {
                break;
            }
        }
        return ranking;
    }


    @Override
    @Scheduled(cron ="0 0 1 * * ?")
    public void setUserRanking() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date startDate = c.getTime();
        List<Integer> qualifiedUserIDs = rankingMapper.selectQualifiedUserIDs(startDate);
        if (qualifiedUserIDs == null || qualifiedUserIDs.size() == 0) {
            logger.info("setUserRanking---There is no qualified users");
            return;
        }
        List<Integer> allIDs = rankingMapper.selectAllIDs();
        List<Integer> failedIDs = allIDs.stream().filter(item -> !qualifiedUserIDs.contains(item)).collect(Collectors.toList());
        for (int id : qualifiedUserIDs) {
            if (rankingMapper.hadWasteItem(id, startDate) > 0) {
                //have waste
                failedIDs.add(id);
                continue;
            }

            if (rankingMapper.alreadyQualified(id) == -1) {
                // new qualified
                if (rankingMapper.setNewQualifiedUser(id) != 1) {
                    logger.error("setUserRanking---When updating User " + id + "occur an error, sql failed!");
                }
            } else {
                // keep increasing
                if (rankingMapper.increasingQualifiedUser(id) != 1) {
                    logger.error("setUserRanking---When increasing User " + id + "occur an error, sql failed!");
                }
            }
        }
        for (int id : failedIDs) {
            if (rankingMapper.resetRankingDays(id) != 1) {
                logger.error("setUserRanking---When reset User " + id + "occur an error, sql failed!");
            }
        }
    }
}



























