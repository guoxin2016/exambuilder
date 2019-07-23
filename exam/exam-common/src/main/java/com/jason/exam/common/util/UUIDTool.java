package com.jason.exam.common.util;
import java.util.UUID;

public class UUIDTool
{
    public static String getUUID() {  
        /*UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13)  
                + str.substring(14, 18) + str.substring(19, 23)  
                + str.substring(24);  
        return temp;*/  
          
        return UUID.randomUUID().toString().replace("-", "");  
    }  
    
    public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
