package com.jason.exam.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jason.exam.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role> {

	Role findByName(String name);
	
	
}
