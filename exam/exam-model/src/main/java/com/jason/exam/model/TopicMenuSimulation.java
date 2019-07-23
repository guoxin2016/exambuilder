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
 * @author guoxin 模拟题
 */
@Entity
@Table(name = "tb_simulation")
public class TopicMenuSimulation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7362707154660956206L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name; // 模拟题名称
	private String uuid; // 模拟题uuid
	@Column(name = "[desc]")
	private String desc; // 模拟题描述
	private String plan_uuid; // 模拟题描述
	private int status; // 模拟题状态
	private Date create_time; // 模拟题创建时间
	private String third_party_paper_id; //

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getThird_party_paper_id() {
		return third_party_paper_id;
	}

	public void setThird_party_paper_id(String third_party_paper_id) {
		this.third_party_paper_id = third_party_paper_id;
	}
}
