package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;

import java.util.List;

public interface ConcertService {

    // 공연 생성
    void addConcert(Concert concert);

    // 공연 상세내역 조회
    ConcertDto concertInfo(Long id);

    // 공연 수정
    void updateConcert(ConcertDto concertDto,Long id);

    // 공연 삭제
    void deleteConcert(Long id);

    // 공연 전체 조회
    List<Concert> concertAllInfo();

    // 장르별 공연 조회
    List<Concert> concertGenreInfo(Genre genre);
}