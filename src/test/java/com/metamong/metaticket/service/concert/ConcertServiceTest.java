package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.dto.concert.ConcertDto;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertServiceTest {

    @Autowired
    ConcertService concertService;

    @Autowired
    ConcertRepository concertRepository;

    @Test
    public void 공연생성(){
        Concert concert = concertRepository.findAll().get(0);
        concert.setId(1L);
        concertService.addConcert(concert);
    }

    @Test
    public void 공연상세조회(){
        ConcertDto concertDto = concertService.concertInfo(1L);
        System.out.println(concertDto.toString());
    }

    @Test
    public void 공연수정(){
        ConcertDto concertDto = concertService.concertInfo(2L);
        concertDto.setSeatNum(300);
        concertService.updateConcert(concertDto,2L);
        System.out.println(concertService.concertInfo(2L).toString());
    }

    @Test
    public void 공연삭제(){
        concertService.deleteConcert(1L);
        assertThat(concertService.concertAllInfo().size()).isEqualTo(1);
    }

    @Test
    public void 공연조회(){
        List<Concert> concertList = concertService.concertAllInfo();
        for(Concert c : concertList){
            System.out.println(c);
        }
    }

    @Test
    public void 장르별_공연조회(){
        List<Concert> concertList = concertService.concertGenreInfo(Genre.MUSICAL_DRAMA);
        for(Concert c : concertList){
            System.out.println(c);
        }
    }
}