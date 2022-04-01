package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.service.UserService;
import com.gday.trackmygrocery.utils.LogUtils;
import com.gday.trackmygrocery.utils.QiNiuUtils;
import com.gday.trackmygrocery.vo.Profile;
import com.gday.trackmygrocery.vo.params.ChangeCodeParam;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();

    @Autowired
    private QiNiuUtils qiNiuUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/get/id/{id}")
    @ApiOperation("Get User information using user id")
    public User currentUser(@PathVariable("id") int id) {
        logger.info("currentUser<<<(id: int): " + id);
        User res = userService.getUserById(id);
        logger.info("currentUser>>>" + logUtils.printObjAsLog(res));
        return res;
    }

    @GetMapping("/get/email/{email}")
    @ApiOperation("Get User information using user email")
    public User getUserByEmail(@PathVariable("email") String email) {
        logger.info("getUserByEmail<<<(email: String): " + email);
        User res = userService.getUserByEmail(email);
        logger.info("getUserByEmail>>>" + logUtils.printObjAsLog(res));
        return res;
    }

    /*
    用户不存在：-1
    已验证：-2
    验证码错误：-3
    数据插入时错误：-4
    正确时：用户uid
     */
    @PostMapping("/register/normal")
    @ApiOperation("Insert new User except avatar")
    public int insertUser(@RequestBody User user) {
        logger.info("insertUser<<<(user: User): " + logUtils.printObjAsLog(user));
        int res = userService.verifyUser(user);
        logger.info("insertUser---\n" + "用户不存在：-1\n" +
                "    已验证：-2\n" +
                "    验证码错误：-3\n" +
                "    数据插入时错误：-4\n" +
                "    正确时：用户uid");
        logger.info("insertUser>>>" + res);
        return res;
    }


    @PostMapping("/register/sendVerifyCode")
    @ApiOperation("Insert new User except avatar")
    public int sendVerifyCode(@RequestBody User user) {
        // 0:邮箱存在, -1:数据库插入失败, -2:邮件发送异常
        logger.info("sendVerifyCode<<<(email: String): " + user.getEmail());
        int res = userService.sendVerifyCode(user.getEmail());
        logger.info("sendVerifyCode---\n" + "email不存在：-1\n" +
                "    寄信失败：-2\n" +
                "    更新资料库失败：-1\n" +
                "    已认证：0\n" +
                "    正确时：1");
        logger.info("sendVerifyCode>>>" + res);
        return res;
    }


    @PostMapping("/register/sendChangeCode")
    @ApiOperation("SendChangeCode") //foget password change code
    public int sendChangeCode(@RequestBody User user) {
        //equal email not equal return 0
        logger.info("sendChangeCode<<<(email: String): " + user.getEmail());
        int res = userService.sendChageCode(user.getEmail());
        logger.info("sendChangeCode---\n" + "email不存在：-1\n" +
                "    寄信失败：-2\n" +
                "    更新资料库失败：-3\n" +
                "    寄信成功：1");
        logger.info("sendChangeCode>>>" + res);
        return res;
    }


    @PostMapping("register/changePasswordbyVcode")
    @ApiOperation("Change password")
    public int changePasswordByVcode(@RequestBody ChangeCodeParam changeCodeParam) {
        logger.info("changePasswordbyVcode<<<(changeCodeParam: ChangeCodeParam): "+logUtils.printObjAsLog(changeCodeParam));
        int res=userService.changePasswordByVcode(changeCodeParam);
        logger.info("changePasswordByVcode---\n" + "email不存在：-1\n" +
                "    Vcode 错误：-2\n" +
                "    更新资料库失败：-3\n" +
                "    修改成功：1");
        logger.info("changePasswordbyVcode>>>"+res);
        return res;
    }

    @PostMapping("/login/normal")
    @ApiOperation("Normal Login using email and pwd")
    public int logIn(@RequestBody LoginParam loginParam) {
        logger.info("logIn<<<(loginParam: LoginParam): " + logUtils.printObjAsLog(loginParam));
        int res = userService.logIn(loginParam);
        logger.info("logIn>>>" + res);
        return res;
    }

    @PostMapping("/register/special")
    @ApiOperation("insert special User using uuid")
    public int insertSpecialUser(@RequestBody SpecialUser specialUser) {
        logger.info("insertSpecialUser<<<(specialUser: SpecialUser): " + logUtils.printObjAsLog(specialUser));
        int res = userService.insertSpecialUser(specialUser);
        logger.info("insertSpecialUser>>>" + res);
        return res;
    }

    @GetMapping("/login/special/{uuid}")
    @ApiOperation("Special Login using uuid and pwd")
    public int logInSpecial(@PathVariable("uuid") String uuid) {
        logger.info("logInSpecial<<<(uuid: String): " + uuid);
        int res = userService.logInSpecial(uuid);
        logger.info("logInSpecial>>>" + res);
        return res;
    }

    @PostMapping("/reset/pwd")
    @ApiOperation("Reset pwd, return -1 if wrong old pwd is given")
    public int resetPwd(@RequestBody ResetPwdParam pwdParam) {
        logger.info("resetPwd<<<(pwdParam: ResetPwdParam): " + logUtils.printObjAsLog(pwdParam));
        int res = userService.resetPwdById(pwdParam);
        logger.info("resetPwd>>>" + res);
        return res;
    }

    @GetMapping("/alert/{id}")
    @ApiOperation("Get alert state using user id")
    public int getAlertState(@PathVariable("id") int id) {
        logger.info("getAlertState<<<(id: int): " + id);
        int res = userService.getAlertStateById(id);
        logger.info("getAlertState>>>" + res);
        return res;
    }

    @GetMapping("/alert/change/{id}")
    @ApiOperation("Change alert state automatically")
    public int changeAlertState(@PathVariable("id") int id) {
        logger.info("changeAlertState<<<(id: int): " + id);
        int res = userService.changeAlertStateById(id);
        logger.info("changeAlertState>>>" + res);
        return res;
    }

    @GetMapping("/profile/{id}")
    @ApiOperation("Get User Profile information except avatar")
    public Profile getUserProfile(@PathVariable("id") int id) {
        logger.info("getUserProfile<<<(id: int): " + id);
        Profile res = userService.getUserProfile(id);
        logger.info("getUserProfile>>>" + logUtils.printObjAsLog(res));
        return res;
    }

    @PostMapping("/profile/update")
    @ApiOperation("Update user profile except avatar")
    public int updateProfile(@RequestBody ProfileParam profileParam) {
        logger.info("updateProfile<<<(profileParam: ProfileParam): " + logUtils.printObjAsLog(profileParam));
        int res = userService.updateProfile(profileParam);
        logger.info("updateProfile>>>" + res);
        return res;
    }

    @PostMapping("/avatar/update/{id}")
    @ApiOperation("Update user's avatar")
    public int updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable("id") int id) {
        logger.info("updateAvatar<<<(file: MultipartFile): " + logUtils.printObjAsLog(file) + "(id: int): " + id);
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiNiuUtils.upload(file, filename);
        if (upload && userService.updateAvatar(QiNiuUtils.url + filename, id) == 1) {
            logger.info("updateAvatar>>>" + 1);
            return 1;
        }
        logger.info("updateAvatar>>>" + -1);
        return -1;
    }

    @GetMapping(value = "/avatar/{id}")
    @ApiOperation("Get user's avatar")
    public String getAvatar(@PathVariable("id") int id) {
        logger.info("getAvatar<<<(id: int): " + id);
        String res = userService.getAvatar(id);
        logger.info("getAvatar>>>" + res);
        return res;
    }
}