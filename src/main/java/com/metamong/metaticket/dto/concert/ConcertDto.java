package com.metamong.metaticket.dto.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Ratings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDto {

    private Long id;

    private String title;

    private String description;

    private String phamplet;

    private LocalDateTime concertDate;

    private Genre genre;

    private Ratings ratings;

    private String address;

    private String host;

    private int seat_num;

    private LocalDateTime drawStartDate;

    private LocalDateTime drawEndDate;

    private int price;

    private int visit_cnt;

    // ConcertDto 생성
    public static ConcertDto createDto(Concert concert){
        ConcertDto concertDto = ConcertDto.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .phamplet(concert.getPhamplet())
                .concertDate(concert.getConcertDate())
                .genre(concert.getGenre())
                .ratings(concert.getRatings())
                .address(concert.getAddress())
                .host(concert.getHost())
                .seat_num(concert.getSeat_num())
                .drawStartDate(concert.getDrawStartDate())
                .drawEndDate(concert.getDrawEndDate())
                .price(concert.getPrice())
                .visit_cnt(concert.getVisit_cnt())
                .build();

        return concertDto;
    }

}