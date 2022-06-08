package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Phamplet_File;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import com.metamong.metaticket.repository.concert.FilesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertServiceTest {

    @Autowired
    ConcertService concertService;

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    FilesRepository filesRepository;

    @Test
    public void 공연생성(){
        Concert concert = concertRepository.findAll().get(0);
        concert.setId(2L);
        concertService.addConcert(concert);
    }

    @Test
    public void 공연상세조회(){
        ConcertDto concertDto = concertService.concertInfo(4L);
        System.out.println(concertDto.toString());
    }

    @Test
    public void 공연수정(){
        ConcertDto concertDto = concertService.concertInfo(4L);
        concertDto.setSeatNum(300);
        Phamplet_File files = filesRepository.findById(concertDto.getPhamplet()).orElse(null);
        concertService.updateConcert(concertDto, files);
        System.out.println(concertService.concertInfo(4L).toString());
    }

    @Test
    public void 공연삭제(){
        concertService.deleteConcert(4L);
        assertThat(concertService.concertAllInfo().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void 공연조회(){
        List<ConcertDto> concertList = concertService.concertAllInfo();
        for(ConcertDto c : concertList){
            System.out.println(c);
        }
    }

    @Test
    @Transactional
    public void 장르별_공연조회(){
        List<ConcertDto> concertList = concertService.concertGenreInfo(Genre.MUSICAL_DRAMA);
        for(ConcertDto c : concertList){
            System.out.println(c);
        }
    }
}