package com.metamong.metaticket.service.batch.draw;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class EndDateConcertDTO {
    Long concertId;
    int seatNum;
    Long drawCnt;
}
