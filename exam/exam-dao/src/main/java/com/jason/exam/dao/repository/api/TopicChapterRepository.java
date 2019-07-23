package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicChapter;
import com.jason.exam.model.TopicType;

public interface TopicChapterRepository extends JpaRepository<TopicChapter, Integer>,JpaSpecificationExecutor<TopicChapter> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);
	
	
	
	@Query(value = "select a.uuid,a.title,a.title_parent,a.analysis,a.score,answer,a.type,a.select_answer,b.type_name,c.uuid as favorites_uuid from tb_topic_chapter a " + 
			"left join tb_topic_type b on a.type_tow = b.uuid " + 
			"left join tb_favorites c on a.uuid = c.uuid2 and c.type = 1 and c.user_uuid = ?1 " + 
			"where a.`status` =1 and a.plan_catalog_uuid = ?2 ",
			countQuery = "select count(*) from tb_topic_chapter where ?1 = ?1 and `status` =1 and plan_catalog_uuid= ?2",
			nativeQuery = true)
	Page<Map<String, Object>> findByPlanCatalogUuid(String userUuid,String planCatalogUuid, Pageable pageable);


	@Query(value = "select a.*,c.type_name from tb_topic_chapter a " + 
			"left join  tb_plan_catalog b on a.plan_catalog_uuid = b.uuid " + 
			"left join tb_topic_type c on a.type_tow = c.uuid " + 
			"where a.`status` = 1 and b.plan_uuid = ?1 ORDER BY RAND() LIMIT 10 ", nativeQuery = true)
	List<Map<String, Object>> findErrorProneTopic(String planUuid);


	@Query(value = "select a.*,c.type_name from tb_topic_chapter a " + 
			"left join  tb_plan_catalog b on a.plan_catalog_uuid = b.uuid " + 
			"left join tb_topic_type c on a.type_tow = c.uuid " + 
			"where a.`status` = 1 and b.plan_uuid = ?1 ORDER BY RAND() LIMIT 10 ", nativeQuery = true)
	List<Map<String, Object>> findSelectedTopic(String planUuid);
	
	
	
}
