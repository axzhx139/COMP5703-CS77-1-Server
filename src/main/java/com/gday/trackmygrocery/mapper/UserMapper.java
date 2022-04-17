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

     @Select("select count(*) from mg_user where email = #{email}")
     int checkEmailExist(String email);

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

     @Insert("insert into mg_user(email,verification_code,verification_code_status) values(#{email},#{code},0)")
     int insertInitUser(String email, String code);

     @Select("select count(*) from mg_user where email = #{email} and verification_code_status = 0")
     int checkEmailExistWithoutVerify(String email);

     @Update("update mg_user SET verification_code=#{code} where email = #{email}")
     int insertCodeOnly(String email, String code);

     @Select("select verification_code_status from mg_user where email = #{email}")
     int checkVerificationCodeStatus(String email);

     //add verification_code=null 03/26/2022
     @Update("update mg_user SET name=#{name}, gender=#{gender}, pwd=#{pwd}, uuid=#{uuid}, verification_code_status=1, verification_code=null where email = #{email}")
     int verifyAndInsertUser(User user);

     @Select("select verification_code from mg_user where email = #{email}")
     String getVerificationCode(String email);

     @Select("select u_id from mg_user where email= #{email}")
     int getUserID(String email);

     @Update("update mg_user SET verification_code_status= 0 where email = #{email}")
     int resetVerificationCodeStatus(String email);

     @Update("update mg_user SET verification_code= #{verificationCode} where email = #{email}")
     int setVerificationCode(String email,String verificationCode);

     @Update("update mg_user SET pwd= #{pwd},verification_code=null where email=#{email}")
     int changePassword(String email,String pwd);

     @Select("select name from mg_user where u_id = #{id}")
     String getUserName(int id);
}
