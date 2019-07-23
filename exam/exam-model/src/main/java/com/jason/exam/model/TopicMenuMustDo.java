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
 * @author guoxin 必做题
 */
@Entity
@Table(name = "tb_topicmenu_mustdo")
public class TopicMenuMustDo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2966571351852853421L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name; // 必做题目录名称
	private String uuid; //  必做题目录uuid
	private String plan_uuid; // 科目id
	private String parent_uuid; // 父uuid
	private int status; 		// 必做题目录状态
	private Date create_time;   // 必做题目录创建时间

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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getParent_uuid() {
		return parent_uuid;
	}

	public void setParent_uuid(String parent_uuid) {
		this.parent_uuid = parent_uuid;
	}
	

}
