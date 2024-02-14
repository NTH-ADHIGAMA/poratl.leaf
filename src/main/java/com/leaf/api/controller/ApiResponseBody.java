package com.leaf.api.controller;

import com.fasterxml.jackson.annotation.JsonView;

public class ApiResponseBody {
	@JsonView String status;
	@JsonView int statusCode;
	@JsonView Object response;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	

}
