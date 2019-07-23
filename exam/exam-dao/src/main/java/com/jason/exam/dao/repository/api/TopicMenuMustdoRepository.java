package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicMenuMustDo;
import com.jason.exam.model.TopicType;

public interface TopicMenuMustdoRepository extends JpaRepository<TopicMenuMustDo, Integer>,JpaSpecificationExecutor<TopicMenuMustDo> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);
	
	@Query(value = "select uuid,parent_uuid,`name` from tb_topicmenu_mustdo where `status`=1 and plan_uuid =?1", nativeQuery = true)
	List<Map<String, Object>> findAllMustdoMenuByPlanId(String planId);

	
	
	
}
