package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.service.FeedbackService;
import com.gday.trackmygrocery.vo.params.FeedbackParam;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private PrintWriter printWriter;
    private DateTimeFormatter dateTimeFormat= DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    private final String PATH="/Feedback";

    @Override
    public String storeFeedbackToTxt(FeedbackParam feedbackParam) {
        File dir=new File(String.valueOf(PATH));
        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            LocalDateTime localDateTime=LocalDateTime.now();
            printWriter=new PrintWriter(new File("/Feedback/user"+feedbackParam.getId()+" "+dateTimeFormat.format(localDateTime)));
            String res=feedbackParam.getFeedback();
            printWriter.println(res);
            printWriter.close();
            return res;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return "Directory not fount.";
        }
    }
}
