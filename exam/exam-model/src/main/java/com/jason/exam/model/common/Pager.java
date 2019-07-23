package com.jason.exam.model.common;

import java.io.Serializable;

public class Pager<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1166759031139544060L;
	
	private static final int OK = 0;
	private static final int FAIL = 1;
	private static final int UNAUTHORIZED = 2;
	private int status = OK; // 状态码
	private String msg = ""; // 描述信息
	
	private int draw = 0;
	private long recordsTotal = 0;
	private long recordsFiltered = 0;
	private T data; // 服务端数据

	// APIS
	public static Pager isOk() {
		return new Pager();
	}
	
	public static Pager isFail() {
		return new Pager();
	}

	public Pager data(T data) {
		this.setData(data);
		return this;
	}

	// Constructors
	public Pager() {

	}

	// Getter&Setters
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
