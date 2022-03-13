package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.service.UserService;
import com.gday.trackmygrocery.utils.QiNiuUtils;
import com.gday.trackmygrocery.vo.Profile;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private QiNiuUtils qiNiuUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/get/id/{id}")
    @ApiOperation("Get User information using user id")
    public User currentUser(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @GetMapping("/get/email/{email}")
    @ApiOperation("Get User information using user email")
    public User getUserByEmail(@PathVariable("email") String email){return userService.getUserByEmail(email);}

    @PostMapping("/register/normal")
    @ApiOperation("Insert new User except avatar")
    public int insertUser(@RequestBody User user){return userService.insertUser(user);}

    @PostMapping("/login/normal")
    @ApiOperation("Normal Login using email and pwd")
    public int logIn(@RequestBody LoginParam loginParam){return userService.logIn(loginParam);}

    @PostMapping("/register/special")
    @ApiOperation("insert special User using uuid")
    public int insertSpecialUser(@RequestBody SpecialUser specialUser){return userService.insertSpecialUser(specialUser);}

    @GetMapping("/login/special/{uuid}")
    @ApiOperation("Special Login using uuid and pwd")
    public int logInSpecial(@PathVariable("uuid") String uuid){return userService.logInSpecial(uuid);}

    @PostMapping("/reset/pwd")
    @ApiOperation("Reset pwd, return -1 if wrong old pwd is given")
    public int resetPwd(@RequestBody ResetPwdParam pwdParam){return userService.resetPwdById(pwdParam);}

    @GetMapping("/alert/{id}")
    @ApiOperation("Get alert state using user id")
    public int getAlertState(@PathVariable("id") int id){return userService.getAlertStateById(id); }

    @GetMapping("/alert/change/{id}")
    @ApiOperation("Change alert state automatically")
    public int changeAlertState(@PathVariable("id") int id){return userService.changeAlertStateById(id); }

    @GetMapping("/profile/{id}")
    @ApiOperation("Get User Profile information except avatar")
    public Profile getUserProfile(@PathVariable("id") int id){return userService.getUserProfile(id); }

    @PostMapping("/profile/update")
    @ApiOperation("Update user profile except avatar")
    public int updateProfile(@RequestBody ProfileParam profileParam){return userService.updateProfile(profileParam);}

    @PostMapping("/avatar/update/{id}")
    @ApiOperation("Update user's avatar")
    public int updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable("id") int id){
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiNiuUtils.upload(file, filename);
        if(upload && userService.updateAvatar(QiNiuUtils.url + filename,id)==1){
            return 1;
        }
        return -1;
    }

    @GetMapping(value = "/avatar/{id}")
    @ApiOperation("Get user's avatar")
    public String getAvatar(@PathVariable("id") int id){return userService.getAvatar(id);}
}