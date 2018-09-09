package com.casic.dubbo.Consumer.sources.model;

public enum ResultCode {
	SUCCESS(200,"成功"),EXCEPTION(500,"异常");
	
	private int code;
	private String msg;
	private ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	
	

}
