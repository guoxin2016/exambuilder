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
 * @author guoxin 考试科目 比如护师
 */
@Entity
@Table(name = "tb_plan_catalog")
public class TopicMenuChapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4050657231551430612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uuid; // uuid
	private String parent_uuid; // uuid
	private String name; // 科目名称
	private Date create_time; // 创建时间
	private int status; // 状态
	private String plan_uuid; // 状态
	private String paper_id; // 状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getParent_uuid() {
		return parent_uuid;
	}

	public void setParent_uuid(String parent_uuid) {
		this.parent_uuid = parent_uuid;
	}

	public String getPlan_uuid() {
		return plan_uuid;
	}

	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}

	public String getPaper_id() {
		return paper_id;
	}

	public void setPaper_id(String paper_id) {
		this.paper_id = paper_id;
	}
}
