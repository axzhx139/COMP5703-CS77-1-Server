package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

@Data
public class RankingObject extends UserRanking{
    private int userID;
    private int perviousUserID;
    private int userRanking;
    private int perviousRanking;
    private int userRankingDays;
    private int perviousRankingDays;
}
