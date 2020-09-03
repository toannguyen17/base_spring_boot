package com.red.system.auth;

import com.red.model.User;
import com.red.model.UserDetailCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Auth {

	private static final Logger log = LogManager.getLogger(Auth.class);

	public Auth(){
	}

	public boolean check() {
		User user = user();
		return user != null;
	}

	public User user() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailCustom) {
				return ((UserDetailCustom) principal).getUserDetails();
			}
		}
		return null;
	}

	public void logout(SessionStatus session) {
		SecurityContextHolder.getContext().setAuthentication(null);
		session.setComplete();
	}
	public void logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			log.error(e.getMessage(), e);
		}
	}
}
