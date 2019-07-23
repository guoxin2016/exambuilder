package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.App;

public interface AppRepository extends JpaRepository<App, Integer>,JpaSpecificationExecutor<App> {
	
	@Query(value = "select * from tb_app where uuid =?1", nativeQuery = true)
	Map<String, Object> findByUuidId(String uuid);
	
	
	
}
