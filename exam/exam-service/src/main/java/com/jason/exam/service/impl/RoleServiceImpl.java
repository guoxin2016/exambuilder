package com.jason.exam.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.RoleRepository;
import com.jason.exam.model.Role;
import com.jason.exam.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService{

    @Resource
    RoleRepository roleRepository;
	
	@Override
	public Page<Role> findRoleNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return roleRepository.findAll(pageable);
	}

	@Override
	public Role roleSave(Role role) {
		Role r = roleRepository.save(role);
		return r;
	}

	@Override
	public Role findRoleById(int id) {
		Role r = roleRepository.findById(id).get();
		return r;
	}

	@Override
	public void roleDeleteById(int id) {
		if(roleRepository.existsById(id)) {
			roleRepository.deleteById(id);
		}
	}

}
