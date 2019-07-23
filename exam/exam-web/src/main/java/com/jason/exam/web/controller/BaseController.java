package com.jason.exam.web.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

	/**
	 * map -> model
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object convertMapToBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
        {
            return null;
        }
        Object obj = beanClass.newInstance();//新实例

        Field[] fields = obj.getClass().getDeclaredFields(); //获取所有的属性
        for (Field field : fields) {
            int mod = field.getModifiers();//返回此类或接口以整数编码的 Java 语言修饰符
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);//打破封装
            if(field.getGenericType().toString().equals(
            	      "class java.lang.String")) {
            	field.set(obj, map.get(field.getName()).toString());
            }else if(field.getGenericType().toString().equals(
          	      "class java.lang.Integer")){
            	field.set(obj, Integer.valueOf(map.get(field.getName()).toString()));
            }
            
        }

        return obj;
    }


	protected Map<String, Object> convertBeanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (null == value) {
						map.put(key, "");
					} else {
						map.put(key, value);
					}
				}
			}
		} catch (Exception e) {
//			logger.error("convertBean2Map Error {}", e);
		}
		return map;
	}

}
