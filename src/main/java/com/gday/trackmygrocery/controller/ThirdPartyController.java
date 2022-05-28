package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.service.ThirdPartyService;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("thirdParty")
public class ThirdPartyController {
    final Logger logger= LoggerFactory.getLogger(getClass());
    final LogUtils logUtils= LogUtils.getInstance();

    @Autowired
    private ThirdPartyService thirdPartyService;

    @RequestMapping("/facebook/{fileName}")
    public byte[] getFacebook(@PathVariable("fileName") String fileName){
        logger.info("getFacebook<<<(fileName: String): "+fileName);
        byte[] res= thirdPartyService.getFacebook(fileName);
        logger.info("getFacebook>>>"+logUtils.printObjAsLog(res));
        return res;
    }


    @RequestMapping("/facebook/download/{fileName}")
    public String fileDownLoad(HttpServletResponse response, @PathVariable("fileName") String fileName){
        logger.info("fileDownLoad<<<(response: HttpServletResponse)"+logUtils.printObjAsLog(response)+"(fileName: String): "+fileName);
        String res= thirdPartyService.downloadFile(response,fileName);
        logger.info("fileDownLoad>>>"+logUtils.printObjAsLog(res));
        return res;
    }

}
