package com.jason.exam.dao.repository.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.TopicMenuOldExam;

public interface OldExamRepository extends JpaRepository<TopicMenuOldExam, Integer>,JpaSpecificationExecutor<TopicMenuOldExam> {
	
	
	@Query(value = "select * from tb_old_exams where third_party_paper_id =?1", nativeQuery = true)
	TopicMenuOldExam findOldExamByPaperId(String paperId);
	
	@Query(value = "select uuid,`name`,`desc` from tb_old_exams where `status`=1 and plan_uuid =?1", nativeQuery = true)
	List<Map<String, Object>> findAllOldExamMenuByPlanId(String planId);


	
	
	
}
