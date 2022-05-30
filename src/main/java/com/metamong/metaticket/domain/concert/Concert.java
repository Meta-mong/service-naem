package com.metamong.metaticket.domain.concert;

import com.metamong.metaticket.domain.BaseEntity;
import com.metamong.metaticket.dto.concert.ConcertDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// 엔티티
@Data
@Entity
@Table(name = "Concerts")
@Builder
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모두 들어간 생성자
public class Concert extends BaseEntity { //BaseEntity -> 공통으로 사용하는  Entity

    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    private String title;

    private String description;

    private String phamplet;

    private LocalDateTime concertDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Ratings ratings;

    private String address;

    private String host;

    private int seat_num;

    private LocalDateTime drawStartDate;

    private LocalDateTime drawEndDate;

    private int price;

    private int visit_cnt;

    public void update(ConcertDto dto){
        title = dto.getTitle();
        description = dto.getDescription();
        phamplet = dto.getPhamplet();
        concertDate = dto.getConcertDate();
        genre = dto.getGenre();
        ratings = dto.getRatings();
        address = dto.getAddress();
        host = dto.getHost();
        seat_num = dto.getSeat_num();
        drawStartDate = dto.getDrawStartDate();
        drawEndDate = dto.getDrawEndDate();
        price = dto.getPrice();
    }

}