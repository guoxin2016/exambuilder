package com.jason.exam.api.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


public class DemoInterceptor
{
    
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            String value = params.get(key);
            prestr = prestr + key + "=" + value + "&";
        }
        return prestr.substring(0, prestr.length() - 1);
    }
    
    public static void main(String[] args)
    {
        String signKey = "C7F5954E8B61A93EDB4594BBFC318852";
        String time = "20190416224310";
        System.out.println(String.valueOf(time));
        Map<String, String> reqmap = new HashMap<String, String>();
        reqmap.put("devid", "1898888");
        reqmap.put("channelid", "188888");
        reqmap.put("planUuid", "24970f442bee4b8d8be325b50f6034f5");
        reqmap.put("time", time);
        
        String linkstring = "?" +createLinkString(reqmap) + signKey;
        System.out.println("2222"+linkstring);
        System.out.println(com.jason.exam.api.util.MD5Util2.MD5(linkstring));
    }

}
