package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicOldExam;
import com.jason.exam.model.TopicType;

public interface TopicOldExamRepository extends JpaRepository<TopicOldExam, Integer>,JpaSpecificationExecutor<TopicOldExam> {
	
	@Query(value = "select * from tb_topic_type where type_name =?1", nativeQuery = true)
	TopicType findTopicTypeByName(String typeName);

	@Query(value = "select * from tb_topic_old_exams where third_party_qid =?1 and third_party_qno = ?2", nativeQuery = true)
	TopicOldExam findTopicOldExamByQidAndQno(String qid, String qNO);

	@Query(value = "select a.uuid,a.title,a.title_parent,a.analysis,a.score,answer,a.type,a.type_tow,a.create_time,a.select_answer,b.type_name,c.uuid as favorites_uuid from tb_topic_old_exams a " + 
			"left join tb_topic_type b on a.type_tow = b.uuid " + 
			"left join tb_favorites c on a.uuid = c.uuid2 and c.type = 3 and c.user_uuid = ?2 " + 
			"where a.`status` =1 and a.old_exam_uuid = ?1",
			countQuery = "select count(*) from tb_topic_old_exams where `status` =1 and old_exam_uuid= ?1 and ?2=?2 ",
			nativeQuery = true)
	Page<Map<String, Object>> findByOldExamMenuUuid(String oldExamMenuUuid,String userUuid, Pageable pageable);
	
	
	
}
