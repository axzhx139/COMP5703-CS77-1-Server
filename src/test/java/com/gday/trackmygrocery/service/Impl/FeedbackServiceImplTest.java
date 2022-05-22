package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.TrackmygroceryApplication;
import com.gday.trackmygrocery.vo.params.FeedbackParam;
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

class FeedbackServiceImplTest {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @Test
    void storeFeedbackToTxt() {
        FeedbackParam feedbackPara = new FeedbackParam();
        feedbackService.storeFeedbackToTxt(feedbackPara);
    }
}