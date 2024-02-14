package com.leaf.api.config;





import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.leaf.api.modal.Users;

public class CustomUserDetails implements UserDetails {

	private Users user;

	public CustomUserDetails(Users user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String rolesStr = user.getRole();
		String[] roles = rolesStr.split(",");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
		System.out.println("authorities=====" + authorities.toString());
		return authorities;
	}

	public boolean isUserNotFound() {
		return user.isUserNotFound();
	}

	public boolean isInCorrectPassword() {
		return user.isInCorrectPassword();
	}
	public boolean isUnAuthorized() {
		return user.isUnAuthorized();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullName() {
		return user.getUsername();
	}

}
