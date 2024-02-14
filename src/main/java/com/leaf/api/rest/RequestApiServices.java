package com.leaf.api.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leaf.api.controller.AjaxResponseBody;
import com.leaf.api.modal.Users;
import com.leaf.api.repository.UserRepository;

public class RequestApiServices {
	@Autowired private UserRepository userRepository;
	
	@GetMapping("/test")
	public String Test() {
		System.out.println("====leaf TEST=====");
		return "leaf test is valid";

	
	}
}
