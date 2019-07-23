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
import com.jason.exam.dao.repository.api.TopicMenuMustdoRepository;
import com.jason.exam.dao.repository.api.TopicMustdoRepository;
import com.jason.exam.service.api.IChapterService;
import com.jason.exam.service.api.IMustdoService;

@Service
public class MustdoServiceImpl implements IMustdoService {

	@Resource
	TopicMenuMustdoRepository topicMenuMustdoRepository;
	
	@Resource
	TopicMustdoRepository topicMustdoRepository;

	@Override
	public List<Map<String, Object>> getMustdoMenuByPlanId(String planId) {
		
		List<Map<String, Object>> menuChapters = topicMenuMustdoRepository.findAllMustdoMenuByPlanId(planId);
		return menuChapters;
	}

	@Override
	public Page<Map<String, Object>> getTopicByMustdoMenuId(Map<String, String> params) {
		String mustdoMenuId = params.get("mustdoMenuId");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		String userUuid = params.get("userUuid");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = topicMustdoRepository.findByMustdoMenuUuid(mustdoMenuId,userUuid, pageable);
		return page;
	}



}
