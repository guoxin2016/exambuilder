package com.jason.exam.service.api.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.api.ChannelRepository;
import com.jason.exam.service.api.IChannelService;

@Service
public class ChannelServiceImpl implements IChannelService{
	
	@Resource
    ChannelRepository channelRepository;

	@Override
	public Map<String, Object> findChannelByDevId(String devId) {
		return channelRepository.findChannelByDevId(devId);
	}

}
