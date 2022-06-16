package com.metamong.metaticket.domain.question.dto;

import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class QuestionDTO {


    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Quest{

        private Long id;

        private Long userId;
        private String userName;

        private String title;
        private String quesContent;
        private String classify;

        private String answer;

        private LocalDate createDate;
        private LocalDate updateDate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddQuest{
        private String title;
        private String quesContent;
        private String classify;
    }




}
