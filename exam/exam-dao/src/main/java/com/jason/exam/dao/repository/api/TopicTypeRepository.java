package com.jason.exam.dao.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicType;

public interface TopicTypeRepository extends JpaRepository<TopicType, Integer>,JpaSpecificationExecutor<TopicType> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);
	
	
	
}
