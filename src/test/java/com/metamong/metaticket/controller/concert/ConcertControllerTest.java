package com.metamong.metaticket.controller.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.dto.concert.ConcertDto;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ConcertControllerTest {

    @Autowired
    ConcertController concertController;

    @Autowired
    ConcertRepository concertRepository;

    @Test
    void 공연등록(){
        Concert concert = concertRepository.findAll().get(0);
        concert.setId(5L);
        ConcertDto concertDto = ConcertDto.createDto(concert);
        concertController.addConcert(concertDto);
    }

    @Test
    public void 공연상세조회(){
        Concert concert = concertRepository.findAll().get(0);
        ConcertDto concertDto = ConcertDto.createDto(concert);
        ConcertDto concertDto1 =  concertController.concertInfo(concertDto.getId());
        System.out.println(concertDto1.toString());
    }

    @Test
    public void 공연수정(){
        Concert concert = concertRepository.findAll().get(0);
        ConcertDto concertDto = ConcertDto.createDto(concert);
        concertDto.setSeat_num(150);
        concertController.updateConcert(concertDto.getId(), concertDto);
    }

    @Test
    public void 공연삭제(){
        concertController.deleteConcert(2L);
    }

    @Test
    public void 공연조회(){
        List<Concert> concertList = concertController.concertList();
        for(Concert c : concertList){
            System.out.println(c);
        }
    }

}
