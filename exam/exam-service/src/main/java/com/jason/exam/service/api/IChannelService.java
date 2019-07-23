package com.jason.exam.service.api;

import java.util.Map;

public interface IChannelService {
    Map<String, Object> findChannelByDevId(String devId);
}
