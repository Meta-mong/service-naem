package com.metamong.metaticket.controller.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
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

    }

    @Test
    public void 공연상세조회(){

    }

    @Test
    public void 공연수정(){

    }

    @Test
    public void 공연삭제(){

    }

    @Test
    public void 공연조회(){

    }

    @Test
    public void 장르별_공연조회(){

    }

}
