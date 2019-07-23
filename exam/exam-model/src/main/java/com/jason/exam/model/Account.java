package com.jason.exam.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guoxin
 * 系统用户
 */
@Entity
@Table(name = "tb_account")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 442359192192621232L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String accountname;    	//用户名
    private String password;    	//用户密码
    private String email;       	//用户邮箱
    private String telephone;   	//电话号码
    private String image;       	//用户头像
    private int    status;      	//用户状态
    private String last_ip;     	//上次登录IP
    private String last_time;   	//上次登录时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	
	@Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountname='" + accountname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", image='" + image + '\'' +
                ", last_ip='" + last_ip + '\'' +
                ", last_time='" + last_time + '\'' +
                '}';
    }
}
