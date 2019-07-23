package com.jason.exam.service.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.PlanRepository;
import com.jason.exam.service.api.IPlanService;

@Service
public class PlanServiceImpl implements IPlanService {

	@Resource
	PlanRepository planRepository;

	@Override
	public List<Map<String, Object>> findByAppUuid(String appUuid) {
		return planRepository.findByAppUuid(appUuid);
	}

	

}
