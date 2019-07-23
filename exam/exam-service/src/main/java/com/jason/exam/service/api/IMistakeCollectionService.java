package com.jason.exam.service.api;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.jason.exam.model.MistakeCollection;

public interface IMistakeCollectionService {
	
	MistakeCollection addMistake(Map<String, String> params);
	
	void removeMistake(Map<String, String> params);

	Page<Map<String, Object>> getMistakeCollectionByType(Map<String, String> params);

	void addMistakeBatch(Map<String, String> params);

	void removeMistakeBatch(Map<String, String> params);
    
}
