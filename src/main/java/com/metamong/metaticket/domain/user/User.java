package com.metamong.metaticket.domain.user;

import com.metamong.metaticket.domain.BaseEntity;
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

    //패스워드 해시 -> 추후 springsecurity의 패스워드 암호화를 사용해도 됨
    public void passwordEncode(String passwd){
        this.passwd = BCrypt.hashpw(passwd, BCrypt.gensalt());
    }

    //Log 테이블과 매핑

}
