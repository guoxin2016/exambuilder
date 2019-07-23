package com.jason.exam.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.jason.exam.model.Account;
import com.jason.exam.model.query.AccountQuery;

public interface IAccountService {
    Page<Account> findAccountNoCriteria(Integer page,Integer size);
    Page<Account> findAccountCriteria(Integer page,Integer size,AccountQuery accountQuery);
    List<Map<String, Object>> findRoleByAccountId(int id);
    List<Map<String, Object>> findPermissionByAccountId(int id);
}
