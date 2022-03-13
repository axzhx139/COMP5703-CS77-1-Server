package com.gday.trackmygrocery.mapper;

import com.gday.trackmygrocery.dao.pojo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Select("select * from mg_notification where u_id = #{id}")
    List<Notification> getNotificationByUserId(int id);
}