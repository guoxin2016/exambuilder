package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicMenuSimulation;

public interface SimulationRepository extends JpaRepository<TopicMenuSimulation, Integer>,JpaSpecificationExecutor<TopicMenuSimulation> {
	
	
	@Query(value = "select * from tb_simulation where third_party_paper_id =?1", nativeQuery = true)
	TopicMenuSimulation findSimulationByPaperId(String paperId);

	@Query(value = "select uuid,name from tb_simulation where status = 1 and plan_uuid =?1", nativeQuery = true)
	List<Map<String, Object>> findAllSimulationMenuByPlanId(String planId);
	
	
	
}
