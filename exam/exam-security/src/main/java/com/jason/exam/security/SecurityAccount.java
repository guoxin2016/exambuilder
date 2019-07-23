package com.jason.exam.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jason.exam.model.Account;
import com.jason.exam.service.IAccountService;

public class SecurityAccount extends Account implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5065527318471588028L;

    IAccountService accountService; 
	
	
	public SecurityAccount(IAccountService accountService,Account account) {
        if (account != null) {
        	this.setId(account.getId());
            this.setAccountname(account.getAccountname());
            this.setPassword(account.getPassword());
            this.setEmail(account.getEmail());
            this.setTelephone(account.getTelephone());
            this.setImage(account.getImage());
            this.setLast_ip(account.getLast_ip());
            this.setLast_time(account.getLast_time());
        }
        this.accountService = accountService;
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
        int accountId = this.getId();
        //添加角色
        List<Map<String, Object>> roles = accountService.findRoleByAccountId(accountId);
        for(Map<String, Object> role :roles ) {
        	authorities.add(new SimpleGrantedAuthority(role.get("name").toString()));
        }
        //添加权限
        List<Map<String, Object>> permissions = accountService.findPermissionByAccountId(accountId);
        for(Map<String, Object> permission :permissions ) {
        	System.out.println("权限"+permission.get("url").toString());
        	authorities.add(new SimpleGrantedAuthority(permission.get("url").toString()));
        }
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getUsername() {
		return this.getAccountname();
	}

}
