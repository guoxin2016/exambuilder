package com.jason.exam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guoxin
 * app更新
 */
@Entity
@Table(name = "tb_app")
public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8249199612648760009L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;    	//用户名
    private String version;    	//用户密码
    private String url;       	//用户邮箱
    private int    isforce;      	//用户状态
    @Column(name = "[desc]")
    private String desc;     	//上次登录IP
    private String os;   		//上次登录时间
    private Date createtime;   	//上次登录时间
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsforce() {
		return isforce;
	}
	public void setIsforce(int isforce) {
		this.isforce = isforce;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
