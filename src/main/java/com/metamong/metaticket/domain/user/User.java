package com.metamong.metaticket.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String name;

    private int age;

    @Column(name="phone_num", unique = true, nullable = false)
    private String number;

    private int loser_cnt;

    private int cancel_cnt;

    @CreationTimestamp
    private LocalDateTime reg_date;
    
    //로그인할 때의 sysdate로 저장
    private LocalDateTime mod_date; 
}
