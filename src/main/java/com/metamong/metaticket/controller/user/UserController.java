package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    //회원가입
    @GetMapping("/signup")
    public String signUp(){
        return "/user/signup";
    }

    //로그인
    @GetMapping("/signin")
    public String SignIn(){
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

    ////To do
    //회원 정보 수정
    @GetMapping("/user/modifyInfo")
    public String modifyInfo(){
        return "/user/modifyInfo";
    }

    @PostMapping("/user/modifyInfo")
    @ResponseBody
    public boolean modifyInfo(@ModelAttribute UserDTO.SESSION_USER_DATA dto){
        return false;
    }

    //회원 탈퇴 -> 페이지 만들고 해당 페이지에서 비밀번호 입력하고 탈퇴 버튼 누르도록 구현
    //내 정보 수정 페이지에 포함,,? 버튼으로
    @GetMapping("/user/unregister")
    public String unregister(){
        return "/user/unregister"; //비밀번호 체크하는 view
    }

    @PostMapping("/user/unregister")
    @ResponseBody
    public boolean unregisterConfirm(){
        return userService.unregister(session);
        //뷰에서 /signout url로 리다이렉트
    }

    //회원 탈퇴 시 패스워드 체크(ajax) -> view: 정말 탈퇴하시겠습니까?
    @PostMapping("/user/passwdck")
    @ResponseBody
    public boolean passwdCheck(@RequestParam("passwd") String passwd){
        UserDTO.SESSION_USER_DATA dto = (UserDTO.SESSION_USER_DATA)session.getAttribute("user");
        User user = userService.userInfo(dto.getId());
        boolean result = userService.passwdCheck(passwd.trim(), user);
        return result;
    }

    //전체 회원 정보 조회 -> adminController 로 이전
    
}
