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
@Table(name = "tb_order")
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4695025344912847244L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;				//菜单id
    private String code;    		//订单编号
    private Date createTime;    	//创建时间
    private String user_uuid;    	//用户编号
    private int status;    			//订单状态  1.未支付2.支付成功.3支付失败
    private String uuid;    		//uuid
    private String product_uuid;    //产品uuid
    private Integer pay_type;    	//支付类型 1支付宝2微信3银联
    private String amount;    		//金额
    private String channel;    		//渠道
    private String product_name;    //产品名称
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUser_uuid() {
		return user_uuid;
	}
	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProduct_uuid() {
		return product_uuid;
	}
	public void setProduct_uuid(String product_uuid) {
		this.product_uuid = product_uuid;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
    
}
