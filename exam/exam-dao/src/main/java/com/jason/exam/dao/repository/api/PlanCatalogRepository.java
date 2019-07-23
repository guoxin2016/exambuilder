package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicMenuChapter;

public interface PlanCatalogRepository extends JpaRepository<TopicMenuChapter, Integer>,JpaSpecificationExecutor<TopicMenuChapter> {

	@Query(value = "select uuid,parent_uuid,name from tb_plan_catalog where `status`=1 and plan_uuid =?1", nativeQuery = true)
	List<Map<String, Object>> findAllPlanCatalogByPlanId(String planId);

	@Query(value = "select * from tb_plan_catalog where paper_id =?1", nativeQuery = true)
	TopicMenuChapter findByPaperId(String paperId);
	
	@Query(value = "select * from tb_plan_catalog where name =?1 and plan_uuid =?2", nativeQuery = true)
	TopicMenuChapter findByNameAndPlanUuid(String name, String planId);
	
	
	
	
}
