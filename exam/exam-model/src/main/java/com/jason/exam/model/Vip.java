package com.jason.exam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jason.exam.common.util.TimeTool;

/**
 * 
 * @author guoxin vip 权限表
 */
@Entity
@Table(name = "tb_vip")
public class Vip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2105172893107959549L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uuid; // uuid
	private String user_uuid; // user_uuid
	private String plan_uuid; // plan_uuid
	private Date startdate; // 开始时间
	private Date enddate; // 开始时间
	private Date createdate; // 开始时间
	private Date updatedate; // 开始时间
	private int status; // 开始时间

	@Transient
	private String startdates;

	@Transient
	private String enddates;

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

	public String getUser_uuid() {
		return user_uuid;
	}

	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}

	public String getPlan_uuid() {
		return plan_uuid;
	}

	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}

	public Date getStartdata() {
		return startdate;
	}

	public void setStartdata(Date startdata) {
		this.startdate = startdata;
	}

	public Date getEnddata() {
		return enddate;
	}

	public void setEnddata(Date enddata) {
		this.enddate = enddata;
	}

	public Date getCreatedata() {
		return createdate;
	}

	public void setCreatedata(Date createdata) {
		this.createdate = createdata;
	}

	public Date getUpdatedata() {
		return updatedate;
	}

	public void setUpdatedata(Date updatedata) {
		this.updatedate = updatedata;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartdates() {
		return TimeTool.formatDate(startdate);
	}

	public void setStartdates(String startdates) {
		this.startdates = startdates;
	}

	public String getEnddates() {
		return TimeTool.formatDate(enddate);
	}

	public void setEnddates(String enddates) {
		this.enddates = enddates;
	}

}
