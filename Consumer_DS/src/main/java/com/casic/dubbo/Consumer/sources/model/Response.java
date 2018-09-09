package com.casic.dubbo.Consumer.sources.model;

import java.io.Serializable;

public class Response implements Serializable{
	
	private String msg;
	private int code;
	public Response() {
		super();
	}
	public Response(int code,String msg) {
		this.msg = msg;
		this.code = code;
	}
	
	

}
