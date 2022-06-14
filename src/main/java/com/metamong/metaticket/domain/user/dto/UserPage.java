package com.metamong.metaticket.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPage {
    private int totalPage=1;
    private int startPage=1;
    private int endPage=5;
    private int presentPage=1;
}
