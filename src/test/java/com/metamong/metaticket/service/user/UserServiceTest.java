package com.metamong.metaticket.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    void emailCheck() {
        String email = "metamong2@naver.com";
        boolean result = service.emailCheck(email);
        if(result==false){
            System.out.println(email+" 사용 가능");
        } else {
            System.out.println(email+" 사용 불가능");
        }
    }

    @Test
    void phoneNumberCheck() {
        String number = "01012345673";
        boolean result = service.phoneNumberCheck(number);
        if(result==false){
            System.out.println(number+" 사용 가능");
        } else {
            System.out.println(number+" 사용 불가능");
        }
    }

    /*
    @Test
    void sendSms() {
        String number = "01076303241";
        int authNumber = 7578;
        boolean result = service.sendSms(number, authNumber);
        if(result==true){
            System.out.println("전송 성공");
        } else {
            System.out.println("전송 실패");
        }
    }
     */

    @Test
    void hashPasswdCheck() {
    }

    @Test
    void inquireEmail() {
        String name = "person2";
        String number = "01012345672";
        try{
            System.out.println("이메일 : "+service.inquireEmail(name, number));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void modifyPasswd() {
    }

    @Test
    void signUp() {
    }

    @Test
    void login() {
    }
}