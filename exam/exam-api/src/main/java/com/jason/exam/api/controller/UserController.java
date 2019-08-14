package com.jason.exam.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jason.exam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.api.tool.GuavaUtil;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IUserService;


@Controller
@RequestMapping("user")
public class UserController
{
    @Autowired
    IUserService userService;

    /**
     * 用户注册,修改
     */
    @RequestMapping("reg")
	@ResponseBody
    public R reg(@RequestParam Map<String, String> params)
    {
    	try {
			int res = userService.reg(params);
			switch (res) {
			case 1:
				return R.isOk();
			case 0:
				Exception e = new Exception("注册失败");
				return R.isFail(e);
			case -1:
				Exception e1 = new Exception("手机号已被注册");
				return R.isFail(e1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
		return null;
    }
    
    /**
     * 检查手机号
     */
    @RequestMapping("checkPhone")
	@ResponseBody
    public R checkPhone(@RequestParam Map<String, String> params)
    {
    	try {
    		String phone = params.get("phone");
    		boolean b = userService.checkPhone(phone);
    		return R.isOk().data(b);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 登录
     */
    @RequestMapping("login")
	@ResponseBody
    public R login(@RequestParam Map<String, String> params,HttpSession session)
    {
    	try {
    		Map<String, Object> data= userService.login(params);
    		if(data!=null) {
    			Map<String, Object> temp = new HashMap<>();
        		temp.putAll(data);
    			String sessionId = session.getId();
    			temp.put("sessionId", sessionId);
                GuavaUtil.userSession.put((String) temp.get("uuid"),sessionId);
                return R.isOk().data(temp);
    		}else {
    			Exception e = new Exception("登录失败");
    			return R.isFail(e);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 会员信息
     */
    @RequestMapping("vip")
	@ResponseBody
    public R vip(@RequestParam Map<String, String> params,HttpSession session)
    {
    	try {
    		String uuid = params.get("userUuid");
    		String sessionId = params.get("sessionId");
    		String planUuid = params.get("planUuid");
    		
    		String sessionId2 = GuavaUtil.userSession.getIfPresent(uuid);
    		if(sessionId2 !=null && sessionId2.equals(sessionId)) {
    			//获取用户最新权限
    			Map<String, Object> temp = userService.getVipInfo(uuid,planUuid);
    			return R.isOk().data(temp);
    		}else {
    			Exception e = new Exception("登录失效");
    			return R.isFail(e);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }

	/**
	 * 苹果游客登录
	 */
	@RequestMapping("guest")
	@ResponseBody
	public R guest(@RequestParam Map<String, String> params,HttpSession session)
	{
		try {
			String iPhoneUuid = params.get("iPhoneUuid");
			String planUuid = params.get("planUuid");

			User user = userService.guest(iPhoneUuid,planUuid);
			String uuid = user.getUuid();
			Map<String, Object> temp = new HashMap<>();
			temp.put("nickname",user.getNickname());
			temp.put("uuid",uuid);
			String sessionId = session.getId();
			temp.put("sessionId", sessionId);
			GuavaUtil.userSession.put((String) temp.get("uuid"),sessionId);
			//获取vip的信息
			Map<String, Object> vip = userService.getVipInfo(uuid,planUuid);
			temp.putAll(vip);
			return R.isOk().data(temp);

		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}
    
    
    
}
