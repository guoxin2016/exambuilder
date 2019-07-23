package com.jason.exam.service.api.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.AppRepository;
import com.jason.exam.service.api.IAppService;

@Service
public class AppServiceImpl implements IAppService{
	
	@Resource
    AppRepository appRepository;

	@Override
	public Map<String, Object> findByUuid(String uuid) {
		return appRepository.findByUuidId(uuid);
	}

	

}
