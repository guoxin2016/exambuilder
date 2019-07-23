package com.jason.exam.service.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.exam.common.util.MD5Util2;
import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.OrderRepository;
import com.jason.exam.dao.repository.api.UserRepository;
import com.jason.exam.dao.repository.api.VipRepository;
import com.jason.exam.model.Order;
import com.jason.exam.model.User;
import com.jason.exam.model.Vip;
import com.jason.exam.service.api.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private VipRepository vipRepository;

	@Override
	public int reg(Map<String, String> parmas) {
		String uuid = parmas.get("uuid");
		User user = new User();
		if(StringUtils.isNotBlank(uuid)) {
			//更新
			user.setEmail(parmas.get("email"));
			user.setImei(parmas.get("imei"));
			user.setNickname(parmas.get("nickname"));
			user.setPwd(parmas.get("pwd"));
			user.setPwdmd5(MD5Util2.MD5(parmas.get("pwd")));
			user.setQq(parmas.get("qq"));
			user = userRepository.saveAndFlush(user);
		}else {
			String phone = parmas.get("phone");
			boolean b = this.checkPhone(phone);
			if(!b) {
				return -1;
			}
			user.setCreate_time(new Date());
			user.setEmail(parmas.get("email"));
			user.setImei(parmas.get("imei"));
			user.setNickname(parmas.get("nickname"));
			user.setPhone(parmas.get("phone"));
			user.setPlan_uuid(parmas.get("plan_uuid"));
			user.setPwd(parmas.get("pwd"));
			user.setPwdmd5(MD5Util2.MD5(parmas.get("pwd")));
			user.setQq(parmas.get("qq"));
			user.setStatus(1);
			user.setUuid(UUIDTool.getUUID());
			user = userRepository.saveAndFlush(user);
		}
		if(user!=null) {
			return 1;
		}
		return 0;
	}

	@Override
	public Map<String, Object> login(Map<String, String> parmas) {
		String phone = parmas.get("phone");
		String pwd = parmas.get("pwd");
		String planUuid = parmas.get("planUuid");
        if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(pwd)) {
            pwd = MD5Util2.MD5(pwd);
            Map<String, Object> user = userRepository.findByPhoneAndPwd(phone,pwd);
            if(user!=null) {
            	Map<String, Object> temp = new HashMap<>();
        		temp.putAll(user);
        		//获取用户权限
        		temp.put("vip_level", -1);
        		//获取广告权限
        		Order order = orderRepository.findRemoveADByUserUuidAndPlanUuid((String)temp.get("uuid"),planUuid);
                if(order!=null) {
                	temp.put("vip_level", 0);
                }
                Vip vip = vipRepository.findVipByUserUuidAndPlanUuid((String)temp.get("uuid"), planUuid);
                if(vip!=null && vip.getEnddata().after(new Date())) {
                	temp.put("vip_level", 1);
                	temp.put("vip", vip);
                }
        		return temp;
            }
        }
		return null;
	}

	@Override
	public Map<String, Object> getVipInfo(String uuid, String planUuid) {
		Map<String, Object> temp = new HashMap<>();
		//获取用户权限
		temp.put("vip_level", -1);
		//获取广告权限
		Order order = orderRepository.findRemoveADByUserUuidAndPlanUuid(uuid,planUuid);
        if(order!=null) {
        	temp.put("vip_level", 0);
        }
        Vip vip = vipRepository.findVipByUserUuidAndPlanUuid(uuid, planUuid);
        if(vip!=null && vip.getEnddata().after(new Date())) {
        	temp.put("vip_level", 1);
        	temp.put("vip", vip);
        }
		return temp;
	}

	@Override
	public boolean checkPhone(String phone) {
		User u = userRepository.findUserByPhone(phone);
		if(u!=null) {
			return false;
		}
		return true;
	}

	
}
