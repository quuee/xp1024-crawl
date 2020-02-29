package com.xp1024.util;


/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.tools
 * @date 2020/2/28 19:07
 */
public class UrlUtil {

    public static String getParam(String url){
        String[] arr = url.split("[?]");
        String[] split = arr[1].split("[=]");
        return split[1];
    }

    public static String getLastName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }


}
