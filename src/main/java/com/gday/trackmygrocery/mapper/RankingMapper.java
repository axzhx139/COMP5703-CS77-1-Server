package com.gday.trackmygrocery.mapper;

import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.dao.pojo.UserRanking;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface RankingMapper {

    @Select("SELECT U.u_id, count(*) FROM mygrocery.mg_item I " +
            "join mygrocery.mg_user U on U.u_id = I.u_id " +
            "where I.add_date > #{startDate} and I.isConsumed != -1 " +
            "group by U.u_id " +
            "having count(*) >= 10")
    List<Integer> selectQualifiedUserIDs(Date startDate);

    @Select("Select u_id from mg_user")
    List<Integer> selectAllIDs();

    @Select("select ranking_days from mg_user where u_id = #{id}")
    Integer alreadyQualified(int id);


    @Update("update mg_user SET ranking_days = 1 where u_id = #{id}")
    Integer setNewQualifiedUser(int id);

    @Update("update mg_user SET ranking_days = 1 + ranking_days where u_id = #{id}")
    Integer increasingQualifiedUser(int id);

    @Update("update mg_user SET ranking_days = -1 where u_id = #{id}")
    Integer resetRankingDays(int id);

    @Select("select * from mg_user " +
            "order by ranking_days DESC " +
            "limit 10")
    List<User> getTopTenUsers();

    @Select("select * from mg_user " +
            "where address like concat('%', '${address}', '%') " +
            "order by ranking_days DESC " +
            "limit 10")
    List<User> getTopTenUsersWithAddress(String address);

    @Select("Select count(*) from mg_item where u_id = #{id} and isConsumed = -1 and add_date > #{startDate}")
    Integer hadWasteItem(int id, Date startDate);

    @Select("select ranking_days from mg_user where u_id = #{id}")
    int getUserRankingDays(int id);

    @Results(id="UserRankingMap",value = {
            @Result(column = "u_id",property = "userID"),
            @Result(column = "ranking_days",property = "userRankingDays")})
    @Select("select u_id, ranking_days from mg_user order by ranking_days DESC")
    List<UserRanking> getUserRankings();

    @Results(id="UserRankingMap2",value = {
            @Result(column = "u_id",property = "userID"),
            @Result(column = "ranking_days",property = "userRankingDays")})
    @Select("select u_id, ranking_days from mg_user where address like concat('%', '${address}', '%') order by ranking_days DESC")
    List<UserRanking> getUserRankingsWithAddress(String address);
}
