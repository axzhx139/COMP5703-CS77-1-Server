//package com.gday.trackmygrocery.service.Impl;
//
//import com.gday.trackmygrocery.TrackmygroceryApplication;
//import com.gday.trackmygrocery.dao.pojo.Potential;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = TrackmygroceryApplication.class)
//@WebAppConfiguration
//
//class PotentialServiceImplTest {
//    @Autowired
//    private PotentialServiceImpl potentialService;
//    private String pre;
//
//    @Test
//    void getPotentialList() {
//        assertEquals( potentialService.getPotentialList(1).size(), 0);
//    }
//
//    @Test
//    void deleteById() {
//        assertEquals(0, potentialService.deleteById(1));
//    }
//
//    @Test
//    void updateListByUserId() {
//        assertEquals(1,potentialService.updateListByUserId(1));
//    }
//
//    @Test
//    void updateStatusById() {
//        assertEquals(0, potentialService.updateStatusById("bought",1));
//    }
//}