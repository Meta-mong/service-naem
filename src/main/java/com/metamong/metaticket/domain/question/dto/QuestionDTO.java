package com.metamong.metaticket.domain.question.dto;

import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;


public class QuestionDTO {


    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Quest{

        private Long id;

        private User user;

        private String title;
        private String quesContent;
        private String classify;

        private String anwser;

        private LocalDateTime quesDate;
        private LocalDateTime ansDate;
    }




}
