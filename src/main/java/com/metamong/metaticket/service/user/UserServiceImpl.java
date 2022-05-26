package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserRepository userRepository;

    @Override
    public boolean emailCheck(String email) {
        boolean result = false; //중복된 이메일 없는 경우
        User user = null;
        try{
            user = userRepository.findByEmail(email);
            if(user!= null) result = true; //중복된 이메일 있는 경우
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean phoneNumberCheck(String number) {
        boolean result = false; //중복된 전화번호 없음
        User user = null;
        try{
            user = userRepository.findByNumber(number);
            if(user!=null) result = true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean hashPasswdCheck(String passwd) {
        return false;
    }

    @Override
    public boolean smsCheck(String number) {
        return false;
    }

    @Override
    public String inquireEmail(String name, String number) {
        return null;
    }

    @Override
    public void modifyPasswd(String passwd) {

    }

    @Override
    public boolean SignUp() {
        return false;
    }

    @Override
    public UserDTO login(String email, String passwd) {
        return null;
    }
}
