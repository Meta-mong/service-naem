package com.metamong.metaticket.domain.admin.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class adminDTO {

    private Long id;
    private String loginId;
    private String password;

}
