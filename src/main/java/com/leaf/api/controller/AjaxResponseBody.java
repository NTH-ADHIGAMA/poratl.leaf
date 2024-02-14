package com.leaf.api.controller;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {

	@JsonView Object msg;
	@JsonView Object msg1;
	@JsonView Object msg2;
	@JsonView Object msg3;
	@JsonView Object msg4;
	@JsonView Object msg5;
	
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Object getMsg1() {
		return msg1;
	}
	public void setMsg1(Object msg1) {
		this.msg1 = msg1;
	}
	public Object getMsg2() {
		return msg2;
	}
	public void setMsg2(Object msg2) {
		this.msg2 = msg2;
	}
	public Object getMsg3() {
		return msg3;
	}
	public void setMsg3(Object msg3) {
		this.msg3 = msg3;
	}
	public Object getMsg4() {
		return msg4;
	}
	public void setMsg4(Object msg4) {
		this.msg4 = msg4;
	}
	public Object getMsg5() {
		return msg5;
	}
	public void setMsg5(Object msg5) {
		this.msg5 = msg5;
	}
	
	

	
}
