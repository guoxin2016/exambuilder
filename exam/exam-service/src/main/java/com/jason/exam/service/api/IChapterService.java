package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface IChapterService {
	/**
	 * 章节列表
	 * 
	 * @param planId
	 * @return 
	 */
	List<Map<String, Object>> getChapterMenuByPlanId(String planId);

	Page<Map<String, Object>> getTopicByChapterMenuId(Map<String, String> params);

	List<Map<String, Object>> getErrorProneProblemTopic(Map<String, String> params);

	List<Map<String, Object>> getSelectedTopic(Map<String, String> params);

	
}
