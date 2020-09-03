package com.red.app.http.controller.auth;

import com.red.model.User;
import com.red.system.auth.Auth;
import com.red.system.auth.form.FormLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class LoginController {
	protected static String redirectTo = "/";
	protected static String loginForm  = "auth/login";

	@Autowired
	protected Auth auth;

	@Autowired
	protected MessageSource messageSource;

	private static final Logger log = LogManager.getLogger(LoginController.class);

	@GetMapping("/login")
	public String login(Model model){
		if (model.getAttribute("formLogin") == null){
			model.addAttribute("formLogin", new FormLogin());
		}
		return loginForm;
	}

	@PostMapping("/login")
	public String loginError(@Valid FormLogin formLogin, BindingResult bindingResult, Model model, HttpServletRequest request) {
		String error = getErrorMessage(request);

		if (error != null){
			model.addAttribute("error", error);
		}

		model.addAttribute("formLogin", formLogin);
		model.addAttribute("org.springframework.validation.BindingResult.formLogin", bindingResult);

		return loginForm;
	}

	//customize the error message
	private String getErrorMessage(HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);

		System.out.println(locale);

		HttpSession session = request.getSession(false);
		String errorMessage = null;
		AuthenticationException exception = null;
		if (session != null) {
			exception = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		if(exception instanceof LockedException) {
			errorMessage = exception.getMessage();
		}else if(exception instanceof AccountExpiredException) {
			errorMessage = exception.getMessage();
		}else if(exception instanceof CredentialsExpiredException) {
			errorMessage = exception.getMessage();
		}else if(exception instanceof DisabledException) {
			errorMessage = exception.getMessage();
		}else if (exception instanceof BadCredentialsException) {
			errorMessage = messageSource.getMessage("login.incorrect", new Object[]{}, locale);// The account or password is incorrect.
		}

		return errorMessage;
	}

	@GetMapping("/logout")
	public String logout(SessionStatus session) {
		log.info("Logout _redt");
		User user = auth.user();
		if (user != null){
			loggedOut(user);
			auth.logout(session);
		}
		return "redirect:" + redirectTo;
	}

	protected void loggedOut(User user)
	{
		System.out.println(user);
		//
	}
}
