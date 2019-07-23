package com.jason.exam.service.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.NewsRepository;
import com.jason.exam.dao.repository.api.OldExamRepository;
import com.jason.exam.dao.repository.api.TopicOldExamRepository;
import com.jason.exam.service.api.INewsService;

@Service
public class NewsServiceImpl implements INewsService {
	
	//真题目录
	@Resource
	NewsRepository newsRepository;

	@Override
	public Page<Map<String, Object>> getNewsByPlanUuId(Map<String, String> params) {
		String planUuid = params.get("planUuid");
		String userUuid = params.get("userUuid");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = newsRepository.findByPlanUuid(planUuid,userUuid, pageable);
		return page;
	}



}
