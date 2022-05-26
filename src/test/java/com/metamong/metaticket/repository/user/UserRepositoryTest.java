package com.metamong.metaticket.repository.user;

import com.metamong.metaticket.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 삽입 테스트")
    public void insertUser(){
        User user = User.builder()
                .email("metamong@naver.com")
                .passwd("3241")
                .name("person2")
                .age(27)
                .number("01012345679")
                .loser_cnt(3)
                .cancel_cnt(3)
                .mod_date(LocalDateTime.now())
                .build();
        user.passwordEncode(user.getPasswd());
        User temp = userRepository.save(user);
        System.out.println(temp.toString());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    public void selectUser(){
        System.out.println("<아이디로 회원 조회>");
        User user = userRepository.findById(3L).get();
        System.out.println(user.toString());

        //패스워드 일치 테스트
        boolean correct = BCrypt.checkpw("3241", user.getPasswd());
        System.out.println("로그인 결과 : "+ correct);

        System.out.println("<전체 회원 조회>");
        List<User> users = userRepository.findAll();
        for(User temp : users){
            System.out.println(temp.toString());
        }
    }

    @Test
    @DisplayName("회원 수정 테스트")
    public void updateUser(){
        Optional<User> user = userRepository.findById(3L);
        User updateUser = user.get();
        updateUser.setAge(17);
        updateUser.setMod_date(LocalDateTime.now());

        try{
            User temp = userRepository.save(updateUser);
            System.out.println(temp.toString());
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteUser(){
        User user = User.builder().id(3L).build();
        try{
            userRepository.delete(user);
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

}