package com.metamong.metaticket.controller;

import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    //로그인
    @GetMapping("/sign")
    public String signForm(){
        return "/user/sign";
    }

    //회원가입
    @GetMapping("/signup")
    public String signUp(){
        return "/user/signup";
    }

    //로그아웃
    @GetMapping("/signout")
    public String signOut(){
        userService.signOut(session);
        return "redirect:/";
    }
}
