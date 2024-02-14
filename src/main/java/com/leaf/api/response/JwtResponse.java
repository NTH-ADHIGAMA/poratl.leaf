package com.leaf.api.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private boolean isAuthenticated;
	private String rejectReason;
	private String username;
	// private String email;
	private List roles;

	public JwtResponse(String accessToken, String username, List roles, boolean isAuthenticated, String rejectReason) {
		this.token = accessToken;
		this.username = username;
		this.roles = roles;
		this.isAuthenticated = isAuthenticated;
		this.rejectReason = rejectReason;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
