package com.gday.trackmygrocery.controller;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.service.FeedbackService;
import com.gday.trackmygrocery.utils.LogUtils;
import com.gday.trackmygrocery.vo.params.FeedbackParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
public class FeedbackController {
    final Logger logger=LoggerFactory.getLogger(getClass());
    final LogUtils logUtils= LogUtils.getInstance();

    @Autowired
    private FeedbackService feedbackService;


    @PostMapping("/storeFeedback")
    @ApiOperation("Get Feedback and store the content to feedback file")
    public String storeFeedback(@RequestBody FeedbackParam feedbackParam){
        logger.info("storeFeedback<<<(feedbackParam: FeedbackParam): " + feedbackParam);
        String res = feedbackService.storeFeedbackToTxt(feedbackParam);
        logger.info("storeFeedback>>>" + logUtils.printObjAsLog(res));
        return res;
    }

}
