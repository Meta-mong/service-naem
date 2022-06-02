package com.metamong.metaticket.domain.admin;


import com.metamong.metaticket.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Admins")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQl 자동으로 키본키 생성
    @Column(name="admin_id")
    private Long id;

    private String loginId;

    @Column(nullable = false)
    private String password;



    public void update(String password ) {


    }
}
