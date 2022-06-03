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

        private User user_id;

        private String title;
        private String ques_content;
        private String classify;

        private boolean answer;
        private String reply_content;

        private LocalDateTime ques_date;
        private LocalDateTime ans_date;
    }




}
