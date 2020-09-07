package com.red.system.foundation.auth;

import com.red.model.Role;
import com.red.model.User;
import com.red.services.role.RoleService;
import com.red.services.user.UserService;
import com.red.system.auth.form.FormRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public abstract class RegistersUsers {
	protected String redirectTo = "/";
	protected String view       = "auth/register";

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService service;

	@Autowired
	private RoleService roleService;

	@GetMapping("register")
	public String index(Model model){
		model.addAttribute("formRegister", new FormRegister());
		return view;
	}

	@PostMapping("register")
	public String register(@Valid FormRegister formRegister, BindingResult bindingResult, HttpServletRequest request, Model model){
		if (!bindingResult.hasErrors() && validator(formRegister, bindingResult)){
			try {
				User users = create(formRegister);
				registered(users);

				request.login(users.getEmail(), formRegister.getPassword());
				return "redirect:" + redirectTo;
			}catch (DataIntegrityViolationException | ServletException e){
				bindingResult.rejectValue("email", "email.unique", "Email already exists");
			}
		}

		model.addAttribute("formRegister", formRegister);
		return view;
	}

	protected boolean validator(FormRegister form, BindingResult bindingResult)
	{
		boolean check = true;
		if (!form.getPassword().equals(form.getPassword_confirm())){
			bindingResult.rejectValue("password", "password.confirm_password", "Confirm Password does not match.");
			bindingResult.rejectValue("password_confirm", "password.confirm_password", "Confirm Password does not match.");
			check = false;
		}
		return check;
	}

	private User create(FormRegister form)
	{
		String password = bCryptPasswordEncoder.encode(form.getPassword());

		User users     = new User();
		users.setEmail(form.getEmail());
		users.setPassword(password);
		users.setLastName(form.getLast_name());
		users.setFirstName(form.getFirst_name());

		users.setEnabled(true);
		users.setAccountNonLocked(true);
		users.setAccountNonExpired(true);
		users.setCredentialsNonExpired(true);

//		service.save(users);

		Role role = roleService.findByName("ROLE_USER");
		if (role != null){
			List<Role> list = new ArrayList<>();
			list.add(role);
			users.setRoles(list);
		}
		service.save(users);

		return users;
	}

	protected void registered(User user)
	{
		//
	}
}
