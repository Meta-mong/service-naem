package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.service.user.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class OauthController {

    @Autowired
    HttpSession session;

    @Autowired
    OauthService oauthService;

    @ResponseBody
    @GetMapping("signin/oauth2/code/kakao")
    public void kakaoCallback(@RequestParam String code,
                                HttpServletResponse response) throws Exception {
        String accessToken = oauthService.getAccessToken(code);
        oauthService.kakaoUserAccess(accessToken);
        response.sendRedirect("/findaccount");
    }



}
