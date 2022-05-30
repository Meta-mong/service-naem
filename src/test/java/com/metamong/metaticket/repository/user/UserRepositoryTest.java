package com.metamong.metaticket.repository.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 삽입 테스트")
    public void insertUser(){
        UserDTO.SESSION_USER_DATA userDTO = UserDTO.SESSION_USER_DATA.builder()
                .email("metamong1@naver.com")
                .passwd("3241")
                .name("person1")
                .age(27)
                .number("01012345671")
                .build();

        User temp = userRepository.save(User.createUser(userDTO, passwordEncoder));
        System.out.println(temp.toString());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    public void selectUser(){
        System.out.println("<아이디로 회원 조회>");
        User user = userRepository.findById(1L).get();
        System.out.println(user.toString());
        //userRepository.findById(5L).ifPresent(System.out::println);

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
        Optional<User> user = userRepository.findById(1L);
        User updateUser = user.get();
        updateUser.setAge(17);

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