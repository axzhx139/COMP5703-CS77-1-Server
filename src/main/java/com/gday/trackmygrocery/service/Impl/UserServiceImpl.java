package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.mapper.PotentialMapper;
import com.gday.trackmygrocery.mapper.UserMapper;
import com.gday.trackmygrocery.service.UserService;
import com.gday.trackmygrocery.utils.EmailUtils;
import com.gday.trackmygrocery.utils.MailUtils;
import com.gday.trackmygrocery.utils.PictureUtils;
import com.gday.trackmygrocery.vo.Profile;
import com.gday.trackmygrocery.vo.params.ChangeCodeParam;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PotentialMapper potentialMapper;

    @Autowired
    private PictureUtils pictureUtils;

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public User getUserById(int id) {
        return userMapper.selectId(id);
    }

    @Override
    public int insertUser(User user) {
        try {
            userMapper.insertBean(user);
            return user.getUId();
        } catch (Exception e) {
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
        if (user == null) {
            return -1;
        }
        if (user.getPwd() != null && user.getPwd().equals(loginParam.getPwd())) {
            return user.getUId();
        } else {
            return -1;
        }
    }

    @Override
    public int insertSpecialUser(SpecialUser specialUser) {
        try {
            userMapper.insertSpecialUser(specialUser);
            return specialUser.getUId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int logInSpecial(String uuid) {
        try {
            SpecialUser specialUser = userMapper.selectByUUID(uuid);
            return specialUser.getUId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int resetPwdById(ResetPwdParam pwdParam) {
        String pwd = userMapper.selectPwdById(pwdParam.getId());
        if (!pwd.equals(pwdParam.getPrePwd())) {
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
        for (Item item : items) {
            if (item.getStatus().equals("instock")) {
                instockCount++;
            } else if (item.getStatus().equals("consume")) {
                consumeCount++;
            } else {
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
    public int updateUserAvatarUrlToDatabase(String s, int id) {
        return userMapper.updateAvatar(s, id);
    }

    @Override
    public String getAvatarUrl(int id) {
        return userMapper.selectAvatarById(id);
    }

    @Override
    public int sendVerifyCode(String email) {
        if (userMapper.checkEmailExist(email) == 0) {
            //邮箱不存在或未激活
            String code = MailUtils.generateVerificationCode();
            int intRes = userMapper.insertInitUser(email, code);
            if (intRes == 1) {
                MailUtils mail = MailUtils.getVerificationMail(email, code);
                return emailUtils.sendMailHtml(mail);
            } else {
                //数据库插入失败
                return -1;
            }
        } else {
            if (userMapper.checkEmailExistWithoutVerify(email) == 1) {
                String code = MailUtils.generateVerificationCode();
                int intRes = userMapper.insertCodeOnly(email, code);
                if (intRes == 1) {
                    MailUtils mail = MailUtils.getVerificationMail(email, code);
                    return emailUtils.sendMailHtml(mail);
                } else {
                    //数据库插入失败
                    return -1;
                }
            }
            return 0;
        }
    }

    @Override
    public int verifyUser(User user) {
        if (userMapper.checkEmailExist(user.getEmail()) == 1) {
            //用户存在
            if (userMapper.checkVerificationCodeStatus(user.getEmail()) == 0) {
                //未验证
                if (user.getVerificationCode() != null && user.getVerificationCode().equals(userMapper.getVerificationCode(user.getEmail()))) {
                    //验证码正确
                    if (userMapper.verifyAndInsertUser(user) == 1) {
                        return userMapper.getUserID(user.getEmail());
                    } else {
                        //插入过程出现错误
                        logger.error("verifyUser---用户验证时数据库插入错误，用户为" + user.getEmail());
                        return -4;
                    }
                } else {
                    //验证码错误
                    return -3;
                }
            } else {
                //已验证
                return -2;
            }
        } else {
            //用户不存在
            return -1;
        }
    }


    @Override
    public int sendChageCode(String email) {
        if (userMapper.checkEmailExist(email.trim()) == 1) {
            if (userMapper.checkVerificationCodeStatus(email) == 1) {
                String code = MailUtils.generateVerificationCode();
                int setVcode = userMapper.setVerificationCode(email, code);
                if (setVcode == 1) {
                    MailUtils mail = MailUtils.getVerificationMail(email, code);
                    int res = emailUtils.sendMailHtml(mail);
                    if (res == 1) {
                        //寄信成功
                        return 1;
                    } else {
                        //寄信失败
                        return -2;
                    }
                } else {
                    //更新资料库失败
                    return -3;
                }
            } else {
                //未验证
                return -4;
            }
        }
        //email 不存在
        return -1;
    }

    @Override
    public int changePasswordByVcode(ChangeCodeParam changeCodeParam) {
        String email = changeCodeParam.getEmail();
        String password = changeCodeParam.getPwd();
        String verification_code = changeCodeParam.getVerification_code();
        if (userMapper.checkEmailExist(email) == 1) {
            if (userMapper.getVerificationCode(email).equals(verification_code)) {
                if (userMapper.changePassword(email, password) == 1) {
                    //修改成功
                    return 1;
                } else {
                    //更新资料库失败
                    return -3;
                }
            } else {
                //Vcode 错误
                return -2;
            }
        } else {
            //email不存在
            return -1;
        }
    }

    @Override
    public int deleteUserAccount(int userId) {
        if (userId != 117&&userId!=115&&userId!=1&&userId!=2) {
//            String userAvatarPath = userMapper.selectAvatarById(userId);
//            int resDeleteUserAvatar = pictureUtils.deletePictureByPath(userAvatarPath);
//            List<String> pathList = itemMapper.selectPictureListByUserId(userId);
//            int[] resDeleteItemPictureArray = new int[pathList.size()];
//            for (int i = 0; i < pathList.size(); i++) {
//                resDeleteItemPictureArray[i] = pictureUtils.deletePictureByPath(pathList.get(i));
//            }
//            int resDeleteUserAccount = userMapper.deleteUserAccountByUserId(userId);
//            int resDeletePotential = potentialMapper.deletePotentialByUserId(userId);
//            int resDeleteItem = itemMapper.deleteItemByUserId(userId);
//
//            if (resDeleteUserAvatar > 0) {
//                for (int j : resDeleteItemPictureArray) {
//                    if (j == -1) {
//                        return -1;
//                    }
//                }
//                if (resDeleteItem > 0 || resDeletePotential > 0 || resDeleteUserAccount > 0) {
//                    return 1;
//                }
//            }
//            return -1;
            return 1;
        }

        return -2;
    }
}
