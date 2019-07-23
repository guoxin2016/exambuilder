package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>,JpaSpecificationExecutor<Order> {
	
	@Query(value = "select * from tb_channel where devId =?1", nativeQuery = true)
	Map<String, Object> findChannelByDevId(String devId);
	
	@Query(value = "select * from tb_order where status !=2 and code = ?1", nativeQuery = true)
	Order findOrderByCode(String outTradeNo);
	
	@Query(value = "select a.* from tb_order a " + 
			"left join tb_products b on a.product_uuid = b.uuid " + 
			"where a.user_uuid =?1 and b.plan_uuid = ?2 and b.vip_level = 0 and a.`status` =2", nativeQuery = true)
	Order findRemoveADByUserUuidAndPlanUuid(String userUuid, String planUuid);
	
	@Query(value = "select * from tb_order where status !=2 and uuid = ?1", nativeQuery = true)
	Order findOrderByUUid(String orderUuid);
	
}
