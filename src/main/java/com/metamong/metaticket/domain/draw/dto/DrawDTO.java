package com.metamong.metaticket.domain.draw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DrawDTO {

    @Getter
    @Setter
    @Builder
    public static class HISTORY {
        Long drawId;
        Long concertId; //공연번호
        String concertTitle; //공연명
        int ranking; //대기번호
        String state; //당첨여부
    }
}
