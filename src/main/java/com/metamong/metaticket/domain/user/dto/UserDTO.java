package com.metamong.metaticket.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String email;

    private String passwd;

    private String name;

    private int age;

    private String number;

    private int loser_cnt;

    private int cancel_cnt;
}
