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
@Table(name = "tb_plan")
public class Plan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8913878584551478971L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name; // 科目名称
	private String uuid; // uuid
	private String muuid; // uuid
	private Date create_time; // 创建时间
	private int status; // 状态
	private String app_uuid; // app uuid

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

	public String getMuuid() {
		return muuid;
	}

	public void setMuuid(String muuid) {
		this.muuid = muuid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getApp_uuid() {
		return app_uuid;
	}

	public void setApp_uuid(String app_uuid) {
		this.app_uuid = app_uuid;
	}
}
