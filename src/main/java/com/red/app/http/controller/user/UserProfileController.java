package com.red.app.http.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    @GetMapping
    public String doGet(){
        return "layout/users/profile/index";
    }
}
