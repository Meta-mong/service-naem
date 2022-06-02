package com.metamong.metaticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/sign")
    public String signForm(){
        return "/user/sign";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "/user/join";
    }

}
