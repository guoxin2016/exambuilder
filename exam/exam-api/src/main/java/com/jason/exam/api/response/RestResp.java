package com.jason.exam.api.response;

/**
 *   
* @ClassName: RestResp
* @Description: TODO(这里用一句话描述这个类的作用)
* @author guoxin
* @date 2018年6月20日 下午10:18:05
*
 */
public class RestResp {
	/**
	 * @Fields code : 请求状态码
	 */ 
	private String status;
	/**
	 * @Fields msg : 返回消息
	 */ 
	private String msg;
	/**
	 * @Fields data : 返回数据
	 */ 
	private Object data;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
