package com.jason.exam.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TimeTool
{
    public static String getCurrectTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }

    public static String getCurrectDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(System.currentTimeMillis());
    }

    public static String addMonthDay(String time, String month, String day)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d = df.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            if(StringUtils.isNotBlank(day)) {
                c.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
            }
            if(StringUtils.isNotBlank(month)) {
                c.add(Calendar.MONTH, Integer.valueOf(month));
            }
            Date tomorrow = c.getTime();
            return df.format(tomorrow.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String addMonthDay(Date time, int month)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
		c.setTime(time);
		if(StringUtils.isNotBlank(String.valueOf(month))) {
		    c.add(Calendar.MONTH, month);
		}
		Date tomorrow = c.getTime();
		return df.format(tomorrow.getTime());
    }
    
    public static void main(String[] args)
    {
        System.out.println(addMonthDay("2018-01-01 23:59:59", "2", "2"));
    }

}
