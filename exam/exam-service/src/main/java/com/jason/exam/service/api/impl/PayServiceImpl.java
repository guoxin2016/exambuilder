package com.jason.exam.service.api.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.exam.common.util.TimeTool;
import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.ProductsRepository;
import com.jason.exam.dao.repository.api.VipRepository;
import com.jason.exam.model.Order;
import com.jason.exam.model.Products;
import com.jason.exam.model.Vip;
import com.jason.exam.service.api.IPayService;

@Service
public class PayServiceImpl implements IPayService {

	@Resource
	VipRepository vipRepository;

	@Resource
	ProductsRepository productsRepository;

	@Override
	public void updateUserAuth(Order order) {
		Date date = new Date();
		String userUuid = order.getUser_uuid();
		String productUuid = order.getProduct_uuid();

		Products product = productsRepository.findProductsByUuid(productUuid);

		int vipmonths = product.getVipmonths();
		String vipLevel = product.getVip_level();

		String planUuid = product.getPlan_uuid();
		if (vipmonths > 0 && !"0".equals(vipLevel)) { // 不是购买的去除广告的订单，才需要修改权限
			Vip vip = vipRepository.findVipByUserUuidAndPlanUuid(userUuid, planUuid);
			if (vip == null) {
				// 如果没有任何vip信息则新建数据
				vip = new Vip();
				vip.setCreatedata(date);
				vip.setPlan_uuid(planUuid);
				vip.setStartdata(date);
				vip.setEnddata(TimeTool.addMonthDay(date, vipmonths));
				vip.setStatus(1);
				vip.setUser_uuid(userUuid);
				vip.setUuid(UUIDTool.getUUID());
				vipRepository.saveAndFlush(vip);
			} else {
				vip.setEnddata(TimeTool.addMonthDay(vip.getEnddata(), vipmonths));
				vipRepository.saveAndFlush(vip);
			}
		}
	}

}
