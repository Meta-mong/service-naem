package com.metamong.metaticket.domain.notice.dto;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.notice.Notice;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class NoticeDTO {


    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Notice{

            private Long id; //공지사항 아이디 기본키

            private String title;
            private String content;
            private String classify;

            //작성자 정보
            private Long adminId; // 관리자

            //작성된 날짜와
            private LocalDate createDate;
            private LocalDate updateDate;


    }



}
