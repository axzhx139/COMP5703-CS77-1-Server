package com.gday.trackmygrocery.service.Impl;

import com.gday.trackmygrocery.service.ThirdPartyService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {
    private final String PATH = "/home/ubuntu/COMP5703/mygrocery/thirdParty/facebook/";

    @Override
    public byte[] getFacebook(String fileName) {
        File file = new File(PATH + fileName);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                fileInputStream.read(buffer);
                fileInputStream.close();
                return buffer;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    @Override
    public String downloadFile(HttpServletResponse response, String fileName) {
        File file = new File(PATH +'/'+ fileName);
        if(!file.exists()){
            return "File doesn't exist";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "Fail";
        }
        return "Success";
    }


}
