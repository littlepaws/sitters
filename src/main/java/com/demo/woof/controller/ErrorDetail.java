package com.demo.woof.controller;

public class ErrorDetail {

	private String name;
	private String msg;
	
	public ErrorDetail() {}
	
	public ErrorDetail(String name, String msg) {
		this.name = name;
		this.msg = msg;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
