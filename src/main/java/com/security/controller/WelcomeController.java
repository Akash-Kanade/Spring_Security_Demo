package com.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("welcome")
    public String Welcome() {
        return "Welcome to the Spring Security";
    }

    @GetMapping("csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
