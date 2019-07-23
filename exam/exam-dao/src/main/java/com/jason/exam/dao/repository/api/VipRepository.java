package com.jason.exam.dao.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Vip;

public interface VipRepository extends JpaRepository<Vip, Integer>,JpaSpecificationExecutor<Vip> {
	
	
	@Query(value = "select * from tb_vip where user_uuid = ?1 and plan_uuid =?2", nativeQuery = true)
	Vip findVipByUserUuidAndPlanUuid(String userUuid,String planUuid);
	
	
	
}
