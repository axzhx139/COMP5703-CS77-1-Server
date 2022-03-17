package com.gday.trackmygrocery.mapper;

import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

//     @Results({
//             @Result(property = "uId", column = "u_id"),
//     })

     @Select("select * from mg_user where u_id = #{id}")
     User selectId(int id);

     @Select("select * from mg_user where email = #{email}")
     User selectByEmail(String email);

     @Insert("insert into mg_user(name,email,pwd,gender) values(#{name},#{email},#{pwd},#{gender})")
     @Options(useGeneratedKeys=true, keyProperty="uId", keyColumn="u_id")
     void insertBean(User user);

     @Select("select pwd from mg_user where email = #{email}")
     String selectPwdByEmail(String email);

     @Insert("insert into mg_user(name,email,gender,uuid) values(#{name},#{email},#{gender},#{uuid})")
     @Options(useGeneratedKeys=true, keyProperty="uId", keyColumn="u_id")
     void insertSpecialUser(SpecialUser specialUser);

     @Select("select * from mg_user where uuid = #{uuid}")
     SpecialUser selectByUUID(String uuid);

     @Select("select pwd from mg_user where u_id = #{id}")
     String selectPwdById(int id);

     @Update("update mg_user SET pwd=#{newPwd} where u_id=#{id}")
     void updatePwd(ResetPwdParam pwdParam);

     @Select("select alert from mg_user where u_id = #{id}")
     int selectAlertStateById(int id);

     @Update("update mg_user SET alert = CASE alert WHEN 0 THEN 1 WHEN 1 THEN 0 END WHERE u_id=#{id}")
     int updateAlertStateById(int id);

     @Update("update mg_user SET address=#{address}, birthday=#{birthday}, gender=#{gender}, name=#{name} where u_id=#{id}")
     int updateProfile(ProfileParam profileParam);

     @Update("update mg_user SET avatar=#{s} where u_id=#{id}")
     int updateAvatar(String s, int id);

     @Select("select avatar from mg_user where u_id = #{id}")
     String selectAvatarById(int id);
}