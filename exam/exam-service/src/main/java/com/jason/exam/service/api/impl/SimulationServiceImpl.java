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
import com.jason.exam.dao.repository.api.SimulationRepository;
import com.jason.exam.dao.repository.api.TopicChapterRepository;
import com.jason.exam.dao.repository.api.TopicSimulationRepository;
import com.jason.exam.service.api.ISimulationService;

@Service
public class SimulationServiceImpl implements ISimulationService {

	@Resource
	TopicSimulationRepository topicSimulationRepository;
	
	@Resource
	SimulationRepository simulationRepository;

	@Override
	public List<Map<String, Object>> getSimulationMenuByPlanId(String planId) {
		
		List<Map<String, Object>> menuChapters = simulationRepository.findAllSimulationMenuByPlanId(planId);
		return menuChapters;
	}

	@Override
	public Page<Map<String, Object>> getTopicBySimulationMenuId(Map<String, String> params) {
		String simulationMenuUuid = params.get("simulationMenuId");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		String userUuid = params.get("userUuid");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = topicSimulationRepository.findBySimulationMenuUuid(simulationMenuUuid,userUuid, pageable);
		return page;
	}



}
