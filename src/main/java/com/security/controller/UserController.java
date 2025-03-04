package com.security.controller;


import com.security.entity.Users;
import com.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("register")
    Users registerUser(@RequestBody Users user) {
        return service.registerUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody Users user)
    {
      return service.login(user);
    }
}
