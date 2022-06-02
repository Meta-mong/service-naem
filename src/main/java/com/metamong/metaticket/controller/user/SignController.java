package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sign")
public class SignController {
    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @PostMapping("/emailcheck")
    @ResponseBody
    public void emailCheck(@RequestParam("email") String email, Model model){
        System.out.println("email :" +email);
        boolean result = userService.emailCheck(email);
        //true : 중복 , false : 중복X
        model.addAttribute("result", result);
    }

    /*
    @PostMapping("/numbercheck")
    @ResponseBody
    public void numberCheck(@RequestParam("number") String number, Model model){
        boolean result = userService.phoneNumberCheck(number);
        //true : 중복 , false : 중복X
        if(result==true){
            model.addAttribute("result", result);
            return;
        }else{

        }
    }
*/

    @ResponseBody
    @PostMapping("/numbercheck")
    public void numberCheck(@RequestParam("number") String number, Model model){
        boolean result = userService.phoneNumberCheck(number.trim());
        if(result==true) {
            model.addAttribute("result", result);
            model.addAttribute("message", "이미 사용 중인 전화번호입니다.");
            return;
        }
        //전송할 인증번호
        int random = 0;
        while(random==0){
            random = (int)(Math.random()*10000); //4자리 인증키
        }
        result = userService.sendSms(number.trim(), random);
        if(result==true) {
            model.addAttribute("result", result);
            model.addAttribute("message", "인증번호가 발송되었습니다.");
            model.addAttribute("random", random);
            session.setAttribute("random", random);
            return;
        }else{
            //5분 동안 버튼 안 보이도록 세션에 값 저장
            model.addAttribute("result", result);
            model.addAttribute("message", "인증번호 전송이 실패하였습니다. 다시 시도해주세요");
            return;
        }
    }

    @PostMapping("/signup")
    @ResponseBody
    public void signUp(@Valid UserDTO.SIGN_UP userDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("result", -1);
            model.addAttribute("msg", "회원가입에 실패하였습니다. 다시 시도해주세요.");
        }else {
            boolean result = userService.signUp(userDTO);
            if (result == false) {
                model.addAttribute("msg", "회원가입에 실패하였습니다. 다시 시도해주세요.");
                model.addAttribute("result", -1);
            } else {
                model.addAttribute("msg", "회원가입에 성공하였습니다. 로그인해주세요");
                model.addAttribute("result", 1);
            }
        }
    }

    @PostMapping("/signin")
    @ResponseBody
    public Map<String, Object> signIn(@ModelAttribute UserDTO.SIGN_IN dto){
        System.out.println(dto.toString());
        Map<String, Object> map = new HashMap<>();
        String message = null;
        int result = userService.signIn(dto, session);
        if(result==0){
            message = "이메일이나 패스워드가 일치하지 않습니다.";
        }else if(result==1){
            UserDTO.SESSION_USER_DATA user = (UserDTO.SESSION_USER_DATA)session.getAttribute("user");
            message = user.getName() + " 님 환영합니다.";
        }else if(result==2){
            message = "탈퇴한 계정입니다. 다시 복구하시겠습니까?";
        }else if(result==-1){
            message = "탈퇴한 계정입니다. 다시 가입해주세요";
        }
        map.put("msg", message);
        map.put("result", result);
        return map;
    }



}
