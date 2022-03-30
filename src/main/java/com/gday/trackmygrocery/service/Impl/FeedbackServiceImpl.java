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
    private final String PATH="/home/ubuntu/COMP5703/mygrocery/Feedback/";

    @Override
    public String storeFeedbackToTxt(FeedbackParam feedbackParam) {
        File dir=new File(PATH+"user"+feedbackParam.getId());
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            LocalDateTime localDateTime=LocalDateTime.now();
            String fileName=PATH+"user"+feedbackParam.getId()+"/"+dateTimeFormat.format(localDateTime)+" (1).txt";

            File file=new File(fileName);

            while (file.exists()){
                StringBuilder stringBuilder=new StringBuilder();
                int start=fileName.indexOf("(");
                int end=fileName.indexOf(")");
                for (int i=start+1;i<end;i++){
                    stringBuilder.append(fileName.charAt(i));
                }
                int index=Integer.parseInt(String.valueOf(stringBuilder))+1;
                file=new File(PATH+"user"+feedbackParam.getId()+"/"+dateTimeFormat.format(localDateTime)+" ("+index+").txt");
            }

            printWriter=new PrintWriter(fileName);
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
