package com.metamong.metaticket.domain.question;


import com.metamong.metaticket.domain.BaseEntity;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name= "Questions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQl 자동으로 키본키 생성
    @Column(name="ques_id")
    private Long id;

    @ManyToOne(targetEntity = User.class , fetch = FetchType.LAZY) // user 테이블의 user_id의  fk설정
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String classify;

    @Column
    private String title;

    @Column
    private String quesContent;

    @Column
    private String answer;



    public void update(QuestionDTO.Quest dto) {
        setTitle(dto.getTitle());
        setQuesContent(dto.getQuesContent());
        setAnswer(dto.getAnwser());

    }

}
