package com.metamong.metaticket.domain.concert.dto;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Phamplet_File;
import com.metamong.metaticket.domain.concert.Ratings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDto {

    private Long id;

    private String title;

    private String description;

    private Long phamplet;

    private LocalDateTime concertDate;

    private Genre genre;

    private Ratings ratings;

    private String address;

    private String host;

    private int seatNum;

    private LocalDate drawStartDate;

    private LocalDate drawEndDate;

    private int price;

    private int visitCnt;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private boolean draw;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FromAdminConcert{ //공연 생성
        private String title;

        private String description;

        private MultipartFile file;

        private String concertDate;

        private Genre genre;

        private Ratings ratings;

        private String address;

        private String host;

        private int seatNum;

        private String drawStartDate;

        private String drawEndDate;

        private int price;
    }


    // ConcertDto 생성
    public static ConcertDto createDto(Concert concert){
        ConcertDto concertDto = ConcertDto.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .phamplet(concert.getPhamplet().getId())
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
                .createDate(concert.getCreatedDate())
                .updateDate(concert.getUpdatedDate())
                .build();

        return concertDto;
    }


    public static Concert createConcert(ConcertDto concertDto, Phamplet_File files){
        Concert concert = Concert.builder()
                .id(concertDto.getId())
                .title(concertDto.getTitle())
                .description(concertDto.getDescription())
                .phamplet(files)
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

    public static Concert createConcert(ConcertDto.FromAdminConcert concertDto, Phamplet_File files){
        LocalDateTime concertDate = LocalDateTime.parse(concertDto.getConcertDate());
        LocalDate drawStartDate = LocalDate.parse(concertDto.getDrawStartDate());
        LocalDate drawEndDate = LocalDate.parse(concertDto.getDrawEndDate());

        Concert concert = Concert.builder()
                .title(concertDto.getTitle())
                .description(concertDto.getDescription())
                .phamplet(files)
                .concertDate(concertDate)
                .genre(concertDto.getGenre())
                .ratings(concertDto.getRatings())
                .address(concertDto.getAddress())
                .host(concertDto.getHost())
                .seatNum(concertDto.getSeatNum())
                .drawStartDate(drawStartDate)
                .drawEndDate(drawEndDate)
                .price(concertDto.getPrice())
                .build();

        return concert;
    }

    public static ConcertDto createConcertDto(ConcertDto.FromAdminConcert dto, Long fileId, Long id){
        LocalDateTime concertDate = LocalDateTime.parse(dto.getConcertDate());
        LocalDate drawStartDate = LocalDate.parse(dto.getDrawStartDate());
        LocalDate drawEndDate = LocalDate.parse(dto.getDrawEndDate());

        ConcertDto concertDto = ConcertDto.builder()
                .id(id)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .phamplet(fileId)
                .concertDate(concertDate)
                .genre(dto.getGenre())
                .ratings(dto.getRatings())
                .address(dto.getAddress())
                .host(dto.getHost())
                .seatNum(dto.getSeatNum())
                .drawStartDate(drawStartDate)
                .drawEndDate(drawEndDate)
                .price(dto.getPrice())
                .build();

        return concertDto;

    }

}