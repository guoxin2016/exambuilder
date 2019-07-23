package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface ISimulationService {
	/**
	 * 模拟题菜单
	 * 
	 * @param planId
	 * @return 
	 */
	List<Map<String, Object>> getSimulationMenuByPlanId(String planId);

	Page<Map<String, Object>> getTopicBySimulationMenuId(Map<String, String> params);

	
}
