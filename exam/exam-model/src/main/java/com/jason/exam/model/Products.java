package com.jason.exam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guoxin
 * 产品
 */
@Entity
@Table(name = "tb_products")
public class Products implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 442359192192621232L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;    	//uuid
    private String name;    	//名称
    private int vipmonths;    	//vip月份
    private int vipdays;    	//vip天数
    private int status;    	    //状态 1：有效  0：无效
    private String plan_uuid;    	//科目
    @Column(name = "[desc]")
    private String desc;    	//描述
    private String discount_price;  //折扣价格
    private String purchase_price;  //原价
    private String vip_level;  //原价
    private String ios_product_id;  //ios产品id
    
    
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getVipmonths() {
		return vipmonths;
	}
	public void setVipmonths(int vipmonths) {
		this.vipmonths = vipmonths;
	}
	public int getVipdays() {
		return vipdays;
	}
	public void setVipdays(int vipdays) {
		this.vipdays = vipdays;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPlan_uuid() {
		return plan_uuid;
	}
	public void setPlan_uuid(String plan_uuid) {
		this.plan_uuid = plan_uuid;
	}
	public String getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(String discount_price) {
		this.discount_price = discount_price;
	}
	public String getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(String purchase_price) {
		this.purchase_price = purchase_price;
	}
	public String getVip_level() {
		return vip_level;
	}
	public void setVip_level(String vip_level) {
		this.vip_level = vip_level;
	}
	public String getIos_product_id() {
		return ios_product_id;
	}
	public void setIos_product_id(String ios_product_id) {
		this.ios_product_id = ios_product_id;
	}
}
