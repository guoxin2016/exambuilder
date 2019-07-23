package com.jason.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.AccountRepository;
import com.jason.exam.model.Account;
import com.jason.exam.model.Permission;
import com.jason.exam.model.Role;
import com.jason.exam.model.query.AccountQuery;
import com.jason.exam.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService{

    @Resource
    AccountRepository accountRepository;
	
	@Override
	public Page<Account> findAccountNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return accountRepository.findAll(pageable);
	}

	@Override
	public Page<Account> findAccountCriteria(Integer page, Integer size, AccountQuery accountQuery) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        Page<Account> accountPage = accountRepository.findAll(new Specification<Account>(){
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null!=accountQuery.getAccountname()&&!"".equals(accountQuery.getAccountname())){
                    list.add(criteriaBuilder.equal(root.get("accontname").as(String.class), accountQuery.getAccountname()));
                }
                if(null!=accountQuery.getEmail()&&!"".equals(accountQuery.getEmail())){
                    list.add(criteriaBuilder.equal(root.get("email").as(String.class), accountQuery.getEmail()));
                }
                if(null!=accountQuery.getTelephone()&&!"".equals(accountQuery.getTelephone())){
                    list.add(criteriaBuilder.equal(root.get("telephone").as(String.class), accountQuery.getTelephone()));
                }
//                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), accountQuery.getStatus()));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }

        },pageable);
        return accountPage;
	}

	@Override
	public List<Map<String, Object>> findRoleByAccountId(int id) {
		return accountRepository.findRoleByAccountId(id);
	}

	@Override
	public List<Map<String, Object>> findPermissionByAccountId(int id) {
		return accountRepository.findPermissionByAccountId(id);
	}

}
