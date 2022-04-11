package com.gday.trackmygrocery.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

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
                e.printStackTrace();
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

    public byte[] getPictureFromServer(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes, 0, fileInputStream.available());
        return bytes;
    }
}



