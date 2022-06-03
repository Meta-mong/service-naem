package com.metamong.metaticket.domain.question;


import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "Questions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQl 자동으로 키본키 생성
    @Column(name="ques_id")
    private Long id;

    @ManyToOne(targetEntity = User.class , fetch = FetchType.LAZY) // user 테이블의 user_id의  fk설정
    @JoinColumn(name = "user_id")
    private User user_id;

    @Column
    private String classify;

    @Column
    private String title;

    @Column
    private String ques_content;

    @Column
    private boolean answer;

    @Column
    private String reply_content;

    @CreationTimestamp
    @Column
    private LocalDateTime ques_date;

    @CreationTimestamp
    @Column
    private LocalDateTime ans_date;


    public void update(QuestionDTO.Quest dto) {
        setTitle(dto.getTitle());
        setQues_content(dto.getQues_content());
        setAnswer(dto.isAnswer());
        setReply_content(dto.getReply_content());

    }

}
