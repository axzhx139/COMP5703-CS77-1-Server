package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.ThirdPartyLoginParm;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.vo.Profile;
import com.gday.trackmygrocery.vo.params.ChangeCodeParam;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User getUserById(int id);

    int insertUser(User user);

    User getUserByEmail(String email);

    int logIn(LoginParam loginParam);

    int insertSpecialUser(SpecialUser specialUser);

    int logInSpecial(String uuid);

    int resetPwdById(ResetPwdParam pwdParam);

    int getAlertStateById(int id);

    int changeAlertStateById(int id);

    Profile getUserProfile(int id);

    int updateProfile(ProfileParam profileParam);

    int updateUserAvatarUrlToDatabase(String url, int u_id) ;

    String getAvatarUrl(int id);

    int sendVerifyCode(String email);

    int verifyUser(User user);

    int sendChageCode(String email);

    int changePasswordByVcode(ChangeCodeParam changeCodeParam);

    int deleteUserAccount(int userId);

    int logInThirdParty(ThirdPartyLoginParm thirdPartyLoginParm);
}
