package com.cermikengineering.identificationmodule.controllers;

import com.cermikengineering.identificationmodule.models.UserModel;
import com.cermikengineering.identificationmodule.services.UserDetailsServiceImpl;
import com.cermikengineering.identificationmodule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {

        UserModel user = new UserModel();
        user.setUserName(username);
        user.setPassword(password);
        user.setRole(role);

        userService.saveUser(user);

        return "redirect:/success";
    }
}
