package com.jason.exam.service.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.OldExamRepository;
import com.jason.exam.dao.repository.api.TopicOldExamRepository;
import com.jason.exam.service.api.IOldExamService;

@Service
public class OldExamServiceImpl implements IOldExamService {
	
	//真题目录
	@Resource
	OldExamRepository oldExamRepository;
	
	//真题题目
	@Resource
	TopicOldExamRepository topicOldExamRepository;

	@Override
	public List<Map<String, Object>> getOldExamMenuByPlanId(String planId) {
		
		List<Map<String, Object>> menuChapters = oldExamRepository.findAllOldExamMenuByPlanId(planId);
		return menuChapters;
	}

	@Override
	public Page<Map<String, Object>> getTopicByOldExamMenuId(Map<String, String> params) {
		
		String vip_level = params.get("vip_level");
		String oldExamMenuUuid = params.get("oldExamMenuUuid");
		String pageNumber = params.get("pageNumber");
		String userUuid = params.get("userUuid");
		String pageSize = params.get("pageSize");
		if(!"1".equals(vip_level)) {
			pageNumber = "0";
			pageSize = "10";
		}
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = topicOldExamRepository.findByOldExamMenuUuid(oldExamMenuUuid,userUuid, pageable);
		
		return page;
	}



}
