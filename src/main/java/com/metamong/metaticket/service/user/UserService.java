package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//회원가입, 로그인/로그아웃 -> SpringSecurity 사용 방법도 익히기
public interface UserService {
    //이메일 중복 체크
    public boolean emailCheck(String email);

    //전화번호 중복 체크
    public boolean phoneNumberCheck(String number);

    //전화번호 인증(문자) -> Naver Sens
    //5분 동안 인증 가능하도록 하고, 5분 지나면 다시 문자 인증 버튼 출력하도록 구현
    //public boolean sendSms(String userNumber, int authNumber);

    //Signature(서명) 생성
    //public String getSignature(String time) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException;

    //해시된 비밀번호 일치 체크
    public boolean hashPasswdCheck(User user, String passwd);

    //회원의 이메일 조회하기 -> 이름과 번호인증 기반
    public String inquireEmail(String name, String number) throws Exception;

    //비밀번호 변경
    public boolean modifyPasswd(HttpSession sesson, String passwd);

    //회원가입
    public boolean SignUp();

    //로그인
    public UserDTO login(String email, String passwd);
}
