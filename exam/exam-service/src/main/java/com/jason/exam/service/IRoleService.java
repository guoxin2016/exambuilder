package com.jason.exam.service;

import org.springframework.data.domain.Page;

import com.jason.exam.model.Role;

public interface IRoleService {
    Page<Role> findRoleNoCriteria(Integer page,Integer size);

    Role roleSave(Role role);

	Role findRoleById(int id);

	void roleDeleteById(int id);
}
