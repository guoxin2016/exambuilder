package com.jason.exam.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.api.util.MD5Util2;
import com.jason.exam.model.Order;
import com.jason.exam.service.api.IOrderService;
import com.jason.exam.service.api.IPayService;

@Controller
@RequestMapping("/pay")
public class PayController {
	
	String AppKey ="b68987d1c3924626a9bb66ab3321a454";
	String AppSecre = "56e2083209824b1d9d09584ad0638ee8";

	@Autowired
	IPayService payService;
	
	@Autowired
	IOrderService orderService;
	
	// 获取章节练习
	@RequestMapping("notify")
	@ResponseBody
	public String notify(@RequestParam Map<String, String> params) {
		System.out.println(params);
		try {
			String appkey = params.get("appkey");
			String method = params.get("method");
			String sign = params.get("sign");
			String timestamp = params.get("timestamp");
			String version = params.get("version");
			String outTradeNo = params.get("outTradeNo");
			String payType = params.get("payType");
			String tradeName = params.get("tradeName");
			String amount = params.get("amount");
			String status = params.get("status");
			String notifyTime = params.get("notifyTime");
			String payUserId = params.get("payUserId");
			String channel = params.get("channel");
			String backParams = params.get("backParams");
			
			String linkString = createLinkString(params);
			String stringSignTemp=linkString+"&appSceret="+AppSecre;
			String signtemp = MD5Util2.MD5(stringSignTemp);
			if(signtemp.equals(sign.toLowerCase())) {
				System.out.println("验证成功");
				if("2".equals(status)) {
					//支付成功
					//更新用户权限
					//先更新用户权限
					Order order = orderService.findOrderByCode(outTradeNo);
					if(order!=null) {
						String userUuid = order.getUser_uuid();
						String planUuid = order.getProduct_uuid();
						payService.updateUserAuth(order);
						//更新order 的状态
						orderService.updateStatus(order);
					}
				}
			}
			System.out.println(params);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
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


}
