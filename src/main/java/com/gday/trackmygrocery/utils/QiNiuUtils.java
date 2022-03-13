package com.gday.trackmygrocery.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class QiNiuUtils {

    public static final String url = "http://r1xpvq9kr.hn-bkt.clouddn.com/";

    @Value("iXXIds4C0LvJS8GXX3PwlfBh0QcHcITO7Wq55bkW")
    private String accessKey;

    @Value("Y-_KFNKWP2PU9OF5oUS9ZHkmZ8PyoHAIWjRvh1Sa")
    private String secretKey;

    public boolean upload(MultipartFile file, String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//      ..生成上传凭证，然后准备上传

        String bucket = "nando-trackmygrocery";
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return true;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    return false;
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
