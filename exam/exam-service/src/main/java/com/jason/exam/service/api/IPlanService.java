package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

public interface IPlanService {

	List<Map<String, Object>> findByAppUuid(String appUuid);
}
