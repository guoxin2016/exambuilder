package com.jason.exam.dao.repository.api;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.User;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

	@Query(value="select id,uuid,phone,nickname,qq,email from tb_user where 1=1 and phone = ?1 and pwdmd5 = ?2",nativeQuery=true)
	Map<String, Object> findByPhoneAndPwd(String phone, String pwd);

	@Query(value="select * from tb_user where 1=1 and phone = ?1",nativeQuery=true)
	User findUserByPhone(String phone);
	
	


	
	
	
}
