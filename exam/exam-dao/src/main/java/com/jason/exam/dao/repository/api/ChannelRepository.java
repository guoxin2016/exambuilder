package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Account;

public interface ChannelRepository extends JpaRepository<Account, Integer>,JpaSpecificationExecutor<Account> {
	
	@Query(value = "select * from tb_channel where devId =?1", nativeQuery = true)
	Map<String, Object> findChannelByDevId(String devId);
	
	
	
}
