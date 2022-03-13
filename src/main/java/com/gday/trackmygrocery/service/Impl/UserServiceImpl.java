package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.mapper.UserMapper;
import com.gday.trackmygrocery.service.UserService;
import com.gday.trackmygrocery.vo.Profile;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public User getUserById(int id) {
        return userMapper.selectId(id);
    }

    @Override
    public int insertUser(User user) {
        try{
            userMapper.insertBean(user);
            return user.getUId();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userMapper.selectByEmail(email);
        user.setPwd(null);
        return user;
    }

    @Override
    public int logIn(LoginParam loginParam) {
        User user = userMapper.selectByEmail(loginParam.getEmail());
        if(user == null){
            return -1;
        }
        if(user.getPwd()!=null && user.getPwd().equals(loginParam.getPwd())){
            return user.getUId();
        }else{
            return -1;
        }
    }

    @Override
    public int insertSpecialUser(SpecialUser specialUser) {
        try{
            userMapper.insertSpecialUser(specialUser);
            return specialUser.getUId();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public int logInSpecial(String uuid) {
        try {
            SpecialUser specialUser = userMapper.selectByUUID(uuid);
            return specialUser.getUId();
        }catch(Exception e) {
            return -1;
        }
    }

    @Override
    public int resetPwdById(ResetPwdParam pwdParam) {
        String pwd = userMapper.selectPwdById(pwdParam.getId());
        if(!pwd.equals(pwdParam.getPrePwd())){
            return -1;
        }
        userMapper.updatePwd(pwdParam);
        return 1;
    }

    @Override
    public int getAlertStateById(int id) {
        return userMapper.selectAlertStateById(id);
    }

    @Override
    public int changeAlertStateById(int id) {
        return userMapper.updateAlertStateById(id);
    }

    @Override
    public Profile getUserProfile(int id) {
        Profile profile = new Profile();
        User user = userMapper.selectId(id);
        profile.setAddress(user.getAddress());
        profile.setGender(user.getGender());
        profile.setName(user.getName());
        List<Item> items = itemMapper.selectItemByUserId(id);
        int instockCount = 0;
        int consumeCount = 0;
        int expireCount = 0;
        for(Item item : items){
            if(item.getStatus().equals("instock")){
                instockCount++;
            }else if(item.getStatus().equals("consume")){
                consumeCount++;
            }else{
                expireCount++;
            }
        }
        profile.setConsumedItemCount(consumeCount);
        profile.setExpiredItemCount(expireCount);
        profile.setTotalItemCount(items.size());
        profile.setInstockItemCount(instockCount);
        return profile;
    }

    @Override
    public int updateProfile(ProfileParam profileParam) {
        return userMapper.updateProfile(profileParam);
    }

    @Override
    public int updateAvatar(String s, int id) {
        return userMapper.updateAvatar(s, id);
    }

    @Override
    public String getAvatar(int id) {
        return userMapper.selectAvatarById(id);
    }

}
