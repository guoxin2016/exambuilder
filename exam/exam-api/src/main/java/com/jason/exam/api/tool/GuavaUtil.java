package com.jason.exam.api.tool;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jason.exam.api.util.SSMTool;

public class GuavaUtil
{
    
    /**
     * 获取手机验证码
     */
    public static LoadingCache<String, String> userVerificationCode = CacheBuilder
            .newBuilder().maximumSize(10000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>()
            {
                @Override
                public String load(String key) throws Exception
                {
                    return getVerificationCode(key);
                }
            });

    private static String getVerificationCode(String ids)
    {

        String code = "";
        try
        {
            code = SSMTool.getRandNum();
        }
        catch (Exception e)
        {
            code = "";
        }
        return code;
    }
    
    public static Cache<String, String> userSession = CacheBuilder.newBuilder().maximumSize(100000)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();
    

}
