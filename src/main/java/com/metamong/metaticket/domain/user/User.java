package com.metamong.metaticket.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
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

    @Column(columnDefinition = "default 0")
    private int loser_cnt;

    @Column(columnDefinition = "default 0")
    private int cancel_cnt;

    @CreationTimestamp
    private LocalDateTime reg_date;

    //로그인할 때의 sysdate로 저장
    private LocalDateTime mod_date;

    //패스워드 해시 -> 추후 springsecurity의 패스워드 암호화를 사용해도 됨
    public void passwordEncode(String passwd){
        this.passwd = BCrypt.hashpw(passwd, BCrypt.gensalt());
    }

}
