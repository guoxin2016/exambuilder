package com.jason.exam.service.api.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.OrderRepository;
import com.jason.exam.model.Order;
import com.jason.exam.service.api.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order genOrder(Map<String, String> parmas) {
		String user_uuid = parmas.get("user_uuid");
		String product_uuid = parmas.get("product_uuid");
		int pay_type = Integer.valueOf(parmas.get("pay_type"));
		String amount = parmas.get("amount");
		String product_name = parmas.get("product_name");
		String channel = parmas.get("channel");
		Order order = new Order();
		order.setCode(getOrderIdByTime());
		order.setCreateTime(new Date());
		order.setUser_uuid(user_uuid);
		order.setStatus(1);
		order.setUuid(UUIDTool.getUUID());
		order.setProduct_uuid(product_uuid);
		order.setPay_type(pay_type);
		order.setAmount(amount);
		order.setChannel(channel);
		order.setProduct_name(product_name);
		order = orderRepository.save(order);
		return order;
	}
	
	public static String getOrderIdByTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for(int i=0;i<3;i++) {
			result += random.nextInt(10);
		}
		return newDate+result;
	}

	@Override
	public Order findOrderByCode(String outTradeNo) {
		return orderRepository.findOrderByCode(outTradeNo);
	}

	@Override
	public void updateStatus(Order order) {
		order.setStatus(2);
		orderRepository.saveAndFlush(order);
	}

	@Override
	public Order findOrderByUUid(String orderUuid) {
		return orderRepository.findOrderByUUid(orderUuid);
	}

}
