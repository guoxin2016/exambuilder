package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicMustDo;
import com.jason.exam.model.TopicType;

public interface TopicMustdoRepository extends JpaRepository<TopicMustDo, Integer>,JpaSpecificationExecutor<TopicMustDo> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);

	@Query(value = "select a.*,b.type_name,c.uuid as favorites_uuid  from tb_topic_mustdo a  " + 
			"left join tb_topic_type b on a.type_tow = b.uuid  " + 
			"left join tb_favorites c on a.uuid = c.uuid2 and c.type = 4 and c.user_uuid = ?2 " + 
			"where a.`status` =1 and menu_mustdo_uuid = ?1",
			countQuery = "select count(*) from tb_topic_mustdo where `status` =1 and menu_mustdo_uuid= ?1 and ?2=?2",
			nativeQuery = true)
	Page<Map<String, Object>> findByMustdoMenuUuid(String mustdoMenuId,String userUuid, Pageable pageable);

	
	
	
}
