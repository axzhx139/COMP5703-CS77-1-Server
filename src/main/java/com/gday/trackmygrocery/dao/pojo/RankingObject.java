package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

@Data
public class RankingObject extends UserRanking{
    private int userID;
    private int previousUserID;
    private int userRanking;
    private int previousRanking;
    private int userRankingDays;
    private int previousRankingDays;
    private String userName;
    private String previousUserName;
    private int daysGap;
}
