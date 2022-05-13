package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.TrackmygroceryApplication;
import com.gday.trackmygrocery.dao.pojo.SpecialUser;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.vo.params.LoginParam;
import com.gday.trackmygrocery.vo.params.ProfileParam;
import com.gday.trackmygrocery.vo.params.ResetPwdParam;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrackmygroceryApplication.class)
@WebAppConfiguration
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;
    private String pre;


    @Test
    void getUserById() {
        assertNotNull(userService.getUserById(1));
        assertEquals("admin",userService.getUserById(1).getName());
        assertEquals("sjm@gmail.com",userService.getUserById(1).getEmail());
        assertEquals("123",userService.getUserById(1).getPwd());

    }

    @Test
    void insertUser() {
        User user = new User();
        userService.insertUser(user);
    }

    @Test
    void getUserByEmail() {
        assertNotNull(userService.getUserByEmail("sjm@gmail.com"));
    }

    @Test
    void logIn() {
        LoginParam login = new LoginParam();
        assertEquals(-1, userService.logIn(login));

        LoginParam correctLogin = new LoginParam("sjm@gmail.com", "123");
        assertEquals(1, userService.logIn(correctLogin));
    }

    @Test
    void insertSpecialUser() {
        SpecialUser user = new SpecialUser();
        userService.insertSpecialUser(user);
    }

    @Test
    void logInSpecial() {
        assertEquals(-1, userService.logInSpecial("aaaaa"));
    }



    @Test
    void getAlertStateById() {
        userService.getAlertStateById(1);
    }

    @Test
    void changeAlertStateById() {
        assertEquals(1, userService.changeAlertStateById(1));
        assertEquals(0, userService.changeAlertStateById(0));
    }

    @Test
    void getUserProfile() {
        assertNotNull(userService.getUserProfile(1));

    }

    @Test
    void updateProfile() {
        ProfileParam para = new ProfileParam();
        userService.updateProfile(para);
    }

    @Test
    void verifyUser() {
        User user = new User();
        assertEquals(-1, userService.verifyUser(user));

    }


}