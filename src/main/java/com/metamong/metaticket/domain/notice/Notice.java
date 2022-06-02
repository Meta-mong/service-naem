package com.metamong.metaticket.domain.notice;


import com.metamong.metaticket.domain.BaseEntity;
import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
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
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQl 자동으로 키본키 생성
    @Column(name="notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //   fk설정
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(nullable = false)
    private String classify;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    public void update(NoticeDTO.Notice dto) {
        setClassify(classify);
        setContent(content);
        setTitle(title);
    }



}
