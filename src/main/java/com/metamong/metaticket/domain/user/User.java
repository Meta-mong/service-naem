package com.metamong.metaticket.domain.user;

import com.metamong.metaticket.domain.BaseEntity;
import com.metamong.metaticket.domain.log.Log;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(name="phone_num", unique = true, nullable = false)
    private String number;

    @Column(columnDefinition = "integer default 0")
    private int loserCnt;

    @Column(columnDefinition = "integer default 0")
    private int cancelCnt;

    //회원 객체 생성
    public static User createUser(UserDTO userDTO, PasswordEncoder passwordEncoder){
        String passwd = passwordEncoder.encode(userDTO.getPasswd());
        User user = User.builder().
                email(userDTO.getEmail()).
                passwd(passwd).
                name(userDTO.getName()).
                age(userDTO.getAge()).
                number(userDTO.getNumber()).
                build();
        return user;
    }

    //UserDTO 객체 생성
    public static UserDTO createUserDTO(User user){
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .passwd(user.getPasswd())
                .name(user.getName())
                .age(user.getAge())
                .number(user.getNumber())
                .loser_cnt(user.getLoserCnt())
                .cancel_cnt(user.getCancelCnt())
                .build();
        return userDTO;
    }

    //Log 테이블과 매핑
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<Log> logs = new ArrayList<>();
}
