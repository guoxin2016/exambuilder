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
 * 系统菜单
 */
@Entity
@Table(name = "tb_menu")
public class Menu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3904670296413959729L;
	/**
	 * 
	 */

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;				//菜单id
    private String name;    		//名称
    private String ename;    		//英文名称
    private Integer parentId;    	//父级菜单id
    private Integer seq;    		//排序
    private String iconfont;    	//父级菜单icon
    private Integer enable;    		//状态   1:可用 0:不可用
    private Date createTime;    	//创建时间
    private Date updateTime;    	//更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getIconfont() {
		return iconfont;
	}
	public void setIconfont(String iconfont) {
		this.iconfont = iconfont;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
    
}
