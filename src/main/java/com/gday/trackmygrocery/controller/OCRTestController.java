package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("ocrTest")
public class OCRTestController {
    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();


    public static Map<String, String> patternMap = new HashMap<>();
    public static Set<String> dateFormatSet = new TreeSet<>(Collections.reverseOrder());

    static {
        //put pattern;
        patternMap.put("DAY_MONTH_TEXT", "(.*),,(\\d|\\d{2})[ -/\\\\_]([A-Za-z]{3}),,(.*)");
        patternMap.put("DAY_MONTH_NUM", "(.*),,([0-3][0-9]|[1-9])[ -/\\\\_]([0-1][0-9]|[1-9]),,(.*)");
        patternMap.put("DAY_MONTH_TEXT_YEAR", "(.*),,([0-9][0-9]|[0-9])[ -/\\\\_]([A-Za-z]{3})[ -/\\\\_]([0-9]{4}),,(.*)");// ,,02 AUG 2022,,
        patternMap.put("DAY_MONTH_NUM_YEAR", "(.*),,([0-9][0-9]|[0-9])[ -/\\\\_](\\d{2})[ -/\\\\_]([0-9]{4}),,(.*)");
        patternMap.put("MONTH_TEXT_YEAR", "(.*),,([A-Za-z]{3})[ -/\\\\_]([0-9]{4}),,(.*)");// ,,AUG 2022,,
        patternMap.put("DAY_MONTH_NUM_YEAR_SEQ", "(.*),,(\\d{2})(\\d{2})(\\d{4}),,(.*)"); //,,02022022,,
        patternMap.put("MONTH_TEXT_YEAR_TIME", "(.*),,([A-Za-z]{3})(\\d{4})(.*)");// ,,AUG202214:05,,
        patternMap.put("MONTH_NUM_YEAR", "(.*),,([0-9]{2}}|[0-9])[ -/\\\\_](\\d{4}),,(.*)");//02 2022

        //put date format;
        dateFormatSet.add("yyyy-MM-dd");
        dateFormatSet.add("yyyy-MM");
//        dateFormatSet.add("dd-MM");
        dateFormatSet.add("yyyy");
    }

    public static String searchDateString(String str) {
        Iterator<Map.Entry<String, String>> iterator = patternMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Matcher matcher = Pattern.compile(entry.getValue()).matcher(str);
            if (matcher.find()) {
                DateFormat dateFormatInput = new SimpleDateFormat("MMM");
                DateFormat dateFormatOutput = new SimpleDateFormat("MM");
                int year = Calendar.getInstance().get(Calendar.YEAR);
                switch (entry.getKey().trim().toUpperCase()) {
                    //return year;month;day
                    case "DAY_MONTH_NUM_YEAR":
                    case "DAY_MONTH_TEXT_YEAR": {
                        try {
                            return matcher.group(4) + "-" + dateFormatOutput.format(dateFormatInput.parse(matcher.group(3))) + "-" + matcher.group(2);
                        } catch (ParseException e) {
                            return matcher.group(4);
                        }
                    }

                    case "DAY_MONTH_NUM_YEAR_SEQ": {
                        return matcher.group(4) + "-" + matcher.group(3) + "-" + matcher.group(2);
                    }
                    case "MONTH_TEXT_YEAR":
                    case "MONTH_TEXT_YEAR_TIME": {
//                        for (int i=0;i<4;i++){
//                            System.out.println(matcher.group(i));
//                        }
                        try {
                            return matcher.group(3) + "-" + dateFormatOutput.format(dateFormatInput.parse(matcher.group(2)));
                        } catch (ParseException e) {
                            return matcher.group(3);
                        }
                    }
                    case "DAY_MONTH_TEXT":
                    case "DAY_MONTH_NUM": {
//
                        try {
                            return year + "-" + dateFormatOutput.format(dateFormatInput.parse(matcher.group(3))) + "-" + matcher.group(2);
                        } catch (ParseException e) {
                            return Integer.toString(year);
                        }
                    }

                    default: {
                        System.out.println(entry.getKey());
                        System.out.println(entry.getValue() + "Error\n");
                        return "Error";
                    }

                }
            }
        }
        return null;
    }

    public static boolean isValid(String date, String dateFormat) {
        if (date == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            Date date1 = sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String formatDate(String testString) {
//        System.out.println(testString);
        if (testString == null||"Error".equalsIgnoreCase(testString)) {
            return "Error";
        }
        DateFormat dateFormat;

        for (String testFormat : dateFormatSet) {

            if (isValid(testString, testFormat)) {
                dateFormat = new SimpleDateFormat(testFormat);

                try {
                    return dateFormat.format(dateFormat.parse(testString));
                } catch (ParseException e) {
//                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    @GetMapping("/transferDate/{str}")
    public String transferDateTest(@PathVariable("str") String str) {
        logger.info("transferDateTest<<<(str: String): " + str);
        String res = formatDate(searchDateString(str));
        logger.info("transferDate>>>" + res);
        return res;
    }

}


