package com.jason.exam.model.query;

import java.io.Serializable;

/**
 * 
 * @author guoxin 系统用户 - 查询
 */
public class AccountQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 442359192192621232L;

	private String accountname; // 用户名
	private String email; // 用户邮箱
	private String telephone; // 电话号码
	private int status; // 用户状态

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
