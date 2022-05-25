package com.metamong.metaticket.repository.user;

import com.metamong.metaticket.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application.yml")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 삽입 테스트")
    public void insertUser(){
        User user = User.builder()
                .email("metamong@naver.com")
                .passwd("3241")
                .name("person1")
                .age(27)
                .number("01012345678")
                .loser_cnt(3)
                .cancel_cnt(3)
                .mod_date(LocalDateTime.now())
                .build();
        User temp = userRepository.save(user);
        System.out.println(temp.toString());

    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    public void selectUser(){
        List<User> users = userRepository.findAll();
        for(User user : users){
            System.out.println(user.toString());
        }
    }

    @Test
    @DisplayName("회원 수정 테스트")
    public void updateUser(){
        //추후 명시적인 메서드 생성 및 변경
        User user = User.builder()
                .id(4L)
                .email("metamong@naver.com")
                .passwd("7852")
                .name("person1")
                .age(27)
                .number("01012345678")
                .loser_cnt(3)
                .cancel_cnt(3)
                .mod_date(LocalDateTime.now())
                .build();
        try{
            User temp = userRepository.save(user);
            System.out.println(temp.toString());
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteUser(){
        User user = User.builder().id(4L).build();
        try{
            userRepository.delete(user);
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

}