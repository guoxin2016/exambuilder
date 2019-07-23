package com.jason.exam.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>,JpaSpecificationExecutor<Account> {

	Account findByAccountname(String accountname);
	
	@Query(value = "select b.`name`,b.`desc` from tb_role_account a \n" + 
			"left join tb_role b on a.role_id = b.id\n" + 
			"where account_id =?1", nativeQuery = true)
	List<Map<String, Object>> findRoleByAccountId(int id);
	
	@Query(value = "select a.* from tb_permission a \n" + 
			"left join tb_permission_role b on a.id = b.permission_id\n" + 
			"left join tb_role_account c on b.role_id = c.role_id\n" + 
			"where c.account_id = ?1", nativeQuery = true)
    List<Map<String, Object>> findPermissionByAccountId(int id);
	
	
}
