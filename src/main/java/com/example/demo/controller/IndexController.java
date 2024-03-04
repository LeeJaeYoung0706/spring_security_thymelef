package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
public class IndexController {


    @GetMapping("/")
    public ModelAndView index(Authentication authentication) {
        log.info(authentication.getName());
        log.info(authentication.getAuthorities());
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login/login");
    }
}
