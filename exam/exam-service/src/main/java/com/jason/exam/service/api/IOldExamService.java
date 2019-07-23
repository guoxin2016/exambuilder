package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface IOldExamService {

	List<Map<String, Object>> getOldExamMenuByPlanId(String planId);

	Page<Map<String, Object>> getTopicByOldExamMenuId(Map<String, String> params);

	
}
