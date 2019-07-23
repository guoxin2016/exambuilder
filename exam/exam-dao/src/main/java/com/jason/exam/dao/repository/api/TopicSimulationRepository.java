package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicSimulation;
import com.jason.exam.model.TopicType;

public interface TopicSimulationRepository extends JpaRepository<TopicSimulation, Integer>,JpaSpecificationExecutor<TopicSimulation> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);
	
	
	@Query(value = "select a.uuid,a.title,a.analysis,a.score,answer,a.type,a.title_parent,a.create_time,a.select_answer,b.type_name,c.uuid as favorites_uuid " + 
			"from tb_topic_simulation a " + 
			"left join tb_topic_type b on a.type_tow = b.uuid " + 
			"left join tb_favorites c on a.uuid = c.uuid2 and c.type = 5 and c.user_uuid = ?2 " + 
			"where a.`status` =1 and a.simulation_uuid = ?1",
			countQuery = "select count(*) from tb_topic_simulation where `status` =1 and simulation_uuid= ?1 and ?2=?2",
			nativeQuery = true)
	Page<Map<String, Object>> findBySimulationMenuUuid(String simulationMenuUuid,String userUuid, Pageable pageable);

	
	
	
}
