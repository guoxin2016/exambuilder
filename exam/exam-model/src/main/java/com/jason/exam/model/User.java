package com.jason.exam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guoxin
 * app用户
 */
@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5869255013411209471L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;    	//uuid
    private String phone;    	//phone
    private String nickname;    	//nickname
    private Date create_time;    	//创建时间
    private String imei;    	 //imei
    private String plan_uuid;    	//plan_uuid
    private String pwd;    		//pwd
    private String pwdmd5;    	//pwdmd5
    private String qq;    		//qq
    private String email;    	//email
    private int status;    	//开始时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getPlan_uuid() {
		return plan_uuid;
	}
	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwdmd5() {
		return pwdmd5;
	}
	public void setPwdmd5(String pwdmd5) {
		this.pwdmd5 = pwdmd5;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}
