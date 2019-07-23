package com.jason.exam.service.api;

import java.util.Map;

import com.jason.exam.model.Order;

public interface IOrderService {
	
	Order genOrder(Map<String, String> parmas);

	Order findOrderByCode(String outTradeNo);

	void updateStatus(Order order);

	Order findOrderByUUid(String orderUuid);

}
