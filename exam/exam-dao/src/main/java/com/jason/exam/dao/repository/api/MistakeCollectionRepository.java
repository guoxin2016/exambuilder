package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jason.exam.model.MistakeCollection;

public interface MistakeCollectionRepository extends JpaRepository<MistakeCollection, Integer>,JpaSpecificationExecutor<MistakeCollection> {
	
	@Modifying
    @Transactional
	@Query(value = "delete from tb_mistake_collection where uuid =?1",nativeQuery = true)
	void deleteByUuid(String uuid);

	@Query(value = "select c.`name`,d.type_name,a.title,a.title_parent,a.uuid,a.analysis,a.answer,a.select_answer,a.score,a.type,b.uuid as mistake_uuid from tb_topic_chapter a " + 
			"left join tb_mistake_collection b on a.uuid = b.uuid2 " + 
			"left join tb_plan_catalog c on c.uuid = b.menu_uuid " + 
			"left join tb_topic_type d on a.type_tow = d.uuid " + 
			"where b.plan_uuid = ?1 and b.user_uuid = ?2 and b.type = ?3",
			countQuery = "select count(*) from  tb_favorites b " + 
					"where b.plan_uuid = ?1  and b.user_uuid = ?2 and b.type = ?3",
			nativeQuery = true)
	Page<Map<String, Object>> getMistakesChapterTopicsByTypeAndPlanUuidAndUserUuid(String planUuid, String userUuid, String type, Pageable pageable);
	
	
	@Query(value = "select c.`name`,d.type_name,a.title,a.title_parent,a.uuid,a.analysis,a.answer,a.select_answer,a.score,a.type,b.uuid as mistake_uuid from tb_topic_old_exams a " + 
			"left join tb_mistake_collection b on a.uuid = b.uuid2 " + 
			"left join tb_old_exams c on c.uuid = b.menu_uuid " + 
			"left join tb_topic_type d on a.type_tow = d.uuid " + 
			"where b.plan_uuid = ?1 and b.user_uuid = ?2 and b.type = ?3",
			countQuery = "select count(*) from  tb_favorites b " + 
					"where b.plan_uuid = ?1  and b.user_uuid = ?2 and b.type = ?3",
			nativeQuery = true)
	Page<Map<String, Object>> getMistakesOldExamTopicsByTypeAndPlanUuidAndUserUuid(String planUuid, String userUuid,
			String type, Pageable pageable);

	@Query(value = "select c.`name`,d.type_name,a.title,a.title_parent,a.uuid,a.analysis,a.answer,a.select_answer,a.score,a.type,b.uuid as mistake_uuid from tb_topic_mustdo a " + 
			"left join tb_mistake_collection b on a.uuid = b.uuid2 " + 
			"left join tb_topicmenu_mustdo c on c.uuid = b.menu_uuid " + 
			"left join tb_topic_type d on a.type_tow = d.uuid  " + 
			"where b.plan_uuid = ?1 and b.user_uuid = ?2 and b.type = ?3",
			countQuery = "select count(*) from  tb_favorites b " + 
					"where b.plan_uuid = ?1  and b.user_uuid = ?2 and b.type = ?3",
			nativeQuery = true)
	Page<Map<String, Object>> getMistakesMustdoTopicsByTypeAndPlanUuidAndUserUuid(String planUuid, String userUuid,
			String type, Pageable pageable);
	
	@Query(value = "select c.`name`,d.type_name,a.title,a.title_parent,a.uuid,a.analysis,a.answer,a.select_answer,a.score,a.type,b.uuid as mistake_uuid from tb_topic_simulation a " + 
			"left join tb_mistake_collection b on a.uuid = b.uuid2 " + 
			"left join tb_simulation c on c.uuid = b.menu_uuid " + 
			"left join tb_topic_type d on a.type_tow = d.uuid  " + 
			"where b.plan_uuid = ?1 and b.user_uuid = ?2 and b.type = ?3",
			countQuery = "select count(*) from  tb_favorites b " + 
					"where b.plan_uuid = ?1  and b.user_uuid = ?2 and b.type = ?3",
			nativeQuery = true)
	Page<Map<String, Object>> getMistakesSimulationTopicsByTypeAndPlanUuidAndUserUuid(String planUuid, String userUuid,
			String type, Pageable pageable);
	
	@Query(value = "select * from tb_mistake_collection where uuid2 =?1",nativeQuery = true)
	MistakeCollection findByUuid2(String uuid2);
	
}
