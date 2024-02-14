package com.leaf.api;




import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {
	@JsonView
	Object msg;
	@JsonView
	Object msg1;
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
	
}
