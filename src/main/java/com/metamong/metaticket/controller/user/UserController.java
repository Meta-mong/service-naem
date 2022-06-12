package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @Value("${kakao.rest-api-key}")
    public String apiKey;

    @Value("${kakao.redirect-uri}")
    public String redirectUri;

    //회원가입
    @GetMapping("/signup")
    public String signUp(){
        return "/user/signup";
    }

    //로그인
    @GetMapping("/signin")
    public String SignIn(Model model){
        //카카오 로그인 시 사용할 정보
        session.setAttribute("kakaoApiKey", apiKey);
        session.setAttribute("kakaoRedirectUri", redirectUri);

        return "/user/signin";
    }

    //로그아웃
    @GetMapping("/signout")
    public String signOut(){
        userService.signOut(session);
        return "redirect:/";
    }

    //email&pw 찾기
    @GetMapping("/findaccount")
    public String findAccount(){
        return "/user/findAccount";
    }

    //마이페이지 첫 화면(회원 상세 정보 페이지)
    @GetMapping("/mypage")
    public String myPage(){
        return "/mypage/myPage_userInfo";
    }

    //계정 복구
    @PostMapping("/resign")
    @ResponseBody
    public Map<String ,Object> resign(@RequestParam("email") String email){
        Map<String, Object> map = new HashMap<>();
        boolean result = userService.resign(email);
        map.put("result", result);
        return map;
    }

    ////To do
    //전체 회원 정보 조회 -> adminController 로 이전
    
}
