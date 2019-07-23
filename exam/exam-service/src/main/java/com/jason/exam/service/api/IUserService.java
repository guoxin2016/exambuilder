package com.jason.exam.service.api;

import java.util.Map;

public interface IUserService {
	
	int reg(Map<String, String> parmas);
	
	Map<String, Object> login(Map<String, String> parmas);

	Map<String, Object> getVipInfo(String uuid, String planUuid);

	boolean checkPhone(String phone);


}
