package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.utils.LogUtils;
import com.gday.trackmygrocery.utils.PictureUtils;
import com.gday.trackmygrocery.vo.ItemVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("ocr")
public class OCRController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();
    public static final String YEAR_REGEX = ".*202[012345].*";

    private static Map<String, Integer> month = new HashMap<>();
    static {
        month.put("JAN", 1);
        month.put("FEB", 2);
        month.put("MAR", 3);
        month.put("APR", 4);
        month.put("MAY", 5);
        month.put("JUN", 6);
        month.put("JUL", 7);
        month.put("AUG", 8);
        month.put("SEP", 9);
        month.put("OCT", 10);
        month.put("NOV", 11);
        month.put("DEC", 12);

    }

    @GetMapping("/transferDate/{str}")
    public String transferDate(@PathVariable("str") String str) {
        logger.info("transferDate<<<(str: String): " + str);
//        String res = "";
//        String[] list = str.split(",,");
//        String temp = null;
//
//        int monthInt = -1;
//        int yearInt = -1;
//        for (String s : list) {
//            String m = null;
//            try {
//                m = Arrays.stream(month.keySet().toArray(new String[0])).parallel().filter(s.toUpperCase(Locale.ROOT)::contains).findAny().get();
//            } catch (NoSuchElementException e) {
//
//            }
//            if (m != null && m.length() != 0) {
//                monthInt = month.get(m);
//                //contains month
//                temp = s;
//                if (Pattern.matches(YEAR_REGEX, s)) {
//                    Pattern r = Pattern.compile("(.*)(202[012345])(.*)");
//                    Matcher mm = r.matcher(s);
//                    System.out.println(r.matcher(s).group(1));
//                    //yearInt = new Integer(r.matcher(s).group(2));
//                    System.out.println(yearInt);
//                }
//                break;
//            } else if (Pattern.matches(YEAR_REGEX, s)) {
//                System.out.println("find year");
//            }
//        }
//
//        System.out.println(month.size());
//
//
//
//        logger.info("transferDate>>>" + res);
        return "2022-05-18";
    }



}
