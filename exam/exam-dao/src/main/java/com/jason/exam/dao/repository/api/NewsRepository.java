package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.News;

public interface NewsRepository extends JpaRepository<News, Integer>,JpaSpecificationExecutor<News> {
	

	
	@Query(value = "select a.title,a.content,a.author,a.uuid,a.plan_uuid,b.uuid as favorites_uuid  from tb_news a " + 
			"left join tb_favorites b on a.uuid = b.uuid2 and b.type = 2 and b.user_uuid = ?2 " + 
			"where a.`status` = 1 and a.plan_uuid = ?1",
			countQuery = "select count(*) from tb_news where ?2 = ?2 and `status` =1 and plan_uuid= ?1",
			nativeQuery = true)
	Page<Map<String, Object>> findByPlanUuid(String planUuid,String userUuid, Pageable pageable);


	
	
	
}
