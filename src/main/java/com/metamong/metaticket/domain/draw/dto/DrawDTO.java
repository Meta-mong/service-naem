package com.metamong.metaticket.domain.draw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DrawDTO {

    @Getter
    @Setter
    @Builder
    public static class HISTORY {
        Long concertId;
        String concertTitle;
        int ranking;
        String state;
    }
}
