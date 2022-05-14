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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("ocrTest")
public class OCRCTestController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();
    public static final String YEAR_REGEX = "(.*)(202[012345])(.*)";
    public static final String DAY_REGEX = "(.*)([0123]{1}[0123456789]{1})(.*)";
    public static final String DAYMONTH_REGEX = "(.*)([0123]{1}[0123456789]{1})([01]{1}[0123456789]{1})(.*)";
    public static final String MONTHDAY_REGEX = "(.*)([01]{1}[0123456789]{1})([0123]{1}[0123456789]{1})(.*)";
    public static final String YMD_REGEX = "(.*)(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)(.*)";
    public static final String YMD_REGEX_TEST = "(.*)(?:(?!0000|0[0-9]{3})[0-9]{4}-" +
            "(?:(?:0[1-9]|1[0-2])" +
            "-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)(.*)";
    public static final String DMY_REGEX = "(.*)(?:(?!0000)[0-9]{4}(.*)";

    public static final String[] MONTH31 = {"1", "01", "3", "03", "5", "05", "7", "07", "8", "08", "10", "12"};
    public static final String[] MONTH30 = {"4", "04", "6", "06", "9", "09", "11"};
    public static final String[] MONTH29 = {"2", "02"};


    private static Map<String, String> month = new HashMap<>();
    static {
        month.put("JAN", "01");
        month.put("FEB", "02");
        month.put("MAR", "03");
        month.put("APR", "04");
        month.put("MAY", "05");
        month.put("JUN", "06");
        month.put("JUL", "07");
        month.put("AUG", "08");
        month.put("SEP", "09");
        month.put("OCT", "10");
        month.put("NOV", "11");
        month.put("DEC", "12");

    }

    @GetMapping("/transferDate/{str}")
    synchronized public String transferDate(@PathVariable("str") String str) {
        logger.info("transferDate<<<(str: String): " + str);
        String res = "";
        String[] list = str.split(",,");
        String temp = null;

        String monthStr = "";
        String yearStr = "";
        String dayStr = "";
        for (String s : list) {
            Matcher matcherFirst = Pattern.compile(YMD_REGEX).matcher(s);
            // 判断是否可以找到匹配正则表达式的字符
            if (matcherFirst.find()) {
                logger.info("transferDate>>>" + matcherFirst.group(2));
                return matcherFirst.group(2);
            }
            String m = null;
            try {
                m = Arrays.stream(month.keySet().toArray(new String[0])).parallel().filter(s.toUpperCase(Locale.ROOT)::contains).findAny().get();
            } catch (NoSuchElementException e) {

            }
            if (m != null && m.length() != 0) {
                monthStr = month.get(m);
                s.replace(m, "");
                //contains month
                temp = s;


                Matcher matcher = Pattern.compile(YEAR_REGEX).matcher(s);
                // 判断是否可以找到匹配正则表达式的字符
                if (matcher.find()) {
                    // 将匹配当前正则表达式的字符串即文件名称进行赋值
                    yearStr = matcher.group(2);
                    logger.info("year is: " + yearStr);
                    s.replace(yearStr, "");
                }
                matcher = Pattern.compile(DAY_REGEX).matcher(s);
                if (matcher.find()) {
                    // 将匹配当前正则表达式的字符串即文件名称进行赋值
                    dayStr = matcher.group(2);
                    logger.info("day is: " + dayStr);
                    s.replace(dayStr, "");
                }
                res = yearStr + "-" + monthStr + "-" + dayStr;
                logger.info("transferDate>>>" + res);
                return res;
            } else if (Pattern.matches(YEAR_REGEX, s)) {
                logger.info("year is: " + yearStr);
                s.replace(yearStr, "");
                Matcher matcherDM = Pattern.compile(DAYMONTH_REGEX).matcher(s);
                Matcher matcherMD = Pattern.compile(MONTHDAY_REGEX).matcher(s);
                if (matcherDM.find()) {
                    dayStr = matcherDM.group(2);
                    monthStr = matcherDM.group(3);
                    if (checkDM(dayStr, monthStr)) {
                        res = yearStr + "-" + monthStr + "-" + dayStr;
                        logger.info("transferDate>>>" + res);
                        return res;
                    } else {
                        dayStr = "";
                        monthStr = "";
                    }
                }
                if (matcherMD.find()) {
                    dayStr = matcherMD.group(3);
                    monthStr = matcherMD.group(2);
                    if (checkDM(dayStr, monthStr)) {
                        res = yearStr + "-" + monthStr + "-" + dayStr;
                        logger.info("transferDate>>>" + res);
                        return res;
                    } else {
                        dayStr = "";
                        monthStr = "";
                    }
                }
            } else {
                continue;
            }
        }
        res = "" + yearStr + "-" + monthStr + "-" + dayStr;
        logger.info("transferDate>>>" + res);
        return res;
    }

    private boolean checkDM(String dayStr, String monthStr) {
        int d = -1;
        try {
            d = Integer.valueOf(dayStr).intValue();
        } catch (NumberFormatException e) {
            logger.error("checkDM---input day: " + dayStr + "month: " + monthStr + "have invalid dayStr!");
            return false;
        }
        if (d < 1 || d > 31) {
            logger.info("checkDM---input day: " + dayStr + "month: " + monthStr + "have invalid dayStr!");
            return false;
        }

        String m = null;
        try {
            m = Arrays.stream(MONTH31).parallel().filter(monthStr::equals).findAny().get();
        } catch (NoSuchElementException e) {

        }
        if (m != null && m.length() != 0) {
            return true;
        }
        try {
            m = Arrays.stream(MONTH30).parallel().filter(monthStr::equals).findAny().get();
        } catch (NoSuchElementException e) {

        }
        if (m != null && m.length() != 0 && d < 31) {
            return true;
        }
        try {
            m = Arrays.stream(MONTH29).parallel().filter(monthStr::equals).findAny().get();
        } catch (NoSuchElementException e) {
            logger.error("checkDM---input day: " + dayStr + "month: " + monthStr + "have invalid monStr!");
            return false;
        }
        if (m != null && m.length() != 0 && d < 30) {
            return true;
        }
        return false;
    }
}
