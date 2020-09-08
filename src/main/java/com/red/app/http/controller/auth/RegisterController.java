package com.red.app.http.controller.auth;

import com.red.model.User;
import com.red.system.foundation.auth.RegistersUsers;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterController extends RegistersUsers {
    protected void registered(User user)
    {
        //

    }
}
