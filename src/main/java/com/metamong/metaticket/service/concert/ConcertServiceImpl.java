package com.metamong.metaticket.service.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.dto.ConcertDto;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    // 공연 생성
    @Override
    public void addConcert(Concert concert) {
        Concert saveConcert = concertRepository.save(concert);
//        return saveConcert;
    }

    // 공연 상세내역 조회
    @Override
    public ConcertDto concertInfo(Long id) {
        Concert findConcert = concertRepository.findById(id).orElse(null);
        ConcertDto concertDto = ConcertDto.createDto(findConcert);
        return concertDto;
    }

    // 공연 수정
    @Override
    public void updateConcert(ConcertDto concertDto,Long id) {
        Concert findConcert = concertRepository.findById(id).orElse(null);
        findConcert.update(concertDto);
    }

    // 공연 삭제
    @Override
    public void deleteConcert(Long id) {
        concertRepository.deleteById(id);
    }

    // 공연 전체 조회
    @Override
    public List<Concert> concertAllInfo() {
        return concertRepository.findAll();
    }

    // 장르별 공연 조회
    @Override
    public List<Concert> concertGenreInfo(Genre genre){
        return concertRepository.findByGenre(genre);
    }

}