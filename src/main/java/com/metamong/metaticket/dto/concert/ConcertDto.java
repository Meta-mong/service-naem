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

    private int seatNum;

    private LocalDateTime drawStartDate;

    private LocalDateTime drawEndDate;

    private int price;

    private int visitCnt;

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
                .seatNum(concert.getSeatNum())
                .drawStartDate(concert.getDrawStartDate())
                .drawEndDate(concert.getDrawEndDate())
                .price(concert.getPrice())
                .visitCnt(concert.getVisitCnt())
                .build();

        return concertDto;
    }

    public static Concert createConcert(ConcertDto concertDto){
        Concert concert = Concert.builder()
                .id(concertDto.getId())
                .title(concertDto.getTitle())
                .description(concertDto.getDescription())
                .phamplet(concertDto.getPhamplet())
                .concertDate(concertDto.getConcertDate())
                .genre(concertDto.getGenre())
                .ratings(concertDto.getRatings())
                .address(concertDto.getAddress())
                .host(concertDto.getHost())
                .seatNum(concertDto.getSeatNum())
                .drawStartDate(concertDto.getDrawStartDate())
                .drawEndDate(concertDto.getDrawEndDate())
                .price(concertDto.getPrice())
                .visitCnt(concertDto.getVisitCnt())
                .build();

        return concert;
    }

}