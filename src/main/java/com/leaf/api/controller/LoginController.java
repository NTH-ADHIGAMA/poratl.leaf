package com.leaf.api.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.leaf.api.modal.Users;
import com.leaf.api.repository.UserRepository;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
	@Autowired private UserRepository userRepository;

	@RequestMapping(value = "/login")
	public ModelAndView login() {
		System.out.println("Login initiated!!!");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}


	@RequestMapping(value = "/home")
	public ModelAndView home(HttpSession session, ModelMap model) {
		ModelAndView modelview = new ModelAndView();
		boolean isUserActive = isUserActive(session);
		System.out.println("isUserActive==="+isUserActive);
		if (!isUserActive) {
			System.out.println("User not Active");
			modelview.setViewName("login");
			return modelview;
		}
		modelview.setViewName("dashboard");
		return modelview;
	}

	public boolean isUserActive(HttpSession session) {
		boolean isActive = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object userObj = auth.getPrincipal();
		String username = auth.getName();
		Users user = userRepository.findByUserName(username);
		System.out.println("auth===" + user);
		if (user != null) {
			isActive = true;
		} else {
			return isActive;
		}

		// Roles role = rolesRepo.findById(userRole.getRoleId()).get();
		session.setAttribute("userName", username);
		session.setAttribute("userRole", user.getRole());
		session.setAttribute("userId", user.getId());

		return isActive;
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/validateSession")
	public ResponseEntity<AjaxResponseBody> validateSession(HttpSession session) throws Exception {
		AjaxResponseBody result = new AjaxResponseBody();
		boolean isActive = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object userObj = auth.getPrincipal();
		String username = auth.getName();
		Users user = userRepository.findByUserName(username);
		System.out.println("user===" + user);

		if (user != null) {
			isActive = true;
		}
		System.out.println("isActive===" + isActive);
		result.setMsg(isActive);
		return ResponseEntity.ok(result);

	}

}
