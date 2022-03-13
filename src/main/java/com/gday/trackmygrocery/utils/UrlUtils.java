package com.gday.trackmygrocery.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UrlUtils {

    /**
     * add parameters to the url
     * @param url
     * @param params Map<String, String>
     * @return
     */
    public static String appendParams(String url, Map<String, String> params){
        if(StrUtils.isBlank(url)){
            return "";
        }else if(StrUtils.isEmptyMap(params)){
            return url.trim();
        }else{
            StringBuffer sb = new StringBuffer("");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if(index > -1){
                if((length - 1) == index){
                    url += sb.toString();
                }else{
                    url += "&" + sb.toString();
                }
            }else{
                url += "?" + sb.toString();
            }
            return url;
        }
    }

    /**
     * add param (single)
     * @param url
     * @param name String
     * @param value String
     * @return
     */
    public static String appendParam(String url, String name, String value){
        if(StrUtils.isBlank(url)){
            return "";
        }else if(StrUtils.isBlank(name)){
            return url.trim();
        }else{
            Map<String, String> params = new HashMap<String, String>();
            params.put(name, value);
            return appendParams(url, params);
        }
    }

    /**
     * remove params
     * @param url String
     * @param paramNames String[]
     * @return
     */
    public static String removeParams(String url, String... paramNames){
        if(StrUtils.isBlank(url)){
            return "";
        }else if(StrUtils.isEmptyArray(paramNames)){
            return url.trim();
        }else{
            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if(index > -1){
                if((length - 1) == index){
                    return url;
                }else{
                    String baseUrl = url.substring(0, index);
                    String paramsString = url.substring(index + 1);
                    String[] params = paramsString.split("&");
                    if(!StrUtils.isEmptyArray(params)){
                        Map<String, String> paramsMap = new HashMap<String, String>();
                        for (String param : params) {
                            if(!StrUtils.isBlank(param)){
                                String[] oneParam = param.split("=");
                                String paramName = oneParam[0];
                                int count = 0;
                                for(int i=0; i<paramNames.length; i++){
                                    if(paramNames[i].equals(paramName)){
                                        break;
                                    }
                                    count ++;
                                }
                                if(count == paramNames.length){
                                    paramsMap.put(paramName, (oneParam.length > 1)?oneParam[1]:"");
                                }
                            }
                        }
                        if(!StrUtils.isEmptyMap(paramsMap)){
                            StringBuffer paramBuffer = new StringBuffer(baseUrl);
                            paramBuffer.append("?");
                            Set<String> set = paramsMap.keySet();
                            for (String paramName : set) {
                                paramBuffer.append(paramName).append("=").append(paramsMap.get(paramName)).append("&");
                            }
                            paramBuffer.deleteCharAt(paramBuffer.length() - 1);
                            return paramBuffer.toString();
                        }
                        return baseUrl;
                    }
                }
            }
            return url;
        }
    }

}


