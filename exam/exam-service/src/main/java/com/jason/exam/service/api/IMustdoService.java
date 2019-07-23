package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface IMustdoService {
	/**
	 * 章节列表
	 * 
	 * @param planId
	 * @return 
	 */
	List<Map<String, Object>> getMustdoMenuByPlanId(String planId);

	Page<Map<String, Object>> getTopicByMustdoMenuId(Map<String, String> params);

	
}
