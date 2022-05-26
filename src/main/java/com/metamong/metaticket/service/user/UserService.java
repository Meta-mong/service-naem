package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.dto.UserDTO;

//회원가입, 로그인/로그아웃 -> SpringSecurity 사용 방법도 익히기
public interface UserService {
    //이메일 중복 체크
    public boolean emailCheck(String email);

    //전화번호 중복 체크
    public boolean phoneNumberCheck(String number);

    //해시된 비밀번호 일치 체크
    public boolean hashPasswdCheck(String passwd);

    //전화번호 인증(문자) -> Naver Sens
    //5분 동안 인증 가능하도록 하고, 5분 지나면 다시 문자 인증 버튼 출력하도록 구현
    public boolean smsCheck(String number);

    //회원의 이메일 조회하기 -> 이름과 번호인증 기반
    public String inquireEmail(String name, String number);

    //비밀번호 변경
    public void modifyPasswd(String passwd);

    //회원가입
    public boolean SignUp();

    //로그인
    public UserDTO login(String email, String passwd);
}
