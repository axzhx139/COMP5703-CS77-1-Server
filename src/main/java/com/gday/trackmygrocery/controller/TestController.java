package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.service.UserService;
import com.gday.trackmygrocery.utils.EncryptUtils;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils= LogUtils.getInstance();

    @Autowired
    private UserService userService;

    @RequestMapping("/zhangjiaole")
    public String hello() {
        logger.info("hello>>>" + "<h1>不喝牛奶！</h1>");
        User user0 = userService.getUserById(1);
        if (user0 == null) {
            return "<h1>不喝牛奶！</h1>" + "<h2>" + EncryptUtils.EncryptString("不喝牛奶！") + "</h2><h3>User 1 not exist!</h3>";
        }
        return "<h1>不喝牛奶！</h1>" + "<h2>" + EncryptUtils.EncryptString("不喝牛奶！") + "</h2><h3>" + logUtils.printObjAsLog(user0) + "</h3>";
    }
}