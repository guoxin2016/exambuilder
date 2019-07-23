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
 * 收藏夹
 */
@Entity
@Table(name = "tb_favorites")
public class Favorites implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7488882782603895190L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;    			//用户名
    private String uuid2;    			//收藏内容uuid 比如 题目，咨询
    private String plan_uuid;       	//用户邮箱
    private String menu_uuid;   		//电话号码
    private int type;       			//1:章节练习  2:咨询  3:真题 4:必做 5:模拟考试
    private int    status;      		//状态
    private String user_uuid;     		//用户uuid
    private Date createtime;   			//创建
    
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
	public String getUuid2() {
		return uuid2;
	}
	public void setUuid2(String uuid2) {
		this.uuid2 = uuid2;
	}
	public String getPlan_uuid() {
		return plan_uuid;
	}
	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUser_uuid() {
		return user_uuid;
	}
	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getMenu_uuid() {
		return menu_uuid;
	}
	public void setMenu_uuid(String menu_uuid) {
		this.menu_uuid = menu_uuid;
	}
	
}
