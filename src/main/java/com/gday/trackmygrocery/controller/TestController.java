package com.gday.trackmygrocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/zhangjiaole")
    public String hello() {
        logger.info("hello>>>" + "<h1>不喝牛奶！</h1>");
        return "<h1>不喝牛奶！</h1>";
    }
}