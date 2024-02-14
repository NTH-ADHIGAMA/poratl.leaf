package com.leaf.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.leaf.api.modal.Users;
import com.leaf.api.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		// for time being encoding if not encoded while creating userlogin
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return new CustomUserDetails(user);
	}

	public UserDetails validateUserNameAndPassword(String username, String password, String userType)
			throws UsernameNotFoundException {
		System.out.println("C=username====" + username);
		System.out.println("C=password====" + password);
		Users user = userRepo.findByUserName(username);
		Users proxyUser = new Users();
		System.out.println("C=user====" + user);
		if (user == null) {
			proxyUser.setUserNotFound(true);
			System.out.println("=C====setUserNotFound====");
			return new CustomUserDetails(proxyUser);
		} else {
			if (!user.getPassword().equals(password)) {
				System.out.println("==C===setInCorrectPassword====");
				proxyUser.setInCorrectPassword(true);
				return new CustomUserDetails(proxyUser);
			}
			if (!userType.equals(user.getType())) {
				proxyUser.setUnAuthorized(true);
				return new CustomUserDetails(proxyUser);
			}
		}
		return new CustomUserDetails(user);
	}

}
