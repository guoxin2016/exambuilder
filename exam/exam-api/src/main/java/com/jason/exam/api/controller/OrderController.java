package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Order;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	IOrderService orderService;
	
	@RequestMapping("genOrder")
	@ResponseBody
    public R list1(@RequestParam Map<String, String> params) {
        try {
        	Order order = orderService.genOrder(params);
            return R.isOk().data(order);
        } catch (Exception e) {
            return R.isFail(e);
        }
    }
	
	
}
