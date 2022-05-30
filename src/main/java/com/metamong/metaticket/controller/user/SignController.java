package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/sign/")
public class SignController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/sendsms")
    public String sendSms(@RequestParam("userNumber") String userNumber){
        boolean result = userService.phoneNumberCheck(userNumber);
        if(result==true) return "이미 존재하는 전화번호입니다.";
        //전송할 인증번호
        int random = 0;
        while(random==0){
            random = (int)(Math.random()*10000); //4자리 인증키
        }
        result = userService.sendSms(userNumber, random);
        if(result==true) return "인증번호를 확인해주세요";
        //5분 동안 버튼 안 보이도록 세션에 값 저장
        return "인증번호 전송이 실패하였습니다. 다시 시도해주세요";
    }

    @PostMapping("/signup")
    public String signUp(@Valid UserDTO userDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/sign/signupform";
        }

        boolean result = userService.signUp(userDTO);
        if(result == false) {
            model.addAttribute("error", "회원가입에 실패하였습니다. 다시 시도해주세요.");
            return "/sign/signupform";
        }
        return "redirect:/"; //메인 화면으로 이동
    }

}
