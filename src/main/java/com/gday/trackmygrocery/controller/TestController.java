package com.gday.trackmygrocery.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/zhangjiaole")
    public String hello(){
        return "<h1>不喝牛奶！</h1>";
    }
}