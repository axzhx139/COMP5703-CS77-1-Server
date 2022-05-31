package com.gday.trackmygrocery.mapper;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Potential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PotentialMapper {

    @Select("select * from mg_potential where u_id = #{id}")
    List<Potential> selectListById(int id);

    @Delete("delete from mg_potential where p_id = #{id}")
    int deleteById(int id);

    @Update("update mg_potential SET status=#{status} where p_id=#{id}")
    int updateStatusById(String status, int id);

    @Delete("delete from mg_potential where u_id = #{id}")
    void deleteByUserId(int id);

    @Insert("insert into mg_potential(u_id,item_id,name,picture,category) " +
            "values(#{uId},#{itemId}, #{name}, #{picture},#{category})")
//    @Options(useGeneratedKeys = true, keyProperty = "pId", keyColumn = "p_id")
    void insertItemAsPotential(Item item);

    @Delete("delete from mg_potential where item_id = #{itemId} and u_id = #{userId}")
    int removePotentialItem(int itemId, int userId);

    @Select("select * from mg_potential where item_id = #{itemId} and u_id = #{uId}")
    Potential selectPotential(Item item);

    @Delete("delete from mg_potential where u_id=#{userId}")
    int deletePotentialByUserId(int userId);

    @Select("select count(*) from mg_potential where item_id = #{itemId}")
    int checkContains(int itemId);
}
