package com.jason.exam.api.response;

/**
 * 
* @ClassName: RestRespError
* @Description: TODO(这里用一句话描述这个类的作用)
* @author guoxin
* @date 2018年6月20日 下午10:18:28
*
 */
public enum RestRespError {

	success("0","success"),
	Required_base_parameter_missing("101","缺少基本的必要参数"),
	illegal_parameter("102","非法参数"),
	devid_invalid("103","'devid'无效"),
	outdated_request("104","请求超时"),
	sign_invalid("105","签名验证失败"),
	user_login_wrong_password("110","登录密码错误"),
	user_not_find("130","无此用户"),
	system_error("999","系统异常");

	RestRespError(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
