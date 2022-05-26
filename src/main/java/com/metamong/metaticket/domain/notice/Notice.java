package com.metamong.metaticket.domain.notice;


import com.metamong.metaticket.domain.admin.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQl 자동으로 키본키 생성
    @Column(name="notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //   fk설정
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column
    private String classify;

    @Column
    private String title;

    @Column
    private String content;

    @CreationTimestamp
    @Column
    private LocalDateTime reg_date;

    public void update(String classify, String title, String content) {
        setClassify(classify);
        setContent(content);
        setTitle(title);
    }

}
