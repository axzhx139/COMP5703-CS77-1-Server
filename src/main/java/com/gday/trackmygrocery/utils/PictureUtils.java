package com.gday.trackmygrocery.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Component
public class PictureUtils {
    private final String PATH = "/home/ubuntu/COMP5703/mygrocery/";
    private volatile static PictureUtils pictureUtils;

    public PictureUtils() {
    }

    public static PictureUtils getInstance() {
        if (pictureUtils == null) {
            synchronized (PictureUtils.class) {
                if (pictureUtils == null) {
                    pictureUtils = new PictureUtils();
                }
            }
        }
        return pictureUtils;
    }

    public String updatePictureToServer(String typeName, int id, MultipartFile multipartFile) {
        if (typeName != null && multipartFile != null) {
            String path = PATH + typeName.substring(0, 1).toUpperCase() + typeName.substring(1) + "/";
            makeDirectory(path);

            String[] deleteType = {"png", "jpg", "gif"};

            for (String s : deleteType) {
                File file = new File(path +typeName+"_" + id + "." + s);
                if (file.exists()) {
                    file.delete();
                }
            }


            String fileName = path +typeName+ "_" + id + "." + StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
            File pictureFile = new File(fileName);
            try {
                pictureFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(pictureFile);
                outputStream.write(multipartFile.getBytes());
                return pictureFile.getAbsolutePath();
            } catch (Exception e) {
//                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private void makeDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public byte[] getPictureFromServer(String type,String path)  {
        FileInputStream fileInputStream;
        byte[] bytes;
        try {
            fileInputStream = new FileInputStream(new File(path));
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes, 0, fileInputStream.available());
            return bytes;
        } catch (Exception e) {
            String defaultPath=PATH+type.substring(0,1).toUpperCase()+type.substring(1)+"/"+type+"_default.png";
            try {
                fileInputStream=new FileInputStream(new File(defaultPath));
                bytes=new byte[fileInputStream.available()];
                fileInputStream.read(bytes,0, fileInputStream.available());
                return bytes;
            } catch (Exception ex) {
                return null;
            }
        }

    }

    public int deletePictureByPath(String path){
        File file=new File(path);
        if (file.exists()&&!path.contains("default")){
            file.delete();
            return 1;
        }else {
            return -1;
        }
    }

    public String getDefaultPicturePath(String type){
        String defaultPath;
        if (type!=null) {
            defaultPath= PATH + type.substring(0, 1).toUpperCase() + type.substring(1) + "/" + type + "_default.png";
            File file=new File(defaultPath);
            if(file.exists()) {
                return defaultPath;
            }else{
                return null;
            }
        }else {
            return null;
        }
    }
}



