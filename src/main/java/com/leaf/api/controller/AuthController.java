package com.leaf.api.controller;




import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leaf.api.config.CustomUserDetailsService;
import com.leaf.api.config.JwtService;
import com.leaf.api.config.CustomUserDetails;
import com.leaf.api.request.LoginRequest;
import com.leaf.api.response.JwtResponse;


@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtUtils;

	@Autowired
	CustomUserDetailsService custUserDetails;

	@GetMapping("/test")
	public String Test() {
		System.out.println("====identity TEST=====");
		return "test is valid";
	}

	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
		String tokenStatus = "Token is Invalid";
		try {
			Boolean isValid = jwtUtils.validateJwtToken(token);
			if (isValid) {
				tokenStatus = "Token Invalid";
			}
		} catch (Exception e) {
			System.out.println("e====" + e.getMessage());
			tokenStatus = e.getMessage();
		}

		return ResponseEntity.ok(tokenStatus);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateuser(@RequestBody LoginRequest loginRequest) {
		System.out.println("User authentication!!!");
		CustomUserDetails userDetails = (CustomUserDetails) custUserDetails.validateUserNameAndPassword(
				loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getUserType());
		//System.out.println("isUserNotFound====" + userDetails.isUserNotFound());
		//System.out.println("isInCorrectPassword====" + userDetails.isInCorrectPassword());
		//System.out.println("isUnAuthorized====" + userDetails.isUnAuthorized());
		boolean isAuthenticated = true;
		String reason = "";
		if (userDetails.isUserNotFound() || userDetails.isInCorrectPassword()) {
			isAuthenticated = false;
			reason = "Incorrect Username or Password";
		}
		if (userDetails.isUnAuthorized()) {
			isAuthenticated = false;
			reason = "User not Authorized to Login this Application ";
		}
		String jwt = "";
		List<String> roles = null;
		if (isAuthenticated) {
			jwt = jwtUtils.generateToken(userDetails.getUsername());
			roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		}
		System.out.println("isAuthenticated==1==" + isAuthenticated);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles, isAuthenticated, reason));
	}

}
