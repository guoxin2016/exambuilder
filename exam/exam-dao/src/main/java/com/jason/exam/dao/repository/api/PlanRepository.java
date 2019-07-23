package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer>,JpaSpecificationExecutor<Plan> {
	
	@Query(value = "select * from tb_channel where devId =?1", nativeQuery = true)
	Map<String, Object> findChannelByDevId(String devId);
	
	@Query(value = "select * from tb_plan where app_uuid =?1", nativeQuery = true)
	List<Map<String, Object>> findByAppUuid(String appUuid);
	
}
