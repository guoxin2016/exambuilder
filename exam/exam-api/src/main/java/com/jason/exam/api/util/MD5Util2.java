package com.jason.exam.api.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util2 {
/*	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
	
	  public static String MD5(String sss) {
		  byte[] btInput = sss.getBytes();
		    String s  = null;
		    char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		    MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				 md.update(btInput);
				    byte[] datas = md.digest(); //16个字节的长整数
				    char[] str = new char[2*16];
				    int k = 0;
				    for(int i=0;i<16;i++){
				      byte b   = datas[i];
				      str[k++] = hexDigist[b>>>4 & 0xf];//高4位
				      str[k++] = hexDigist[b & 0xf];//低4位
				    }
				    s = new String(str);
				   
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 return s;
		  }
	  
	  
	  public static void main(String[] args)
    {
        System.out.println(MD5("sfsfsd"));
    }
}
