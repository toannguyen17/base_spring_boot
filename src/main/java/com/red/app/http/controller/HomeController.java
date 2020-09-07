package com.red.app.http.controller;

import com.red.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@Autowired
	private Auth auth;

	@GetMapping("/")
	public String index(){
		if (auth.check())
			return "layout/users/home";
		return "layout/home";
	}
}
