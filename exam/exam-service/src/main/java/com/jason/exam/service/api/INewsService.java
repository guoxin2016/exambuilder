package com.jason.exam.service.api;

import java.util.Map;

import org.springframework.data.domain.Page;

public interface INewsService {
	
	Page<Map<String, Object>> getNewsByPlanUuId(Map<String, String> params);
}
