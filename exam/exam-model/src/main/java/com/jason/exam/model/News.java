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
 * 咨询
 */
@Entity
@Table(name = "tb_news")
public class News implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -50770774126450849L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;				//菜单id
    private String title;    		//名称
    private String content;    		//内容
    private Integer status;    		//状态   1:可用 0:不可用
    private String author;    		//作者
    private Integer type;    		//类型 1：精华
    private String uuid;    		//uuid
    private Date createtime;    	//创建时间
    private String source;    		//
    private String plan_uuid;    	//科目uuid
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPlan_uuid() {
		return plan_uuid;
	}
	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}
	
    
}
