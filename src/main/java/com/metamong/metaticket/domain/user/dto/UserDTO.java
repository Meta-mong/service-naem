package com.metamong.metaticket.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class UserDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SIGN_IN{
        @NotEmpty(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        @Length(min = 8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
        private String passwd;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SIGN_UP{
        @NotEmpty(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
        @Length(min = 8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
        private String passwd;

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @NotEmpty(message = "나이는 필수 입력 값입니다.")
        private int age;

        @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
        private String number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FIND_EMAIL{
        private String name;

        private String number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SESSION_USER_DATA{
        private Long id;

        private String email;

        private String passwd;

        private String name;

        private int age;

        private String number;

        private int loserCnt;

        private int cancelCnt;
    }

}
