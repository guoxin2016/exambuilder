package com.jason.exam.service.api;

import java.util.Map;

public interface IAppService {
    Map<String, Object> findByUuid(String uuid);
}
