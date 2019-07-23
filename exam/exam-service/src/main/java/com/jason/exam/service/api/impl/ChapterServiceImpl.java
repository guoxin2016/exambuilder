package com.jason.exam.service.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.PlanCatalogRepository;
import com.jason.exam.dao.repository.api.TopicChapterRepository;
import com.jason.exam.service.api.IChapterService;

@Service
public class ChapterServiceImpl implements IChapterService {

	@Resource
	TopicChapterRepository topicChapterRepository;
	
	@Resource
	PlanCatalogRepository planCatalogRepository;

	@Override
	public List<Map<String, Object>> getChapterMenuByPlanId(String planId) {
		
		List<Map<String, Object>> menuChapters = planCatalogRepository.findAllPlanCatalogByPlanId(planId);
		return menuChapters;
	}

	@Override
	public Page<Map<String, Object>> getTopicByChapterMenuId(Map<String, String> params) {
		String planCatalogUuid = params.get("chapterMenuId");
		String userUuid = params.get("userUuid");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = topicChapterRepository.findByPlanCatalogUuid(userUuid,planCatalogUuid, pageable);
		return page;
	}

	@Override
	public List<Map<String, Object>> getErrorProneProblemTopic(Map<String, String> params) {
		String planUuid = params.get("planUuid");
		List<Map<String, Object>> topics = topicChapterRepository.findErrorProneTopic(planUuid);
		return topics;
	}

	@Override
	public List<Map<String, Object>> getSelectedTopic(Map<String, String> params) {
		String planUuid = params.get("planUuid");
		List<Map<String, Object>> topics = topicChapterRepository.findSelectedTopic(planUuid);
		return topics;
	}



}
