package com.gday.trackmygrocery.mapper;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.mysql.cj.jdbc.Blob;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.BlobTypeHandler;

import java.util.Date;
import java.util.List;

@Mapper
public interface ItemMapper {

    @Update("update mg_item SET unread = false where item_id = #{itemId}")
    int setNotificationStatus(int itemId);

    @Select("select unread from mg_item where item_id = #{itemId}")
    Boolean checkNotificationStatus(int itemId);

    @Select("select * from mg_item where u_id = #{id}")
    List<Item> selectItemByUserId(int id);

    @Select("select * from mg_item where u_id = #{id} and isConsumed=0")
    List<Item> selectInStockItemByUserId(int id);

    @Select("select count(*) from mg_item where u_id = #{id}")
    Integer selectItemCountByUserId(int id);

    @Select("select count(*) from mg_item where u_id = #{id} and isConsumed=0")
    Integer selectInStockItemCountByUserId(int id);

    @Select("select * from mg_item where u_id = #{id} and isConsumed!=0")
    List<Item> selectHistoryItemByUserId(int id);

    @Select("select * from mg_item where u_id = #{id} and isConsumed=1")
    List<Item> selectConsumedItemByUserId(int id);

    @Select("select * from mg_item where u_id = #{id} and isConsumed=-1")
    List<Item> selectExpiredItemByUserId(int id);

    @Select("select remind_Time from mg_item where item_Id = #{itemId}")
    Date selectRemindTimeByItemId(int itemId);

    @Select("select * from mg_item where item_id = #{id}")
    Item selectItemById(int id);

    @Delete("delete from mg_item where item_id = #{id}")
    int deleteItemById(int id);

    @Insert("insert into mg_item(name,add_date,con_date," +
            "exp_date, add_method, detail,status,category," +
            "remind_time,other_detail,u_id, isConsumed, quantity) values(#{name},#{addDate},#{conDate}" +
            ",#{expDate},#{addMethod},#{detail},#{status}," +
            "#{category},#{remindTime},#{otherDetail},#{uId}, 0, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId", keyColumn = "item_id")
    int insertItem(Item item);

    @Update("update mg_item SET name=#{name},add_date=#{addDate},con_date=#{conDate}" +
            ",exp_date=#{expDate},add_method=#{addMethod},detail=#{detail},status=#{status}," +
            "category=#{category},remind_time=#{remindTime},other_detail=#{otherDetail}, quantity=#{quantity} where" +
            " item_id=#{itemId}")
    int updateItem(Item item);

    @Update("update mg_item SET status=#{newStatus}, isConsumed = 1, con_date = #{date} where item_id=#{itemId}")
    int consumeItem(String newStatus, int itemId, String date);

    @Select("select status from mg_item where item_id=#{itemId}")
    String selectStatusByItemId(int itemId);

    @Update("update mg_item SET remind_time=#{newDate} where item_id=#{id}")
    int updateRemindDate(int id, Date newDate);

    @Select("select * from mg_item where u_id=#{id}")
    List<Item> selectItemByRemindDate(int id);

//    @Update("update mg_item SET picture = #{bytes} where item_id = #{id}")
//    int updatePicture(int id, byte[] bytes);

    @Select("select picture from mg_item where item_id = #{id}")
    String getPictureById(int id);

    @Select("select * from mg_item where u_id = #{id} and status = 'instock'")
    List<Item> selectInStockItemById(int id);

    @Select("select * from mg_item where u_id = #{id} and TO_DAYS(NOW()) - TO_DAYS(add_date) <= 5")
    List<Item> selectPotentialList(int id);

    @Update("update mg_item SET picture = #{s} where item_id = #{id}")
    int storePictureUrlByItemId(int id, String s);

    @Update("update mg_item SET status='expired', isConsumed = -1 where exp_Date < #{date} and isConsumed = 0")
    int expireItem(Date date);

    @Update("update mg_item SET unread = false where remind_Time < #{date}")
    int readNotification(Date date);

    @Select("select quantity from mg_item where item_id = #{itemId}")
    Integer checkQuantity(int itemId);

    @Update("update mg_item SET quantity = quantity - 1 where item_id = #{itemId}")
    int consumeOne(int itemId);

    @Select("select picture from mg_item where u_id=#{userId}")
    List<String> selectPictureListByUserId(int userId);

    @Delete("delete from mg_item where u_id = #{userId}")
    int deleteItemByUserId(int userId);
}